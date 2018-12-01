package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class StudentCourse extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Student student;

    @OneToOne
    private Course course;

    public StudentCourse(Integer id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public StudentCourse() {
        super();
    }

}