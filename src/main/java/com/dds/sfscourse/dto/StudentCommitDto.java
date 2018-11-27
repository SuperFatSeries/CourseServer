package com.dds.sfscourse.dto;

import com.dds.sfscourse.entity.HomeworkSubmit;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentCommitDto {

    @JsonProperty(value = "student_id")
    private Integer studentId;

    @JsonProperty(value = "student_name")
    private String studentName;

    @JsonProperty(value = "file_name")
    private String fileName;

    @JsonProperty(value = "update_time")
    private Long updateTime;

    @JsonProperty(value = "commit_count")
    private Integer commitCount;

    public StudentCommitDto(Integer studentId, String studentName, String fileName, Long updateTime, Integer commitCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.fileName = fileName;
        this.updateTime = updateTime;
        this.commitCount = commitCount;
    }

    public StudentCommitDto(HomeworkSubmit homeworkSubmit,Integer commitCount) {
        this.studentId = homeworkSubmit.getStudent().getId();
        this.studentName = homeworkSubmit.getStudent().getName();
        this.fileName = homeworkSubmit.getFileName();
        this.updateTime = homeworkSubmit.getUpdateTime();
        this.commitCount = commitCount;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    @Override
    public String toString() {
        return "StudentCommitDto{" +
                "studentId=" + studentId +
                ", studentName='" + studentName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", updateTime=" + updateTime +
                ", commitCount=" + commitCount +
                '}';
    }
}
