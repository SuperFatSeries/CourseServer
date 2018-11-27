package com.dds.sfscourse.controller;

import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.AdminCourse;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.security.JwtUserDetails;
import com.dds.sfscourse.util.JwtTokenUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminCourseController {
    @Autowired
    AdminCourseRepo adminCourseRepo;

    @GetMapping(value = "/admin/course")
    @ApiImplicitParams({
            @ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),
    })
    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    ResultBean getAdminCourse(HttpSession session) {

        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        List<AdminCourse> courseList = adminCourseRepo.getAdminCoursesByAdminId();
        return ResultHandler.ok(courseList);
    }
}
