package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Integer> {
    @Query(value = "SELECT * FROM notification WHERE course_id = ?1",nativeQuery = true)
    List<Notification> findNotificationsByCourseId(Course course);
}
