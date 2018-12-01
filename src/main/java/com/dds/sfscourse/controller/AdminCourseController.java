package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.AdminCourse;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.repo.AdminRoleRepo;
import com.dds.sfscourse.security.JwtUserDetails;
import com.dds.sfscourse.util.JwtTokenUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminCourseController {
    @Autowired
    AdminCourseRepo adminCourseRepo;

    @Autowired
    AdminRoleRepo adminRoleRepo;

    //查看admin管理的课程
    @GetMapping(value = "/admin/{adminId}/course")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher')")
    ResultBean getAdminCourse(HttpSession session,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable) {

        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println(String.format("SecurityContextHolder.getContext().getAuthentication().getPrincipal()=%s",SecurityContextHolder.getContext().getAuthentication().getPrincipal()));

        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        Page<AdminCourse> coursePage = adminCourseRepo.findAdminCoursesByAdminId(admin_id,pageable);
        return ResultHandler.ok(coursePage);
    }

    //权限管理 目前只实现 赋予|删除 课程管理权限
    //赋予 adminId 课程courseId管理权限
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher')")
    @PutMapping(value = "/admin/{adminId}/course/{courseId}")
    ResultBean putAuthority(@PathVariable Integer adminId,@PathVariable Integer courseId){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        int roleIdA = adminRoleRepo.findAdminRoleListByAdminId(admin_id).get(0).getRole().getId();
        int roleIdB = adminRoleRepo.findAdminRoleListByAdminId(adminId).get(0).getRole().getId();
        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null&&roleIdA!=0)
            throw new ForbiddenException();



        if(roleIdA==0){

            //adminCourseRepo.save();
        }
        else if(roleIdA>roleIdB){

        }else{
            throw new ForbiddenException();
        }


        return ResultHandler.ok(ResultEnum.SUCCESS);
    }

    //删除 adminId 课程courseId管理权限
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher')")
    @DeleteMapping(value = "/admin/{adminId}/course/{courseId}")
    ResultBean deleteAuthority(@PathVariable Integer adminId,@PathVariable Integer courseId){


        return ResultHandler.ok(ResultEnum.SUCCESS);
    }
}
