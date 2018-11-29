package com.dds.sfscourse.dao;


import com.dds.sfscourse.model.Homework;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface HomeworkDao{
    Page<Homework> findHomeworkByCourse(Integer integer, Pageable pageable);

    Homework findHomeworkDetailByCourseId(Integer integer_id);
}
