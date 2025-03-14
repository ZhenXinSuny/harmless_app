package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-08-08 14:46.
 * @Description :描述
 */
public class EntryCheckLogData {

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
        @SerializedName("EntryCheckMid")
        public String entryCheckMid;
        @SerializedName("Modifier")
        public ModifierBean modifier;
        @SerializedName("ChangeTime")
        public String changeTime;
        @SerializedName("CertNo")
        public String certNo;
        @SerializedName("ChangeInfo")
        public ChangeInfoBean changeInfo;

        public static class ModifierBean {
            @SerializedName("Name")
            public String name;
            @SerializedName("UserID")
            public String userID;
        }

        public static class ChangeInfoBean {
            @SerializedName("DeathNumber")
            public String deathNumber;
            @SerializedName("ActualNumber")
            public String actualNumber;
            @SerializedName("NumberIsPass")
            public int numberIsPass;
            @SerializedName("AnomalyNumber")
            public String anomalyNumber;
        }
    }
}
