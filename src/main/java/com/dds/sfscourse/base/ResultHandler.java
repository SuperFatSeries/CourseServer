package com.dds.sfscourse.base;

public class ResultHandler {
    /**
     * 成功时将object对象转化为ResultBean对象
     *
     * @param o
     * @return
     */
    public static ResultBean ok(Object o) {

        return new ResultBean(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), o);
    }

    /**
     * 使用枚举列举错误类型
     *
     * @param error
     * @return
     */
    public static ResultBean error(ResultEnum error) {
        return new ResultBean(error.getCode(), error.getMsg(), null);
    }

    public static ResultBean error(String e) {
        return new ResultBean(ResultEnum.EXCEPTION.getCode(), e, null);
    }
}
