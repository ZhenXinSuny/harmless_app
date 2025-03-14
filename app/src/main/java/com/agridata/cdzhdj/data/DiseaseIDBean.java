package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-14 17:10.
 * @Description :描述
 */
public class DiseaseIDBean {

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
        public Object name;
        @SerializedName("AnimalID")
        public int animalID;
        @SerializedName("DiseaseID")
        public int diseaseID;
    }
}
