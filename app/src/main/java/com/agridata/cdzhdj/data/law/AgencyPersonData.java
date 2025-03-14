package com.agridata.cdzhdj.data.law;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 15:09.
 * @Description :描述
 */
public class AgencyPersonData {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("Name")
        public String name;
        @SerializedName("Tel")
        public String tel;

    }
}
