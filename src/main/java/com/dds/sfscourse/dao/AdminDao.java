package com.dds.sfscourse.dao;

import com.dds.sfscourse.model.Admin;

public interface AdminDao  {
    Admin findAdminByEmail(String email);
}
