package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification,Integer> {
    @Query(value = "FROM Notification n WHERE n.course.id = ?1 AND n.valid = 1")
    Page<Notification> findNotificationsByCourseId(Integer courseId, Pageable pageable);

    @Query(value = "FROM Notification n WHERE n.course.id = ?1 AND n.valid = 1")
    Notification findNotificationById(Integer ide);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Notification n SET n.valid = 0 WHERE n.id = ?1 AND n.valid = 1")
    int deleteNotificationById(Integer id);
}
