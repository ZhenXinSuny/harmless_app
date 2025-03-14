package com.agridata.cdzhdj.data.law;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 11:35.
 * @Description :描述
 */
public class AgencyData {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public List<Result> result;

    @Override
    public String toString() {
        return "AgencyData{" +
                "status=" + status +
                ", errorCode=" + errorCode +
                ", message=" + message +
                ", result=" + result +
                '}';
    }

    public static class Result {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("Name")
        public String name;

        @Override
        public String toString() {
            return "Result{" +
                    "mid='" + mid + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
