package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Student extends AbstracEntity{
    @Id
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne
    private Institute institute;

    public Student(Integer id, String name, Institute institute) {
        this.id = id;
        this.name = name;
        this.institute = institute;
    }

    public Student(Integer id) {
        this.id = id;
    }

    public Student() {
        super();
    }
}