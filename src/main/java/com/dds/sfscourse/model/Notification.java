package com.dds.sfscourse.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Notification {
    private Long courseId;
    private String title;
    @JsonProperty(value = "create_time")
    private Date createTime;
    private String content;

    public Notification(Long courseId, String title, Date createTime, String content) {
        this.courseId = courseId;
        this.title = title;
        this.createTime = createTime;
        this.content = content;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}