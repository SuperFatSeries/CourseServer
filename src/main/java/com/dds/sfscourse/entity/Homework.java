package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Homework extends AbstracEntity{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date ddl;

    @OneToOne
    private Course course;

    @OneToOne
    private Admin admin;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String requirement;

    @JsonProperty(value = "commit_count")
    @Column(nullable = false)
    private Integer commitCount = 0;


    public Homework(Integer id, String name, Date ddl, Course course, Admin admin, String requirement, Integer commitCount) {
        this.id = id;
        this.name = name;
        this.ddl = ddl;
        this.course = course;
        this.admin = admin;
        this.requirement = requirement;
        this.commitCount = commitCount;
    }

    public Homework(Integer id) {
        this.id = id;
    }

    public Homework() {
        super();
    }

}