package com.dds.sfscourse.dto;

import com.dds.sfscourse.entity.HomeworkSubmit;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigInteger;
import java.util.Date;

public class StudentCommitDto {
    @JsonProperty(value = "id")
    private Integer id;

    @JsonProperty(value = "student_id")
    private Integer studentId;

    @JsonProperty(value = "student_name")
    private String studentName;

    @JsonProperty(value = "file_name")
    private String fileName;

    @JsonProperty(value = "update_time")
    private Date updateTime;

    @JsonProperty(value = "commit_count")
    private Integer commitCount;

    @JsonProperty(value = "remark")
    private String remark;

    public StudentCommitDto(Integer studentId, String studentName, String fileName, Date updateTime, Integer commitCount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.fileName = fileName;
        this.updateTime = updateTime;
        this.commitCount = commitCount;
    }

    public StudentCommitDto(Integer studentId, String fileName, Date updateTime, Integer commitCount) {
        this.studentId = studentId;
        this.fileName = fileName;
        this.updateTime = updateTime;
        this.commitCount = commitCount;
    }

    public StudentCommitDto(Date updateTime, Integer commitCount) {
        this.updateTime = updateTime;
        this.commitCount = commitCount;
    }

    public StudentCommitDto(Object[] objects) {
        for(int i=0;i<objects.length;i++){
            switch (i){
                case 0:
                    this.id = ((BigInteger)objects[i]).intValue();
                    break;
                case 1:
                    this.studentId = ((BigInteger)objects[i]).intValue();
                    break;
                case 2:
                    this.studentName = (String) objects[i];
                    break;
                case 3:
                    this.fileName = (String) objects[i];
                    break;
                case 4:
                    this.commitCount = ((BigInteger)objects[i]).intValue();
                    break;
                case 5:
                    this.updateTime = new Date();//(((BigInteger)objects[i]).longValue());
                    break;
                case 6:
                    this.remark = (String) objects[i];//(((BigInteger)objects[i]).longValue());
                    break;
            }
        }
    }

    public StudentCommitDto() {
    }

    public StudentCommitDto(HomeworkSubmit homeworkSubmit,Integer commitCount) {
        this.studentId = homeworkSubmit.getStudent().getId();
        this.studentName = homeworkSubmit.getStudent().getName();
        this.fileName = homeworkSubmit.getFileName();
        this.updateTime = homeworkSubmit.getLastModifiedTime();
        this.commitCount = commitCount;
        this.remark = homeworkSubmit.getRemark();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Integer commitCount) {
        this.commitCount = commitCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
