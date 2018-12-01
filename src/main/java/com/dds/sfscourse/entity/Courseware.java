package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Courseware extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Course course;

    @JsonProperty(value = "file_name")
    @Column(nullable = false)
    private String fileName;

    @JsonProperty(value = "file_key")
    @Column(nullable = false)
    private String fileKey;

    @Column(nullable = false)
    private Integer downloads = 0;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "Text")
    private String remark;

    public Courseware(Integer id, Course course, String fileName, String fileKey, Integer downloads, String remark) {
        this.id = id;
        this.course = course;
        this.fileName = fileName;
        this.fileKey = fileKey;
        this.downloads = downloads;
        this.remark = remark;
    }

    public Courseware(Integer id) {
        this.id = id;
    }

    public Courseware() {
        super();
    }


}
