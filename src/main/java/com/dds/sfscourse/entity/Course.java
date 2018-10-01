package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 255,nullable = true)
    private String name;

    @JsonProperty(value = "teacher_name")
    @Column
    private String teacherName;

    @Column
    private Short capacity;

    @Column
    private String period;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String info;

    public Course(Integer id,String name, String teacherName, Short capacity, String period, Long updateTime, Byte state, String info) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.period = period;
        this.updateTime = updateTime;
        this.state = state;
        this.info = info;
    }

    public Course(Integer id,String name, String teacherName, Short capacity, String period, Long updateTime, String info) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.period = period;
        this.updateTime = updateTime;
        this.info = info;
    }

    public Course(Integer id,String name, String teacherName, String period) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.period = period;
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Course() {
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

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Short getCapacity() {
        return capacity;
    }

    public void setCapacity(Short capacity) {
        this.capacity = capacity;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", period='" + period + '\'' +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", info='" + info + '\'' +
                '}';
    }
}
