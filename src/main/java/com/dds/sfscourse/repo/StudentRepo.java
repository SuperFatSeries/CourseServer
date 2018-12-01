package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Courseware;
import com.dds.sfscourse.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepo extends JpaRepository<Student,Integer> {
    @Query(value = "FROM Student n WHERE n.valid = 1")
    Page<Student> findStudents(Pageable pageable);

    @Query(value = "FROM Student n WHERE n.id = ?1 AND n.valid = 1")
    Student findStudentById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Student n SET n.valid = 0 WHERE n.id = ?1 AND n.valid = 1")
    int deleteStudentById(Integer id);
}
