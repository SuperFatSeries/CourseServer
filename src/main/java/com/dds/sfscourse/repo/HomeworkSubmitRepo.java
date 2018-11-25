package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Homework;
import com.dds.sfscourse.entity.HomeworkSubmit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HomeworkSubmitRepo extends JpaRepository<HomeworkSubmit,Integer> {
    @Query(value = "SELECT name,student_id,file_name,MAX(modify_time) as modify_time,COUNT(name) as commit_count FROM homework_submit WHERE homework_id = ?1 GROUP BY student_id",nativeQuery = true)
    Page<HomeworkSubmit> findHomeworkSubmitByHomework(Homework homework, Pageable pageable);

    @Query(value = "SELECT COUNT( DISTINCT student_id) as commited_count FROM homework_submit WHERE homework_id = ?1",nativeQuery = true)
    Integer getCommittedCountByHomeworkId(Integer homework_id);

    @Query(value = "SELECT COUNT(1) FROM homework_submit WHERE homework_id = ?1 and student_id = ?2",nativeQuery = true)
    Integer getCommittedCountByHomeworkIdAndStudentId(Integer homework_id,Integer student_id);
}
