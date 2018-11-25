package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeworkRepo extends JpaRepository<Homework,Integer> {
    Page<Homework> findHomeworkByCourse(Course course, Pageable pageable);

    @Query("SELECT new Homework (homework.id, homework.name, homework.ddl, homework.course, homework.admin,homework.updateTime, homework.requirement, homework.commitCount) FROM Homework homework WHERE homework.id = ?1")
    Homework findHomeworkDetailByCourseId(Integer course_id);
}
