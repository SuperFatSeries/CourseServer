package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<Student,Integer> {
}
