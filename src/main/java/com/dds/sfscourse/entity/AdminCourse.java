package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class AdminCourse extends AbstracEntity{

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Admin admin;

    @OneToOne
    private Course course;


    public AdminCourse(Integer id, Admin admin, Course course) {
        this.id = id;
        this.admin = admin;
        this.course = course;
    }

    public AdminCourse() {
        super();
    }


}