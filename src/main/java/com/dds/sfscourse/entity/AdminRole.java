package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class AdminRole extends AbstracEntity{
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Admin admin;

    @OneToOne
    private Role role;

    public AdminRole(Admin admin, Role role) {
        this.admin = admin;
        this.role = role;
    }

    public AdminRole() {
    }

}
