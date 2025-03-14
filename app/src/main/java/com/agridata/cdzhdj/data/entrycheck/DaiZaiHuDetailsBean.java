package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-11-27 14:59.
 * @Description :描述
 */
public class DaiZaiHuDetailsBean {

    /**
     * status
     */
    @SerializedName("Status")
    public Integer status;
    /**
     * errorCode
     */
    @SerializedName("ErrorCode")
    public Integer errorCode;
    /**
     * message
     */
    @SerializedName("Message")
    public Object message;
    /**
     * result
     */
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        /**
         * mid
         */
        @SerializedName("Mid")
        public String mid;
        /**
         * name
         */
        @SerializedName("Name")
        public String name;
        /**
         * checkMid
         */
        @SerializedName("CheckMid")
        public String checkMid;
        /**
         * amount
         */
        @SerializedName("Amount")
        public String amount;
        /**
         * certNo
         */
        @SerializedName("CertNo")
        public String certNo;
        /**
         * breederId
         */
        @SerializedName("BreederId")
        public String breederId;
    }
}
