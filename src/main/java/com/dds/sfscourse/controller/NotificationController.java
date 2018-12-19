package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.Exception.ResourceExistException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.config.WebSecurityConfig;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Notification;
import com.dds.sfscourse.repo.AdminCourseRepo;
import com.dds.sfscourse.repo.CourseRepo;
import com.dds.sfscourse.repo.NotificationRepo;
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
import java.util.Date;
import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    NotificationRepo notificationRepo;

    @Autowired
    AdminCourseRepo adminCourseRepo;

    @Autowired
    CourseRepo courseRepo;

    //通知列表
    @GetMapping(value = "/course/{courseId}/notification")
    ResultBean getNotifications(HttpSession session, @PathVariable Integer courseId,@PageableDefault(value = 10, sort = { "id" }, direction = Sort.Direction.DESC)
            Pageable pageable){
        Page<Notification> notificationPage = notificationRepo.findNotificationsByCourseId(courseId,pageable);
        System.out.println("JWT:" + SecurityContextHolder.getContext().getAuthentication());
        return ResultHandler.ok(notificationPage);
    }

    //通知详细
    @GetMapping(value = "/course/{courseId}/notification/{notificationId}")
    ResultBean getNotification(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId) {
        Notification notification = notificationRepo.findNotificationById(notificationId);
        if (notification == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(notification);
    }

    //添加通知
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @PutMapping(value = "/course/{courseId}/notification")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean addNotification(@PathVariable int courseId,@RequestBody Notification notification){
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        assert notification.getId() == null;
        // TODO: 2018/12/14 合法性判断

        notification.setCourse(courseRepo.findCourseDetailByCourseId(courseId));
        
        Notification notificationResult = notificationRepo.save(notification);
        if(notificationResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notificationResult);
    }

    //修改通知
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/notification/{notificationId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean postNotification(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId,@RequestBody Notification notification) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        //if(jwtUserDetails.getAuthorities().contains()){

        //}
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        Notification notification1 = notificationRepo.findNotificationById(notification.getId());

        if(notification1==null)
            throw new ResourceNotFoundException();

        if(notification.getTitle()!=null)
            notification1.setTitle(notification.getTitle());

        if(notification.getContent()!=null)
            notification1.setContent(notification.getContent());

        // TODO: 2018/12/14 合法性判断

        Notification notificationResult = notificationRepo.save(notification);
        return ResultHandler.ok(notificationResult);
    }

    //删除通知
    @PreAuthorize("hasAuthority('admin') OR hasAuthority('teacher') OR hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/notification/{notificationId}")
    @ApiImplicitParams({@ApiImplicitParam(name= WebSecurityConfig.JWT_TOKEN_HEADER_PARAM,value="JWT token",required=true,paramType="headers"),})
    ResultBean deleteCourse(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId) {
        JwtUserDetails jwtUserDetails =
                (JwtUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer admin_id = Integer.parseInt(jwtUserDetails.getUsername());

        //检查admin 管理权限
        if(adminCourseRepo.findAdminCourseByAdminIdAndCourseId(admin_id,courseId)==null)
            throw new ForbiddenException();

        int res = notificationRepo.deleteNotificationById(notificationId);
        Notification notification = notificationRepo.findNotificationById(notificationId);
        if (res != 1)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(ResultEnum.SUCCESS);
    }
}
