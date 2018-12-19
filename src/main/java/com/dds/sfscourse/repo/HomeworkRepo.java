package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Course;
import com.dds.sfscourse.entity.Homework;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HomeworkRepo extends JpaRepository<Homework,Integer> {
    Page<Homework> findHomeworkByCourse(Course course, Pageable pageable);

    @Query("SELECT new Homework (homework.id, homework.name, homework.ddl, homework.course, homework.admin, homework.requirement, homework.commitCount) FROM Homework homework WHERE homework.course.id = ?1 AND homework.valid = 1")
    List<Homework> findHomeworksByCourseId(Integer course_id);

    @Query("SELECT new Homework (homework.id, homework.name, homework.ddl, homework.course, homework.admin, homework.requirement, homework.commitCount) FROM Homework homework WHERE homework.id = ?1 AND homework.valid = 1")
    Homework findHomeworkDetailByHomeworkId(Integer homeworkId);

    @Query("SELECT COUNT(homework.id) FROM Homework homework WHERE homework.course.id = ?1 AND homework.valid = 1")
    Integer findHomeworkCountByCourseId(Integer course_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Homework h SET h.valid = 0 WHERE h.course.id = ?1 AND h.valid = 1")
    int deleteHomeworkByCourseId(Integer courseId);


    @Modifying
    @Transactional
    @Query(value = "UPDATE Homework h SET h.valid = 0 WHERE h.id = ?1 AND h.valid = 1")
    int deleteHomeworkByHomeworkId(Integer id);
}
