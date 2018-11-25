package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminRoleRepo extends JpaRepository<AdminRole,Integer> {
    @Query(value = "SELECT * FROM admin_role WHERE admin_id = ?1",nativeQuery = true)
    List<AdminRole> findAdminRolesByAdminId(Integer adminId);
}
