package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-29 11:06.
 * @Description :描述
 */
public class CertNoExistsBean {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public Result result;

    public static class Result {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("SourceId")
        public Object sourceId;
        @SerializedName("Name")
        public Object name;
        public String _PartId;
        @SerializedName("CertNo")
        public String certNo;
        @SerializedName("CheckType")
        public int checkType;
        @SerializedName("EarTags")
        public  List<String> earTags;
        @SerializedName("Imgs")
        public List<String> imgs;
        @SerializedName("Counts")
        public int counts;
        @SerializedName("CarHead")
        public String carHead;
        @SerializedName("CheckResult")
        public int checkResult;
        @SerializedName("TimeIsPass")
        public int timeIsPass;
        @SerializedName("AddressIsPass")
        public int addressIsPass;
        @SerializedName("CarIsPass")
        public int carIsPass;
        @SerializedName("RoadIsPass")
        public int roadIsPass;
        @SerializedName("NumberIsPass")
        public int numberIsPass;
        @SerializedName("EarTagIsPass")
        public int earTagIsPass;
        @SerializedName("CheckTime")
        public String checkTime;
        @SerializedName("CheckUser")
        public CheckUser checkUser;
        @SerializedName("CertType")
        public String certType;
        @SerializedName("CreateAt")
        public long createAt;
        @SerializedName("LastUpdate")
        public long lastUpdate;
        @SerializedName("CreateAtStr")
        public String createAtStr;
        @SerializedName("LastUpdateStr")
        public String lastUpdateStr;
        @SerializedName("CreatorId")
        public String creatorId;
        @SerializedName("ModifierId")
        public String modifierId;
        @SerializedName("CreatorName")
        public String creatorName;
        @SerializedName("ModifierName")
        public String modifierName;
        @SerializedName("PartitionId")
        public String partitionId;
        @SerializedName("PartitionIds")
        public List<String> partitionIds;
        @SerializedName("CategoryId")
        public String categoryId;
        @SerializedName("CategoryName")
        public String categoryName;
        @SerializedName("CategoryType")
        public String categoryType;

        public static class CheckUser {
            @SerializedName("Key")
            public String key;
            @SerializedName("Name")
            public String name;
        }
    }
}
