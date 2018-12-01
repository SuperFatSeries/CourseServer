package com.dds.sfscourse.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Role extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String name;

}
