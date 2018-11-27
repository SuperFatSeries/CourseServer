package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Notification;
import com.dds.sfscourse.repo.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RestController
public class NotificationController {
    @Autowired
    NotificationRepo notificationRepo;

    @GetMapping(value = "/course/{courseId}/notification")
    ResultBean getNotifications(HttpSession session, @PathVariable Integer courseId) {
        List<Notification> notificationList = notificationRepo.findNotificationsByCourseId(new Course(courseId));
        return ResultHandler.ok(notificationList);
    }

    @GetMapping(value = "/course/{courseId}/notification/{notificationId}")
    ResultBean getNotification(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId) {
        Notification notification = notificationRepo.findOne(notificationId);
        if (notification == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(notification);
    }

    @PutMapping(value = "/course/{courseId}/notification")
    ResultBean addNotification(@RequestBody Notification notification){
        Notification notificationResult = notificationRepo.save(notification);
        if(notificationResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notificationResult);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/notification/{notificationId}")
    ResultBean deleteCourse(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId) {
        notificationRepo.delete(notificationId);
        Notification notification = notificationRepo.findOne(notificationId);
        if (notification != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notification);
    }

    @PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/course/{courseId}/notification")
    ResultBean  updateCourse(@RequestBody Notification notification) {

        notification.setUpdateTime(new Date().getTime());
        Notification notificationResult = notificationRepo.save(notification);
        if(notificationResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notificationResult);
    }
}
