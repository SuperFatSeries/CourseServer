package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Integer> {
    @Query("SELECT new Course(course.id,course.name,course.teacherName,course.period) FROM Course course WHERE course.valid = 1")
    Page<Course> findCoursesOverview(Pageable pageable);

    //course.updateTime,course.state,course.info
    //@Query("SELECT new Course(course.id,course.name,course.teacherName,course.capacity,course.period,course.lastModifiedTime,course.info) FROM Course course WHERE course.id = ?1 AND course.valid = true")
    @Query("SELECT new Course(course.id,course.name,course.teacherName,course.capacity,course.period,course.info) FROM Course course WHERE course.id = ?1 AND course.valid = 1")
    Course findCourseDetailByCourseId(Integer course_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Courseware cw SET cw.valid = 0 WHERE cw.id = ?1 AND cw.valid = 1")
    int deleteCourseById(Integer courseId);
}
