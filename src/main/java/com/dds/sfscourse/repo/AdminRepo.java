package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    @Query(value = "SELECT * FROM admin WHERE email = ?1",nativeQuery = true)
    Admin findAdminByEmail(String email);
}
