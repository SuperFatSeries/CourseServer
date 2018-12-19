package com.dds.sfscourse.base;

import com.dds.sfscourse.Exception.BaseException;
import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.Exception.NullAttrException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常捕获类
 */
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultBean handle(BaseException e) {

        System.out.println(e);

        return ResultHandler.error(e);
    }

    /*
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultBean handle(Exception e) {

        if (e instanceof NullAttrException) {
            return ResultHandler.error(ResultEnum.NULL_ATTR);
        }

        if (e instanceof ForbiddenException) {
            return ResultHandler.error(ResultEnum.FORBIDDEN_EXCEPTION);
        }

        System.out.println(e);

        return ResultHandler.error(e.getMessage());
    }*/
}
