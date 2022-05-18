package com.spring.config;

import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private String message;
    private Object data;

    protected Result(){

    }

    protected Result(int code, String message, Object data) {
        this.code=code;
        this.message=message;
        this.data=data;
    }

    /**
     * 成功
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static <T> Result<T> success(int code, String message, Object data){
        return new Result(code,message,data);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static <T> Result<T> fail(int code, String message, Object data){
        return new  Result(code,message,data);
    }




}
