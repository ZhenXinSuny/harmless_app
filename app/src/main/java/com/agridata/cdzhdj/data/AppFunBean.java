package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-13 13:21.
 * @Description :描述
 */
public class AppFunBean {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public Result result;

    public static class Result {
        public String description;
        public boolean disabled;
        public String firstId;
        public String firstMobileId;
        public String id;
        public String name;
    }
}
