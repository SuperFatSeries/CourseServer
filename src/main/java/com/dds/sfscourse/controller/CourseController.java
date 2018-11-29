package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

//import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping(value = "/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(value = "")
    ResultBean getCourses(HttpSession session) {
        List<Integer> integerList = null;//courseDao.findCoursesOverview();
        return ResultHandler.ok(integerList);
    }

    @GetMapping(value = "/{courseId}")
    ResultBean getCourse(HttpSession session, @PathVariable int courseId) {
        Integer integer = null;//courseDao.findCourseDetailByCourseId(courseId);
        if (integer == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(integer);
    }

    @PutMapping(value = "")
    ResultBean addCourse(@RequestBody Integer integer){
        Integer integerResult = null;//courseDao.save(integer);
        if(integerResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(integerResult);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/{courseId}")
    ResultBean deleteCourse(HttpSession session, @PathVariable int courseId) {
        //courseDao.delete(courseId);
        Integer integer = null;//courseDao.findOne(courseId);
        if (integer != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(integer);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/{courseId}")
    ResultBean updateCourse(@RequestBody Integer integer) {

        //integer.setUpdateTime(new Date().getTime());
        Integer integerResult = null;//courseDao.save(integer);
        if(integerResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(integerResult);
    }
}
