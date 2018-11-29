package com.dds.sfscourse.model;


public class Student extends Base {
    private String name;
    private Long instituteId;

    public Student(String name, Long instituteId) {
        this.name = name;
        this.instituteId = instituteId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", instituteId=" + instituteId +
                '}';
    }
}