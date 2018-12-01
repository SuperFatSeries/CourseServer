package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 255,nullable = true)
    private String name;

    @JsonProperty(value = "teacher_name")
    @Column(length = 40,nullable = false)
    private String teacherName;

    @Column
    private Short capacity;

    @Column(length = 100,nullable = false)
    private String period;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String info;

    public Course(Integer id, String name, String teacherName, Short capacity, String period, String info) {
        this.id = id;
        this.name = name;
        this.teacherName = teacherName;
        this.capacity = capacity;
        this.period = period;
        this.info = info;
    }

    public Course(Integer id, String name, String teacherName, String period) {
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

}
