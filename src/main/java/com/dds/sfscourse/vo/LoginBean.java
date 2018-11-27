package com.dds.sfscourse.vo;

/**
 * 登录接口封装对象
 * @author liaowm5
 * @date Dec 10, 2018
 */
public class LoginBean {
    private Integer id;
    private String passwd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "id=" + id +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
