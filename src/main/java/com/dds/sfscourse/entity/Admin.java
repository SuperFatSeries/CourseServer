package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 20,nullable = false)
    private String name;

    @Column(length = 255,nullable = false)
    private String salt;

    @OneToOne
    private Institute institute;

    @Column(length = 255,nullable = false)
    private String password;

    @Column(length = 255)
    private String email;

    @Column
    @JsonProperty(value = "update_time")
    private Long updateTime;

    @Column
    private Byte state = 0;

    public Admin(Integer id, String name, String salt, Institute institute, String password, String email, Long updateTime, Byte state) {
        this.id = id;
        this.name = name;
        this.salt = salt;
        this.institute = institute;
        this.password = password;
        this.email = email;
        this.updateTime = updateTime;
        this.state = state;
    }

    public Admin(Integer id, String name, Institute institute, String password, String email, Long updateTime, Byte state) {
        this.id = id;
        this.name = name;
        this.institute = institute;
        this.password = password;
        this.email = email;
        this.updateTime = updateTime;
        this.state = state;
    }

    public Admin() {
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

    public Institute getInstitute() {
        return institute;
    }

    public void setInstitute(Institute institute) {
        this.institute = institute;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salt='" + salt + '\'' +
                ", institute=" + institute +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", updateTime=" + updateTime +
                ", state=" + state +
                '}';
    }
}