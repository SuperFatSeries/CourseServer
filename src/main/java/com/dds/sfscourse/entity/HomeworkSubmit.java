package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class HomeworkSubmit extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @JoinColumn(name = "homework_id",referencedColumnName = "id")
    @OneToOne
    private Homework homework;

    @JoinColumn(name = "student_id",referencedColumnName = "id")
    @OneToOne
    private Student student;

    @JsonProperty(value = "file_name")
    @Column(nullable = false)
    private String fileName;

    @JsonProperty(value = "file_key")
    @Column(nullable = false)
    private String fileKey;

    @Column
    private String remark;


    public HomeworkSubmit(Homework homework, Student student, String fileName, String fileKey, String remark) {
        this.homework = homework;
        this.student = student;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.remark = remark;
    }

    public HomeworkSubmit(Homework homework, Student student, String fileName, String fileKey) {
        this.homework = homework;
        this.student = student;
        this.fileName = fileName;
        this.fileKey = fileKey;
    }

    public HomeworkSubmit() {
        super();
    }

}
