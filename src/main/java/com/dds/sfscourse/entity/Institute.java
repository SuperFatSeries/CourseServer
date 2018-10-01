package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Institute {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 255)
    private String name;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    public Institute(Integer id, String name, Long updateTime, Byte state) {
        this.id = id;
        this.name = name;
        this.updateTime = updateTime;
        this.state = state;
    }

    public Institute() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
}