package com.dds.sfscourse.base;

public enum ResultEnum {
    SUCCESS(0,"操作成功"),
    FAIL(-1,"操作失败"),
    EXCEPTION(500, ""),
    NULL_ATTR(101,"属性为空"),
    RESOURCE_NOT_FOUND(404,"未知资源"),
    RESOURCE_EXIST(405,"资源已存在"),

    FORBIDDEN_EXCEPTION(403, "访问禁止"),

    //Login 2xxx
    LOGIN_ADMIN_NOT_FOUND(2001, "ADMIN不存在"),
    LOGIN_WRONG_PASSWD(2002, "账号或密码错误"),
    LOGIN_ACCOUNT_FREEZED(2003, "账号已被锁定,请联系管理员"),

    FILE_UPLOAD_FAIL(1000,"文件上传失败"),

    FILE_DELETE_FAIL(1001,"文件删除失败"),

    DEADLINE(1002,"作业提交已截止");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
