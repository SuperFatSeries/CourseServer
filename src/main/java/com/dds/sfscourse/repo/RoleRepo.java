package com.dds.sfscourse.repo;

import com.dds.sfscourse.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query(value = "FROM Role n WHERE n.valid = 1")
    Page<Role> findRoles(Pageable pageable);

    @Query(value = "FROM Role n WHERE n.id = ?1 AND n.valid = 1")
    Role findRoleById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Role n SET n.valid = 0 WHERE n.id = ?1 AND n.valid = 1")
    int deleteRoleById(Integer id);
}
