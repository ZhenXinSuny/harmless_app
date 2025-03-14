package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-14 18:18.
 * @Description :描述    根据疫病选取疫苗种类
 */
public class VaccineBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        @SerializedName("Name")
        public String name;
        @SerializedName("Mid")
        public String mid;
        @SerializedName("AnimalID")
        public String animalID;
        @SerializedName("IsforceIm")
        public boolean isforceIm;
    }

    @Override
    public String toString() {
        return "VaccineBean{" +
                "status=" + status +
                ", errorCode=" + errorCode +
                ", message=" + message +
                ", result=" + result +
                '}';
    }
}
