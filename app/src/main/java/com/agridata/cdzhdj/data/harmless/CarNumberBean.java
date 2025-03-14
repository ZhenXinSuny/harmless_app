package com.agridata.cdzhdj.data.harmless;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-29 16:00.
 * @Description :描述
 */
public class CarNumberBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public List<ResultBean> result;

    public static class ResultBean {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("CarNo")
        public String carNo;

    }
}
