package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.StudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StudentCourseRepo extends JpaRepository<StudentCourse,Integer> {
    @Query(value = "FROM StudentCourse ac WHERE ac.student.id = ?1 AND valid = 1")
    Page<StudentCourse> findAStudentCoursesByStudentId(Integer studentId, Pageable pageable);

    @Query(value = "FROM StudentCourse ac WHERE ac.course.id = ?1 AND valid = 1")
    Page<StudentCourse> findStudentCoursesByCourseId(Integer roleId,Pageable pageable);

    @Query(value = "FROM StudentCourse ac WHERE ac.id = ?1 AND valid = 1")
    StudentCourse findStudentCoursesById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE StudentCourse ac SET ac.valid = 0 WHERE ac.id = ?1 AND ac.valid = 1")
    int deleteStudentCourseById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE StudentCourse ac SET ac.valid = 0 WHERE ac.student.id = ?1 AND ac.valid = 1")
    int deleteStudentCourseByStudentId(Integer studentId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE StudentCourse ac SET ac.valid = 0 WHERE ac.course.id = ?1 AND ac.valid = 1")
    int deleteStudentCourseByCourseId(Integer courseId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE StudentCourse ac SET ac.valid = 0 WHERE ac.student.id = ?1 AND ac.course.id = ?2 AND ac.valid = 1")
    int deleteStudentCourseByStudentIdAndCourseId(Integer studentId,Integer courseId);
}
