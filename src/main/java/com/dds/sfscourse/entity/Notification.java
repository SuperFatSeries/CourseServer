package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Notification extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Course course;

    @Column(nullable = false)
    private String title;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String content;

    public Notification(Integer id, Course course, String title, String content) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.content = content;
    }

    public Notification() {
        super();
    }

}