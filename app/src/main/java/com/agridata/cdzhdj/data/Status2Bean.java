package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-28 16:05.
 * @Description :描述
 */
public class Status2Bean {
    /**
     * code
     */
    @SerializedName("code")
    public Integer code;
    /**
     * message
     */
    @SerializedName("message")
    public String message;
    /**
     * data
     */
    @SerializedName("data")
    public Objects data;
}
