package com.dds.sfscourse.service.Impl;

import com.dds.sfscourse.model.Course;
import com.dds.sfscourse.page.PageRequest;
import com.dds.sfscourse.page.PageResult;
import com.dds.sfscourse.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    @Override
    public int save(Course record) {
        return 0;
    }

    @Override
    public int delete(Course record) {
        return 0;
    }

    @Override
    public int delete(List<Course> records) {
        return 0;
    }

    @Override
    public Course findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return null;
    }
}
