package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-24 09:34.
 * @Description :描述
 */
public class IsCheckDZHBean {

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
    public Integer data;
}
