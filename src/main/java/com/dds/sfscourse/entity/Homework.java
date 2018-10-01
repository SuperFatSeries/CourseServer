package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Homework {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 255)
    private String name;

    @Column
    private Long ddl;

    @OneToOne
    private Course course;

    @OneToOne
    private Admin admin;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String requirement;

    @JsonProperty(value = "commit_count")
    @Column
    private Integer commitCount;

    public Homework(Integer id, String name, Long ddl, Course course, Admin admin, Long updateTime, Byte state, String requirement, Integer commitCount) {
        this.id = id;
        this.name = name;
        this.ddl = ddl;
        this.course = course;
        this.admin = admin;
        this.updateTime = updateTime;
        this.state = state;
        this.requirement = requirement;
        this.commitCount = commitCount;
    }

    public Homework(Integer id, String name, Long ddl, Course course, Admin admin, Long updateTime, String requirement, Integer commitCount) {
        this.id = id;
        this.name = name;
        this.ddl = ddl;
        this.course = course;
        this.admin = admin;
        this.updateTime = updateTime;
        this.requirement = requirement;
        this.commitCount = commitCount;
    }

    public Homework() {
        super();
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

    public Long getDdl() {
        return ddl;
    }

    public void setDdl(Long ddl) {
        this.ddl = ddl;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ddl=" + ddl +
                ", course=" + course +
                ", admin=" + admin +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", requirement='" + requirement + '\'' +
                ", commitCount=" + commitCount +
                '}';
    }
}