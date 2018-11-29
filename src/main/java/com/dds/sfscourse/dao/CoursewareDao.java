package com.dds.sfscourse.dao;

import com.dds.sfscourse.model.Courseware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoursewareDao {
    Page<Courseware> findCoursewareByCourse(Integer integer, Pageable pageable);

    Courseware findCoursewareByCourseId(Integer integer);

    int deleteCoursewaresByCourse_Id(Integer integer);
}
