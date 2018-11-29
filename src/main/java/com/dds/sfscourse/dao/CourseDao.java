package com.dds.sfscourse.dao;


import com.dds.sfscourse.model.Course;

import java.util.List;

public interface CourseDao {
    Course selectByPrimaryKey(Long id);

    List<Course> findAll();

    List<Course> findPage();
}
