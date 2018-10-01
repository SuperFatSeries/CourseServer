package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Courseware {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Course course;

    @JsonProperty(value = "file_name")
    @Column(length = 255)
    private String fileName;

    @JsonProperty(value = "file_key")
    @Column(length = 255)
    private String fileKey;

    @Column
    private Integer downloads;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    @Column(length = 255)
    private String remark;

    public Courseware(Integer id, Course course, String fileName, String fileKey, Integer downloads, Long updateTime, Byte state, String remark) {
        this.id = id;
        this.course = course;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.downloads = downloads;
        this.updateTime = updateTime;
        this.state = state;
        this.remark = remark;
    }

    public Courseware(Integer id) {
        this.id = id;
    }

    public Courseware() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Courseware{" +
                "id=" + id +
                ", course=" + course +
                ", fileName='" + fileName + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", downloads=" + downloads +
                ", updateTime=" + updateTime +
                ", state=" + state +
                ", remark='" + remark + '\'' +
                '}';
    }
}
