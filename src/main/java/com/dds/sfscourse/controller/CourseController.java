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
import com.dds.sfscourse.entity.AdminCourse;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.repo.*;
import com.dds.sfscourse.security.JwtUserDetails;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/course")
public class CourseController {
    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AdminRoleRepo adminRoleRepo;

    @Autowired
    private AdminCourseRepo adminCourseRepo;

    @Autowired
    private StudentCourseRepo studentCourseRepo;

    //课程列表
    @GetMapping(value = "")
    ResultBean getCourses(HttpSession session,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable) {
        System.out.println(pageable);
        Page<Course> coursePage = courseRepo.findCoursesOverview(pageable);
        return ResultHandler.ok(coursePage);
    }

    //课程信息
    @GetMapping(value = "/{courseId}")
    ResultBean getCourse(HttpSession session, @PathVariable Integer courseId) {
        Course course = courseRepo.findCourseDetailByCourseId(courseId);
        if (course == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(course);
    }

    //新增课程
    @PutMapping(value = "")
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean postCourse(@RequestBody Course course){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());
        //
        Course courseResult = courseRepo.findCourseDetailByCourseId(course.getId());
        if(courseResult !=null)
            throw new ResourceExistException();

        // TODO: 2018/12/14 属性合法检查

        Course course1 =courseRepo.save(course);

        //创建者获得权限
        AdminCourse adminCourse = new AdminCourse();
        adminCourse.setAdmin(new Admin(admin_id));
        adminCourse.setCourse(courseResult);

        AdminCourse adminCourseResult = adminCourseRepo.save(adminCourse);
        //course.setState(0);
        //course.setCreatedTime(new Date());
        if(adminCourseResult ==null)
            throw new BaseException(ResultEnum.FAIL);

        //courseRepo.save();

        return ResultHandler.ok(course1);
    }

    //删除课程
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @DeleteMapping(value = "/{courseId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteCourse(HttpSession session, @PathVariable int courseId) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        courseRepo.deleteCourseById(courseId);
        Course course = courseRepo.findCourseDetailByCourseId(courseId);
        if (course != null)
            throw new BaseException(ResultEnum.FAIL);

        //级联删除AdminCourse StudentCourse
        adminCourseRepo.deleteAdminCourseByAdminIdAndCourseId(admin_id,courseId);
        studentCourseRepo.deleteStudentCourseByCourseId(courseId);

        return ResultHandler.ok(ResultEnum.SUCCESS);
    }

    //更新课程
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @PostMapping(value = "/{courseId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean updateCourse(@RequestBody Course course) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,course.getId())==null)
            throw new ForbiddenException();

        // TODO: 2018/12/14 属性合法检查

        Course courseResult = courseRepo.save(course);
        if(courseResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(courseResult);
    }
}
