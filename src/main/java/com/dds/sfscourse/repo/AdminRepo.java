package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    @Query(value = "FROM Admin a WHERE a.email = ?1 AND valid = 1")
    Admin findAdminByEmail(String email);

    @Query(value = "FROM Admin a WHERE a.valid = 1")
    Page<Admin> findAdmins(Pageable pageable);

    @Query(value = "FROM Admin a WHERE a.valid = 0")
    Page<Admin> findFreezeAdmins(Pageable pageable);

    @Query(value = "FROM Admin a WHERE a.id = ?1 AND a.valid = 1")
    Admin findAdminById(Integer id);

    @Query(value = "FROM Admin a WHERE a.id = ?1 AND a.valid = 0")
    Admin findFreezeAdminById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Admin a SET a.valid = 0 WHERE a.id = ?1 AND a.valid = 1")
    int deleteAdminById(Integer id);
}
