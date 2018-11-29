package com.dds.sfscourse.model;

public class Admin extends Base{
    private String name;
    private String salt;
    private Long instituteId;
    private String password;
    private String email;

    public Admin(String name, String salt, Long instituteId, String password, String email) {
        this.name = name;
        this.salt = salt;
        this.instituteId = instituteId;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(Long instituteId) {
        this.instituteId = instituteId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", salt='" + salt + '\'' +
                ", instituteId=" + instituteId +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}