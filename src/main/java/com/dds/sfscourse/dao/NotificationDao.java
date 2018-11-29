package com.dds.sfscourse.dao;


import com.dds.sfscourse.model.Notification;

import java.util.List;

public interface NotificationDao{
    List<Notification> findNotificationsByCourseId(Integer integer);
}
