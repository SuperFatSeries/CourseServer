package com.dds.sfscourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Courseware extends Base {
    private Long courseId;
    @JsonProperty(value = "file_name")
    private String fileName;
    @JsonProperty(value = "file_key")
    private String fileKey;
    private Long downloads;
    private String remark;

    public Courseware(Long courseId, String fileName, String fileKey, Long downloads, String remark) {
        this.courseId = courseId;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.downloads = downloads;
        this.remark = remark;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public Long getDownloads() {
        return downloads;
    }

    public void setDownloads(Long downloads) {
        this.downloads = downloads;
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
                "courseId=" + courseId +
                ", fileName='" + fileName + '\'' +
                ", fileKey='" + fileKey + '\'' +
                ", downloads=" + downloads +
                ", remark='" + remark + '\'' +
                '}';
    }
}
