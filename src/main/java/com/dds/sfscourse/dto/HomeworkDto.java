package com.dds.sfscourse.dto;

import com.dds.sfscourse.entity.Homework;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class HomeworkDto {
    private Integer id;

    private String name;

    private Date ddl;

    @JsonProperty(value = "teacher_name")
    private String teacherName;

    private String requirement;

    @JsonProperty(value = "commit_count")
    private Integer commitCount;

    private Short capacity;

    private Integer createdBy;

    private Date createdTime = new Date();

    private Integer lastModifiedBy;

    private Date lastModifiedTime = new Date();

    public HomeworkDto(Integer id, String name, Date ddl, String teacherName, String requirement, Integer commitCount) {
        this.id = id;
        this.name = name;
        this.ddl = ddl;
        this.teacherName = teacherName;
        this.requirement = requirement;
        this.commitCount = commitCount;
    }

    public HomeworkDto(Homework homework) {
        this.id = homework.getId();
        this.name = homework.getName();
        this.ddl = homework.getDdl();
        this.teacherName = homework.getCourse().getTeacherName();
        this.requirement = homework.getRequirement();
        this.commitCount = homework.getCommitCount();
        this.capacity = homework.getCourse().getCapacity();
        this.createdBy = homework.getCreatedBy();
        this.createdTime = homework.getCreatedTime();
        this.lastModifiedBy = homework.getLastModifiedBy();
        this.lastModifiedTime = homework.getLastModifiedTime();
    }

    public HomeworkDto(Homework homework, Integer commitCount) {
        this.id = homework.getId();
        this.name = homework.getName();
        this.ddl = homework.getDdl();
        this.teacherName = homework.getCourse().getTeacherName();
        this.requirement = homework.getRequirement();
        this.capacity = homework.getCourse().getCapacity();
        this.commitCount = commitCount;

        this.createdBy = homework.getCreatedBy();
        this.createdTime = homework.getCreatedTime();
        this.lastModifiedBy = homework.getLastModifiedBy();
        this.lastModifiedTime = homework.getLastModifiedTime();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }


    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return "HomeworkDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ddl=" + ddl +
                ", teacherName='" + teacherName + '\'' +
                ", requirement='" + requirement + '\'' +
                ", commitCount=" + commitCount +
                ", capacity=" + capacity +
                '}';
    }
}
