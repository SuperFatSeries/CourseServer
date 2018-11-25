package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRepo extends JpaRepository<StudentCourse,Integer> {
}
