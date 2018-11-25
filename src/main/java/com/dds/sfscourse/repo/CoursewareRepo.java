package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Courseware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CoursewareRepo extends JpaRepository<Courseware,Integer> {
    Page<Courseware> findCoursewareByCourse(Course course, Pageable pageable);

    @Query(value = "SELECT * FROM courseware WHERE course_id = ?1",nativeQuery = true)
    Courseware findCoursewareByCourseId(Course course);

    @Query(value = "DELETE * FROM courseware WHERE course_id = ?1",nativeQuery = true)
    int deleteCoursewaresByCourse_Id(Course course);
}
