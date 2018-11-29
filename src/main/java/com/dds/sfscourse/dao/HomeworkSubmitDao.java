package com.dds.sfscourse.dao;

import com.dds.sfscourse.model.Homework;
import com.dds.sfscourse.model.HomeworkSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HomeworkSubmitDao {
    Page<HomeworkSubmit> findHomeworkSubmitByHomework(Homework homework, Pageable pageable);

    Integer getCommittedCountByHomeworkId(Integer homework_id);
}
