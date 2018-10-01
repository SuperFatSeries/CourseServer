package com.dds.sfscourse.base;

import com.dds.sfscourse.Exception.ForbiddenException;
import com.dds.sfscourse.Exception.NullAttrException;

/**
 * 异常捕获类
 */
//ControllerAdvice
public class ExceptionHandle {

    //@ExceptionHandler(value = Exception.class)
    //@ResponseBody
    public ResultBean handle(Exception e) {

        if (e instanceof NullAttrException) {
            return ResultHandler.error(ResultEnum.NULL_ATTR);
        }

        if (e instanceof ForbiddenException) {
            return ResultHandler.error(ResultEnum.FORBIDDEN_EXCEPTION);
        }

        return ResultHandler.error(e.getMessage());
    }
}
