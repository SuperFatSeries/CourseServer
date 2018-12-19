package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Homework;
import com.dds.sfscourse.entity.HomeworkSubmit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HomeworkSubmitRepo extends JpaRepository<HomeworkSubmit,Integer>, JpaSpecificationExecutor {

    // TODO: 2018/12/13 复杂（取最新的update_time Group by student_id）查询暂时不知道如何实现 
    @Query(value = "SELECT h_s.id,h_s.student_id,s.name,h_s.file_name,h_s.commit_count,h_s.update_time,h_s.remark " +
            "           FROM (SELECT ANY_VALUE(id) AS id,ANY_VALUE(remark) AS remark,ANY_VALUE(student_id) AS student_id ,ANY_VALUE(file_name) AS file_name,MAX(last_modified_time) AS update_time,COUNT(*) AS commit_count " +
            "FROM homework_submit " +
            "WHERE homework_id = ?1 AND valid = 1 GROUP BY student_id) AS h_s " +
            "LEFT JOIN student AS s ON s.id = h_s.student_id",nativeQuery = true)
    //@Query(value = "SELECT MAX (hs.student),MAX(hs.fileName),MAX(hs.updateTime),COUNT() FROM HomeworkSubmit hs WHERE hs.homework = ?1 GROUP BY hs.student")
    List<Object[]> findHomeworkSubmitByHomework(Homework homework);

    @Query(value = "FROM HomeworkSubmit hs WHERE hs.student.id = ?1 AND hs.valid = 1")
    HomeworkSubmit findHomeworkSubmitById(Integer studentId);

    @Query(value = "SELECT COUNT(hs.id) FROM HomeworkSubmit hs WHERE hs.homework.course.id = ?1 AND hs.valid = 1")
    Integer findHomeworkSubmitCountByCourseId(Integer courseId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE HomeworkSubmit hs SET hs.valid = 0 WHERE hs.id = ?1 AND hs.valid = 1")
    int deleteHomeworkSubmitById(Integer id);

    @Query(value = "SELECT COUNT( DISTINCT student_id) as commit_count FROM homework_submit WHERE homework_id = ?1 AND valid = 1",nativeQuery = true)
    Integer getCommittedCountByHomeworkId(Integer homework_id);



    //@Query(value = "SELECT COUNT(1) FROM homework_submit WHERE homework_id = ?1 and student_id = ?2",nativeQuery = true)
    //Integer getCommittedCountByHomeworkIdAndStudentId(Integer homework_id,Integer student_id);
}
