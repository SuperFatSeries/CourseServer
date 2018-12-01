package com.dds.sfscourse.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Admin extends AbstracEntity{
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

    public Admin(Integer id, String name, String salt, Institute institute, String password, String email) {
        this.id = id;
        this.name = name;
        this.salt = salt;
        this.institute = institute;
        this.password = password;
        this.email = email;
    }

    public Admin(Integer id, String name, Institute institute, String password, String email) {
        this.id = id;
        this.name = name;
        this.institute = institute;
        this.password = password;
        this.email = email;
    }

    public Admin(Integer id) {
        this.id = id;
    }

    public Admin() {
        super();
    }
}