package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.repo.AdminRoleRepo;
import com.dds.sfscourse.repo.CourseRepo;
import com.dds.sfscourse.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

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

    @GetMapping(value = "")
    ResultBean getCourses(HttpSession session) {
        List<Course> courseList = courseRepo.findCoursesOverview();
        return ResultHandler.ok(courseList);
    }

    @GetMapping(value = "/{courseId}")
    ResultBean getCourse(HttpSession session, @PathVariable int courseId) {
        Course course = courseRepo.findCourseDetailByCourseId(courseId);
        if (course == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(course);
    }

    @PutMapping(value = "")
    ResultBean addCourse(@RequestBody Course course){
        Course courseResult = courseRepo.save(course);
        if(courseResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(courseResult);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/{courseId}")
    ResultBean deleteCourse(HttpSession session, @PathVariable int courseId) {
        courseRepo.delete(courseId);
        Course course = courseRepo.findOne(courseId);
        if (course != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(course);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/{courseId}")
    ResultBean updateCourse(@RequestBody Course course) {

        course.setUpdateTime(new Date().getTime());
        Course courseResult = courseRepo.save(course);
        if(courseResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(courseResult);
    }
}
