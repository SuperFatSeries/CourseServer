package com.dds.sfscourse.dao;

import com.dds.sfscourse.model.AdminRole;

import java.util.List;

public interface AdminRoleDao{
    List<AdminRole> findAdminRolesByAdminId(Integer adminId);
}
