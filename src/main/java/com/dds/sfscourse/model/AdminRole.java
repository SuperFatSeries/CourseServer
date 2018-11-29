package com.dds.sfscourse.model;

public class AdminRole extends Base {
    private Long adminId;
    private Long roleId;

    public AdminRole(Long adminId, Long roleId) {
        this.adminId = adminId;
        this.roleId = roleId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "AdminRole{" +
                "adminId=" + adminId +
                ", roleId=" + roleId +
                '}';
    }
}
