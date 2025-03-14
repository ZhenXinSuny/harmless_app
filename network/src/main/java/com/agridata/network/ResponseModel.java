package com.agridata.network;

import java.util.List;

/**
 * 返回模板类，根据实际情况调整
 */
public class ResponseModel<T> {

    /**
     * 请求成功code值
     */
    public static final int SUCCESS = 0;


    private  int  ErrorCode;
    private int Status;
    private String Message;

    private T Result;


    public int getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(int errorCode) {
        ErrorCode = errorCode;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public T getResult() {
        return Result;
    }

    public void setResult(T result) {
        Result = result;
    }
}
