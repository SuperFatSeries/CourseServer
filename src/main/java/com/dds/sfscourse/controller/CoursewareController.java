package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.Exception.ResourceExistException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.Admin;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Courseware;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.repo.AdminRepo;
import com.dds.sfscourse.repo.CourseRepo;
import com.dds.sfscourse.repo.CoursewareRepo;
import com.dds.sfscourse.security.GrantedAuthorityImpl;
import com.dds.sfscourse.security.JwtUserDetails;
import com.dds.sfscourse.service.MongoDBService;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

@RestController
public class CoursewareController {
    @Autowired
    private CoursewareRepo coursewareRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private AdminCourseRepo adminCourseRepo;

    @Autowired
    MongoDBService mongoDBService;

    //课件列表
    @GetMapping(value = "/course/{courseId}/ware")
    ResultBean getCoursewares(HttpSession session, @PathVariable int courseId,@PageableDefault(value = 100, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable){
        //int page=0,pageSize=10;
        Page<Courseware> coursewarePage = coursewareRepo.findCoursewaresByCourseId(courseId,pageable);

        return ResultHandler.ok(coursewarePage);
    }

    //课件详细
    @GetMapping(value = "/course/{courseId}/ware/{coursewareId}")
    ResultBean getCourseware(HttpSession session, @PathVariable int courseId,@PathVariable int coursewareId,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable){
        Courseware courseware = coursewareRepo.findCoursewaresById(coursewareId);

        return ResultHandler.ok(courseware);
    }

    // 新增课件
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @PutMapping(value = "/course/{courseId}/ware")
    ResultBean uploadCourseware(HttpSession session,
                             @PathVariable Integer courseId,
                             @RequestParam(value = "file") MultipartFile file,
                             @RequestParam(value = "remark") String remark){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        GrantedAuthorityImpl grantedAuthority = (GrantedAuthorityImpl)jwtUserDetails.getAuthorities().toArray()[0];
        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null&&!grantedAuthority.getAuthority().equals("admin"))
            throw new ForbiddenException();

        //  2018/12/13  删除时更新update_time Done

        //  2018/12/13 根据上下文获取admin Done

        //  2018/12/13 检查admin权限 Done

        //上传文件
        GridFSInputFile homeworkSubmitFile = mongoDBService.save(file);
        if (homeworkSubmitFile == null) {
            throw new BaseException(ResultEnum.FILE_UPLOAD_FAIL);
        }

        Courseware courseware=coursewareRepo.findCoursewaresById(courseId);

        if(courseware!=null)
            throw new ResourceExistException();

        //新建课件

        // 2018/12/13 增加基础字段(create_time create_id | update_time update_id | state) Done
        courseware = new Courseware();
        courseware.setRemark(remark);
        courseware.setCourse(new Course(courseId));
        courseware.setFileKey(homeworkSubmitFile.getId().toString());
        courseware.setFileName(file.getOriginalFilename());
        coursewareRepo.save(courseware);

        return ResultHandler.ok(courseware);
    }

    //删除指定课程所有课件？
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/ware")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteCoursewares(HttpSession session, @PathVariable int courseId){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        coursewareRepo.deleteCoursewaresByCourseId(courseId);
        return ResultHandler.ok(ResultEnum.SUCCESS);
    }


    //修改课件
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/ware/{coursewareId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean postCourseware(HttpSession session,
                                @PathVariable Integer courseId,
                                @RequestBody Courseware courseware,
                                @RequestParam(value = "file") MultipartFile file){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        Courseware courseware1=coursewareRepo.findCoursewaresById(courseId);
        if(courseware1==null)
            throw new ResourceNotFoundException();


        // TODO: 2018/12/15  检查更新字段
        if(file!=null){
            //上传文件
            GridFSInputFile homeworkSubmitFile = mongoDBService.save(file);
            if (homeworkSubmitFile == null)
                throw new BaseException(ResultEnum.FILE_UPLOAD_FAIL);
            courseware1.setFileName(file.getOriginalFilename());
            courseware1.setFileKey(homeworkSubmitFile.getId().toString());
        }

       if(courseware.getRemark()!=null){
           courseware1.setRemark(courseware.getRemark());
       }
       Courseware coursewareRes = coursewareRepo.save(courseware1);

        return ResultHandler.ok(coursewareRes);
    }
    //删除课件
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/ware/{coursewareId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteCourseware(HttpSession session, @PathVariable int courseId, @PathVariable Integer coursewareId){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        Courseware courseware = coursewareRepo.findCoursewaresById(coursewareId);
        if(courseware==null)
            throw new ResourceNotFoundException();

        int coursewareRes = coursewareRepo.deleteCoursewaresById(coursewareId);

        if (coursewareRes == 0)
            throw new BaseException(ResultEnum.FILE_DELETE_FAIL);

        return ResultHandler.ok(ResultEnum.SUCCESS);
    }

    //下载课件
    @GetMapping(value = "/course/{courseId}/ware/{coursewareId}/download")
    ResponseEntity<InputStreamSource> downloadCourseware(HttpSession session, @PathVariable int courseId, @PathVariable int coursewareId) throws IOException {
        Courseware courseware = coursewareRepo.findOne(coursewareId);
        if(courseware==null)
            throw new ResourceNotFoundException();

        //下载文件
        GridFSDBFile gridFSDBFile = mongoDBService.getById(courseware.getFileKey());
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
