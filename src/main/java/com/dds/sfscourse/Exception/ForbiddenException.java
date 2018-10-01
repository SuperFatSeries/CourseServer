package com.dds.sfscourse.Exception;

import com.dds.sfscourse.base.ResultEnum;

/**
 * 权限异常
 */
public class ForbiddenException extends RuntimeException{
    private int code = ResultEnum.FORBIDDEN_EXCEPTION.getCode();
    private static String msg = ResultEnum.FORBIDDEN_EXCEPTION.getMsg();

    public ForbiddenException() {
        super(msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
