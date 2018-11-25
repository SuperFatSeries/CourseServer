package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.AdminCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminCourseRepo extends JpaRepository<AdminCourse,Integer> {
    @Query(value = "SELECT * FROM admin_course WHERE admin_id = ?1 AND state = 0",nativeQuery = true)
    List<AdminCourse> getAdminCoursesByAdminId(Integer adminId);
}
