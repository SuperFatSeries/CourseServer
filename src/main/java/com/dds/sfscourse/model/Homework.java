package com.dds.sfscourse.model;


import java.util.Date;

public class Homework extends Base {
    private String name;
    private Date ddl;
    private Long integer;
    private Long adminId;
    private String requirement;
    private Long downloadCount;

    public Homework(String name, Date ddl, Long integer, Long adminId, String requirement, Long downloadCount) {
        this.name = name;
        this.ddl = ddl;
        this.integer = integer;
        this.adminId = adminId;
        this.requirement = requirement;
        this.downloadCount = downloadCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDdl() {
        return ddl;
    }

    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }

    public Long getInteger() {
        return integer;
    }

    public void setInteger(Long integer) {
        this.integer = integer;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "name='" + name + '\'' +
                ", ddl=" + ddl +
                ", integer=" + integer +
                ", adminId=" + adminId +
                ", requirement='" + requirement + '\'' +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
