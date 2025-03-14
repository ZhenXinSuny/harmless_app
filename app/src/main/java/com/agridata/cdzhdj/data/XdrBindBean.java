package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-02-27 09:07.
 * @Description :描述
 */
public class XdrBindBean {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("SourceId")
        public Object sourceId;
        @SerializedName("Name")
        public Object name;
        public String _PartId;
        @SerializedName("UserID")
        public String userID;
        @SerializedName("XDRMid")
        public String xDRMid;
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
    }
}
