package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.dto.StudentCommitDto;
import com.dds.sfscourse.entity.Homework;
import com.dds.sfscourse.entity.HomeworkSubmit;
import com.dds.sfscourse.entity.Student;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.repo.HomeworkRepo;
import com.dds.sfscourse.repo.HomeworkSubmitRepo;
import com.dds.sfscourse.repo.StudentRepo;
import com.dds.sfscourse.security.JwtUserDetails;
import com.dds.sfscourse.service.MongoDBService;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class HomeworkSubmitController {
    @Autowired
    HomeworkRepo homeworkRepo;

    @Autowired
    HomeworkSubmitRepo homeworkSubmitRepo;

    @Autowired
    StudentRepo studentRepo;

    @Autowired
    private AdminCourseRepo adminCourseRepo;

    @Autowired
    MongoDBService mongoDBService;

    //作业提交列表
    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}/submit")
    ResultBean getHomeworkSubmits(HttpSession session, @PathVariable int courseId, @PathVariable Integer homeworkId,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable){

        // TODO: 2018/12/14 native查询的分页
        int page=pageable.getPageNumber(),pageSize=pageable.getPageSize();

        Homework homework = new Homework();
        homework.setId(homeworkId);
        List<Object[]> objectsList = homeworkSubmitRepo.findHomeworkSubmitByHomework(homework);

        for(Object[] objects:objectsList){
            for(Object object:objects){
                System.out.println(object);
            }
        }

        List<StudentCommitDto> studentCommitDtoList = new ArrayList<StudentCommitDto>();
        for(Object[] objects:objectsList){
            studentCommitDtoList.add(new StudentCommitDto(objects));
        }

        /*
        for (HomeworkSubmit submit:homeworkSubmitList){
            studentCommitDtoList.add(new StudentCommitDto(submit,homeworkSubmitRepo.getCommittedCountByHomeworkIdAndStudentId(submit.getHomework().getId(),submit.getStudent().getId())));
        }*/
        //模拟Page
        HashMap<String,List> resMap = new HashMap<String,List>();
        resMap.put("content", studentCommitDtoList);
        return ResultHandler.ok(resMap);
    }

    //作业提交
    @PutMapping(value = "/course/{courseId}/homework/{homeworkId}/submit")
    ResultBean putHomeworkSubmit(HttpSession session,
                                 @PathVariable int courseId,
                                 @PathVariable Integer homeworkId,
                                 @RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "student_id") String studentId,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "remark") String remark){
        Long uploadTime = new Date().getTime();
        Homework homework = homeworkRepo.findOne(homeworkId);
        if(homework == null)
            throw new ResourceNotFoundException();

        Long ddl = homework.getDdl().getTime();

        System.out.println(String.format("ddl= %d uploadTime = %d",ddl,uploadTime));


        if(ddl<uploadTime)
            throw new BaseException(ResultEnum.DEADLINE);

        System.out.println("putHomework");
        Student student = studentRepo.findStudentById(Integer.parseInt(studentId));

        if(student == null){
            System.out.println(String.format("student={%s} is new, creating ...", studentId));
            student = new Student();
            student.setId(Integer.parseInt(studentId));
            student.setName(name);
            //student.setUpdateTime(new Date().getTime());
            studentRepo.save(student);
        }
        System.out.println(String.format("student={%s} created, upload={%s}", studentId,file.getOriginalFilename()));

        System.out.println(String.format("save upload={%s} to mongodb", studentId,file.getOriginalFilename()));
        GridFSInputFile homeworkSubmitFile = mongoDBService.save(file);
        if (homeworkSubmitFile == null) {
            throw new BaseException(ResultEnum.FILE_UPLOAD_FAIL);
        }
        System.out.println(homeworkSubmitFile.getId());
        System.out.println(String.format("upload={%s} saved , get file id={%s}", studentId,file.getOriginalFilename(),homeworkSubmitFile.getId()));
        HomeworkSubmit homeworkSubmit = new HomeworkSubmit();
        homeworkSubmit.setFileKey(homeworkSubmitFile.getId().toString());
        homeworkSubmit.setFileName(file.getOriginalFilename());
        homeworkSubmit.setHomework(new Homework(homeworkId));
        homeworkSubmit.setStudent(new Student(Integer.parseInt(studentId)));
        //homeworkSubmit.setCreateTime(uploadTime);
        //homeworkSubmit.setUpdateTime(uploadTime);

        HomeworkSubmit homeworkSubmitRes = homeworkSubmitRepo.save(homeworkSubmit);

        //HomeworkSubmit homeworkSubmitRes = homeworkSubmitRepo.findHomeworkSubmitById(Integer.parseInt(studentId));

        if(homeworkSubmitRes==null){
            throw new BaseException(ResultEnum.FILE_UPLOAD_FAIL);
        }

        // TODO: 2018/12/9

        return ResultHandler.ok(homeworkSubmitRes);
    }

    //删除提交
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{submit_id}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteHomeworkSubmit(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId, @PathVariable int submitId) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        int res = homeworkSubmitRepo.deleteHomeworkSubmitById(submitId);
        if (res != 1)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(ResultEnum.SUCCESS);
    }

    /*
    @Deprecated
    //修改提交
    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{submit_id}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean updateHomeworkSubmit(@PathVariable int courseId, @RequestBody HomeworkSubmit homeworkSubmit) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        HomeworkSubmit homeworkSubmit1 = homeworkSubmitRepo.findHomeworkSubmitById(homeworkSubmit.getId());
        if(homeworkSubmit1 == null)
            throw new ResourceNotFoundException();
        // TODO: 2018/12/14 修改合法性检查

        //homeworkSubmit.setUpdateTime(new Date().getTime());
        HomeworkSubmit homeworkSubmitResult = homeworkSubmitRepo.save(homeworkSubmit1);
        if(homeworkSubmitResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkSubmitResult);
    }*/

    //下载作业
    ///@PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    //@ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}/submit/{summitId}/download")
    ResponseEntity<InputStreamSource> downloadHomeworkSubmit(HttpSession session,@PathVariable int courseId, @PathVariable int homeworkId, @PathVariable int summitId) throws IOException {
        HomeworkSubmit homeworkSubmit = homeworkSubmitRepo.findOne(summitId);
        if(homeworkSubmit==null)
            throw new ResourceNotFoundException();



        //下载文件
        GridFSDBFile gridFSDBFile = mongoDBService.getById(homeworkSubmit.getFileKey());
        if (gridFSDBFile == null)
            throw new ResourceNotFoundException();

        //File file = new File("C:\\git\\test.txt");
        //InputStreamResource resource = //new InputStreamResource(new FileInputStream(file));
        InputStream inputStream = gridFSDBFile.getInputStream();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attachment;fileName=" + gridFSDBFile.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(gridFSDBFile.getLength())
                .contentType(MediaType.parseMediaType(gridFSDBFile.getContentType()))
                .body(new InputStreamResource(gridFSDBFile.getInputStream()));
    }
}
