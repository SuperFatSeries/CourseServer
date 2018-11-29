package com.dds.sfscourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class HomeworkSubmit {
    private Long homeworkId;
    private Long studentId;
    @JsonProperty(value = "file_key")
    private String fileKey;
    @JsonProperty(value = "submit_time")
    private Date submitTime;

    public HomeworkSubmit(Long homeworkId, Long studentId, String fileKey, Date submitTime) {
        this.homeworkId = homeworkId;
        this.studentId = studentId;
        this.fileKey = fileKey;
        this.submitTime = submitTime;
    }

    public Long getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Long homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    @Override
    public String toString() {
        return "HomeworkSubmit{" +
                "homeworkId=" + homeworkId +
                ", studentId=" + studentId +
                ", fileKey='" + fileKey + '\'' +
                ", submitTime=" + submitTime +
                '}';
    }
}
