package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Institute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface InstituteRepo extends JpaRepository<Institute,Integer> {
    @Query(value = "FROM Institute n WHERE n.valid = 1")
    Page<Institute> findInstitutes(Pageable pageable);

    @Query(value = "FROM Institute n WHERE n.id = ?1 AND n.valid = 1")
    Institute findInstituteById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Institute n SET n.valid = 0 WHERE n.id = ?1 AND n.valid = 1")
    int deleteInstituteById(Integer id);
}
