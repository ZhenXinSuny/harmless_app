package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-08-16 16:01.
 * @Description :描述
 */
public class CheckPointCertABean {

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
        @SerializedName("SourceId")
        public String sourceId;
        @SerializedName("Name")
        public Object name;
        @SerializedName("ApplyNo")
        public Object applyNo;
        @SerializedName("CertNo")
        public String certNo;
        @SerializedName("CertType")
        public String certType;
        @SerializedName("CheckPoint")
        public CheckPointBean checkPoint;
        @SerializedName("CheckResult")
        public String checkResult;
        @SerializedName("CheckSituation")
        public String checkSituation;
        @SerializedName("IsCarRecord")
        public int isCarRecord;
        @SerializedName("IsTransUserRecord")
        public String isTransUserRecord;
        @SerializedName("AnimalIsPass")
        public String animalIsPass;
        @SerializedName("ActuallyAnimal")
        public Object actuallyAnimal;
        @SerializedName("AmountIsPass")
        public String amountIsPass;
        @SerializedName("ActuallyAmount")
        public Object actuallyAmount;
        @SerializedName("CarIsPass")
        public String carIsPass;
        @SerializedName("ActuallyCar")
        public Object actuallyCar;
        @SerializedName("ActuallyCarIsRecord")
        public String actuallyCarIsRecord;
        @SerializedName("TransUserIsPass")
        public int transUserIsPass;
        @SerializedName("ActTransUser")
        public Object actTransUser;
        @SerializedName("ActTransUserRecord")
        public int actTransUserRecord;
        @SerializedName("MarkIsPass")
        public int markIsPass;
        @SerializedName("IsDisease")
        public int isDisease;
        @SerializedName("OtherIsPass")
        public int otherIsPass;
        @SerializedName("Other")
        public Object other;
        @SerializedName("Remark")
        public Object remark;
        public String PartId;
        @SerializedName("QuarantineTimeIsPass")
        public int quarantineTimeIsPass;
        @SerializedName("ActTransUserMobile")
        public Object actTransUserMobile;
        @SerializedName("ProductName")
        public Object productName;
        @SerializedName("ProductAmount")
        public Object productAmount;
        @SerializedName("LicensePlateNumber")
        public Object licensePlateNumber;
        @SerializedName("Carrier")
        public Object carrier;
        @SerializedName("CarrierMobile")
        public Object carrierMobile;
        @SerializedName("AnimalName")
        public Object animalName;
        @SerializedName("AnimalAmount")
        public Object animalAmount;
        @SerializedName("CheckSerialNo")
        public int checkSerialNo;
        @SerializedName("CheckResultNo")
        public String checkResultNo;

        public static class CheckPointBean {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public String name;
        }
    }
}
