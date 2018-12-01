package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.AdminCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminCourseRepo extends JpaRepository<AdminCourse,Integer> {
    @Query(value = "FROM AdminCourse ac WHERE ac.admin.id = ?1 AND valid = 1")
    Page<AdminCourse> findAdminCoursesByAdminId(Integer adminId,Pageable pageable);

    @Query(value = "FROM AdminCourse ac WHERE ac.course.id = ?1 AND valid = 1")
    Page<AdminCourse> findAdminCoursesByCourseId(Integer courseId,Pageable pageable);

    @Query(value = "FROM AdminCourse ac WHERE ac.admin.id = ?1 AND ac.course.id = ?2 AND valid = 1")
    AdminCourse findAdminCourseByAdminIdAndCourseId(Integer adminId,Integer courseId);

    @Query(value = "FROM AdminCourse ac WHERE ac.id = ?1 AND valid = 1")
    AdminCourse findAdminCourseById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminCourse ac SET ac.valid = 0 WHERE ac.id = ?1 AND ac.valid = 1")
    int deleteAdminCourseById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminCourse ac SET ac.valid = 0 WHERE ac.admin.id = ?1 AND ac.valid = 1")
    int deleteAdminCourseByAdminId(Integer adminId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminCourse ac SET ac.valid = 0 WHERE ac.course.id = ?1 AND ac.valid = 1")
    int deleteAdminCourseByCourseId(Integer courseId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminCourse ac SET ac.valid = 0 WHERE ac.admin.id = ?1 AND ac.course.id = ?2 AND ac.valid = 1")
    int deleteAdminCourseByAdminIdAndCourseId(Integer adminId,Integer courseId);
}
