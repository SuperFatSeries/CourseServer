package com.dds.sfscourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Course extends Base {
    private String name;
    @JsonProperty(value = "teacher_name")
    private String teacherName;
    private Integer capacity;
    private String period;
    private String info;

    public Course(String name, String teacherName, Integer capacity, String period, String info) {
        this.name = name;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.period = period;
        this.info = info;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Integer{" +
                "name='" + name + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", capacity=" + capacity +
                ", period='" + period + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
