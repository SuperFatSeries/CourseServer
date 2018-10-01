package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class AdminCourse {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Admin admin;

    @OneToOne
    private Course course;

    @Column
    @JsonProperty(value = "update_time")
    private Long updateTime;

    private Byte state = 0;

    public AdminCourse(Integer id, Admin admin, Course course, Long updateTime, Byte state) {
        this.id = id;
        this.admin = admin;
        this.course = course;
        this.updateTime = updateTime;
        this.state = state;
    }

    public AdminCourse() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
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
}