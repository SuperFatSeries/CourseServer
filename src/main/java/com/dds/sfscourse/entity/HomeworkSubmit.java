package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class HomeworkSubmit {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Homework homework;

    @OneToOne
    private Student student;

    @JsonProperty(value = "file_name")
    @Column(length = 255)
    private String fileName;

    @JsonProperty(value = "file_key")
    @Column(length = 255)
    private String fileKey;

    @JsonProperty(value = "submit_time")
    @Column
    private Long submitTime;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    @Column(length = 255)
    private String remark;

    public HomeworkSubmit(Homework homework, Student student, String fileName, String fileKey, Long submitTime, Long updateTime, Byte state, String remark) {
        this.homework = homework;
        this.student = student;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.submitTime = submitTime;
        this.updateTime = updateTime;
        this.state = state;
        this.remark = remark;
    }

    public HomeworkSubmit(Homework homework, Student student, String fileName, String fileKey, Long submitTime, Long updateTime, Byte state) {
        this.homework = homework;
        this.student = student;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.submitTime = submitTime;
        this.updateTime = updateTime;
        this.state = state;
    }

    public HomeworkSubmit(Integer id, Homework homework, Student student, String fileKey, Long submitTime, Long updateTime, Byte state) {
        this.id = id;
        this.homework = homework;
        this.student = student;
        this.fileKey = fileKey;
        this.submitTime = submitTime;
        this.updateTime = updateTime;
        this.state = state;
    }

    public HomeworkSubmit() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey == null ? null : fileKey.trim();
    }

    public Long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Long submitTime) {
        this.submitTime = submitTime;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "HomeworkSubmit{" +
                "id=" + id +
                ", homework=" + homework +
                ", student=" + student +
                ", fileName='" + fileName + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", submitTime=" + submitTime +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                '}';
    }
}
