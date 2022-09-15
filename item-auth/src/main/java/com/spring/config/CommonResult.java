package com.spring.config;

public class CommonResult<T> {
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    protected T data;
    public CommonResult(){

    }
}
