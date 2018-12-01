package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Institute extends AbstracEntity{

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

    public Institute(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Institute(Integer id) {
        this.id = id;
    }

    public Institute() {
        super();
    }

}