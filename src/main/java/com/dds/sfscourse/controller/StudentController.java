package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceExistException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.Notification;
import com.dds.sfscourse.entity.Student;
import com.dds.sfscourse.repo.StudentRepo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class StudentController {
    @Autowired
    StudentRepo studentRepo;

    //学生列表
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @GetMapping(value = "/student")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean getStudents(@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable){
        Page<Student> studentPage = studentRepo.findStudents(pageable);
        return ResultHandler.ok(studentPage);
    }

    //学生详细
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @GetMapping(value = "/student/{studentId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean getStudent(HttpSession session, @PathVariable int studentId) {
        Student student = studentRepo.findStudentById(studentId);
        if (student == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(student);
    }

    //添加学生
    @PutMapping(value = "/student")
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean addStudent(@RequestBody Student student){
        if(studentRepo.findStudentById(student.getId())!=null)
            throw new ResourceExistException();


        // TODO: 2018/12/14 合法性判断
        //System.out.println("");

        Student studentResult = studentRepo.save(student);
        if(studentResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(studentResult);
    }

    //修改学生
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping(value = "/student/{studentId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean postStudent(@PathVariable int studentId, @RequestBody Student student) {
        if(studentRepo.findStudentById(student.getId())==null)
            throw new ResourceNotFoundException();

        // TODO: 2018/12/14 合法性判断

        Student studentResult=studentRepo.save(student);

        if (studentResult == null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(studentResult);
    }

    //删除学生
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping(value = "/student/{studentId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteCourse(HttpSession session, @PathVariable int studentId) {
        int res = studentRepo.deleteStudentById(studentId);
        if (res != 1)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(ResultEnum.SUCCESS);
    }
}
