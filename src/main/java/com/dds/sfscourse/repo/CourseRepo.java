package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Integer> {
    @Query("SELECT new Course(course.id,course.name,course.teacherName,course.period) FROM Course course")
    List<Course> findCoursesOverview();

    //course.updateTime,course.state,course.info
    @Query("SELECT new Course(course.id,course.name,course.teacherName,course.capacity,course.period,course.updateTime,course.info) FROM Course course WHERE course.id = ?1")
    Course findCourseDetailByCourseId(Integer course_id);
}
