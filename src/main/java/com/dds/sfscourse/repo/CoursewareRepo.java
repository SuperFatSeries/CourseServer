package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Courseware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CoursewareRepo extends JpaRepository<Courseware,Integer> {
    //Page<Courseware> findCoursewareByCourse(Course course, Pageable pageable);

    @Query(value = "FROM Courseware courseware WHERE courseware.course.id = ?1 AND courseware.valid = 1")
    Page<Courseware> findCoursewaresByCourseId(Integer courseId, Pageable pageable);

    @Query(value = "FROM Courseware courseware WHERE courseware.id = ?1 AND courseware.valid = 1")
    Courseware findCoursewaresById(Integer courseId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Courseware cw SET cw.valid = 0 WHERE cw.id = ?1 AND cw.valid = 1")
    int deleteCoursewaresById(Integer coursewareId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Courseware cw SET cw.valid = 0 WHERE cw.course.id = ?1 AND cw.valid = 1")
    int deleteCoursewaresByCourseId(Integer courseId);

    /*
    @Deprecated
    @Modifying
    @Transactional
    @Query(value = "UPDATE Courseware cw SET cw.valid = 0 WHERE cw.course_id IN (?1) AND cw.valid = 1")
    int deleteCoursewaresByCourseIdList(List<Integer> coursewareIdList);*/


}
