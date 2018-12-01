package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.dto.HomeworkDto;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Homework;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.repo.HomeworkRepo;
import com.dds.sfscourse.repo.HomeworkSubmitRepo;
import com.dds.sfscourse.security.GrantedAuthorityImpl;
import com.dds.sfscourse.security.JwtUserDetails;
import com.dds.sfscourse.service.MongoDBService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class HomeworkController {
    @Autowired
    private HomeworkRepo homeworkRepo;

    @Autowired
    private HomeworkSubmitRepo homeworkSubmitRepo;

    @Autowired
    private AdminCourseRepo adminCourseRepo;

    @Autowired
    MongoDBService mongoDBService;

    //作业列表
    @GetMapping(value = "/course/{courseId}/homework")
    ResultBean getHomeworks(HttpSession session, @PathVariable Integer courseId,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable){

        Page<Homework> homeworkPage = homeworkRepo.findHomeworkByCourse(new Course(courseId),pageable);


        List<Homework> homeworkList = homeworkPage.getContent();

        List<HomeworkDto> homeworkDtoList = new ArrayList<HomeworkDto>();

        for (Homework h:homeworkList){
            homeworkDtoList.add(new HomeworkDto(h,homeworkSubmitRepo.getCommittedCountByHomeworkId(h.getId())));
        }
        //模拟Page
        HashMap<String,List> resMap = new HashMap<String,List>();
        resMap.put("content", homeworkDtoList);

        return ResultHandler.ok(resMap);
    }

    //作业详细
    @GetMapping(value = "/course/{courseId}/homework/{homeworkId}")
    ResultBean getHomework(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId) {
        Homework homework = homeworkRepo.findHomeworkDetailByHomeworkId(homeworkId);
        if (homework == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(new HomeworkDto(homework));
    }

    //发布作业
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @PutMapping(value = "/course/{courseId}/homework")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean addHomework(@PathVariable int courseId, @RequestBody Homework homework){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        GrantedAuthorityImpl grantedAuthority = (GrantedAuthorityImpl)jwtUserDetails.getAuthorities().toArray()[0];
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null&&!grantedAuthority.getAuthority().equals("admin"))
            throw new ForbiddenException();

        assert homework.getId()==null;
        Homework homeworkResult = homeworkRepo.save(homework);
        if(homeworkResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkResult);
    }

    //删除作业
    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/homework/{homeworkId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteHomework(HttpSession session, @PathVariable int courseId, @PathVariable int homeworkId) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        int res = homeworkRepo.deleteHomeworkByHomeworkId(homeworkId);
        if (res != 1)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(ResultEnum.SUCCESS);
    }

    //修改作业
    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/homework/{homeworkId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean updateHomework(@PathVariable int courseId, @RequestBody Homework homework) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        Homework homework1 = homeworkRepo.findHomeworkDetailByHomeworkId(homework.getId());
        if(homework1==null)
            throw new ResourceNotFoundException();

        if(homework.getDdl()!=null){
            homework1.setDdl(homework.getDdl());
        }
        if(homework.getRequirement()!=null){
            homework1.setRequirement(homework.getRequirement());
        }
        if(homework.getName()!=null){
            homework1.setName(homework.getName());
        }
        // TODO: 2018/12/14 异常值检验 90%
        
        Homework homeworkResult = homeworkRepo.save(homework1);
        if(homeworkResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(homeworkResult);
    }
}
