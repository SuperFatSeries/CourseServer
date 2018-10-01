package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Notification {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Course course;

    @Column(length = 255)
    private String title;

    @JsonProperty(value = "create_time")
    @Column
    private Long createTime;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String content;

    public Notification(Integer id, Course course, String title, Long createTime, Long updateTime, Byte state, String content) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.state = state;
        this.content = content;
    }

    public Notification() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}