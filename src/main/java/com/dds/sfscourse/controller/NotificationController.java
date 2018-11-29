package com.dds.sfscourse.controller;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ResourceNotFoundException;
import com.dds.sfscourse.base.ResultBean;
import com.dds.sfscourse.base.ResultEnum;
import com.dds.sfscourse.base.ResultHandler;
import com.dds.sfscourse.dao.NotificationDao;
import com.dds.sfscourse.model.Notification;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class NotificationController {
    NotificationDao notificationDao;

    @GetMapping(value = "/course/{integerId}/notification")
    ResultBean getNotifications(HttpSession session, @PathVariable Integer integerId) {
        List<Notification> notificationList = null;// notificationDao.findNotificationsByCourseId(new Integer(integerId));
        return ResultHandler.ok(notificationList);
    }

    @GetMapping(value = "/course/{courseId}/notification/{notificationId}")
    ResultBean getNotification(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId) {
        Notification notification = null;//notificationDao.findOne(notificationId);
        if (notification == null)
            throw new ResourceNotFoundException();
        return ResultHandler.ok(notification);
    }

    @PutMapping(value = "/course/{courseId}/notification")
    ResultBean addNotification(@RequestBody Notification notification){
        Notification notificationResult = null;//notificationDao.save(notification);
        if(notificationResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notificationResult);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @DeleteMapping(value = "/course/{courseId}/notification/{notificationId}")
    ResultBean deleteCourse(HttpSession session, @PathVariable int courseId, @PathVariable int notificationId) {
        //notificationDao.delete(notificationId);
        Notification notification = null;//notificationDao.findOne(notificationId);
        if (notification != null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notification);
    }

    //@PreAuthorize("hasAuthority('admin') AND hasAuthority('teacher') AND hasAuthority('ta')")
    @PostMapping(value = "/{courseId}")
    ResultBean updateCourse(@RequestBody Notification notification) {

        //notification.setUpdateTime(new Date().getTime());
        Notification notificationResult = null;// notificationDao.save(notification);
        if(notificationResult ==null)
            throw new BaseException(ResultEnum.FAIL);
        return ResultHandler.ok(notificationResult);
    }
}
