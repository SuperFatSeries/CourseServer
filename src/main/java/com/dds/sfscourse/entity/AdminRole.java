package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class AdminRole {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne
    private Admin admin;

    @OneToOne
    private Role role;

    @JsonProperty(value = "update_time")
    @Column
    private Long updateTime;

    @Column
    private Byte state = 0;

    public AdminRole(Admin admin, Role role, Long updateTime, Byte state) {
        this.admin = admin;
        this.role = role;
        this.updateTime = updateTime;
        this.state = state;
    }

    public AdminRole(Admin admin, Role role) {
        this.admin = admin;
        this.role = role;
    }

    public AdminRole() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    @Override
    public String toString() {
        return "AdminRole{" +
                "id=" + id +
                ", admin=" + admin +
                ", role=" + role +
                '}';
    }
}
