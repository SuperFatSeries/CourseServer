package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.AdminRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminRoleRepo extends JpaRepository<AdminRole,Integer> {

    @Query(value = "FROM AdminRole ac WHERE ac.admin.id = ?1 AND valid = 1")
    Page<AdminRole> findAdminRolesByAdminId(Integer adminId,Pageable pageable);

    @Query(value = "FROM AdminRole ac WHERE ac.admin.id = ?1 AND valid = 1")
    List<AdminRole> findAdminRoleListByAdminId(Integer adminId);

    @Query(value = "FROM AdminRole ac WHERE ac.role.id = ?1 AND valid = 1")
    Page<AdminRole> findAdminRolesByRoleId(Integer roleId,Pageable pageable);

    @Query(value = "FROM AdminRole ac WHERE ac.id = ?1 AND valid = 1")
    AdminRole findAdminRoleById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminRole ac SET ac.valid = 0 WHERE ac.id = ?1 AND ac.valid = 1")
    int deleteAdminRoleById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminRole ac SET ac.valid = 0 WHERE ac.admin.id = ?1 AND ac.valid = 1")
    int deleteAdminRoleByAdminId(Integer adminId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE AdminRole ac SET ac.valid = 0 WHERE ac.role.id = ?1 AND ac.valid = 1")
    int deleteAdminRoleByRoleId(Integer roleId);
}
