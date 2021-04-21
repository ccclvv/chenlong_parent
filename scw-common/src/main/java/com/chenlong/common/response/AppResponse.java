package com.chenlong.common.response;

import com.chenlong.common.enums.DemoEnum;
import com.chenlong.common.enums.ResponseCodeEnum;

public class AppResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public AppResponse() {
        super();
    }

    /**
     * 响应成功
     * @param data  用户传过来的数据
     * @param <T>  用户自定义类型
     * @return
     */
    public static<T> AppResponse<T> ok(T data){
        AppResponse<T> response = new AppResponse<>();
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setMsg(ResponseCodeEnum.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    /**
     * 响应失败
     * @param data  用户传过来的数据
     * @param <T>  用户自定义类型
     * @return
     */
    public static<T> AppResponse<T> fail(T data){
        AppResponse<T> response = new AppResponse<>();
        response.setCode(ResponseCodeEnum.FAIL.getCode());
        response.setMsg(ResponseCodeEnum.FAIL.getMsg());
        response.setData(data);
        return response;
    }

    public AppResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
