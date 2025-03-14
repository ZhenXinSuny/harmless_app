package com.agridata.cdzhdj.data.pigbreed.feed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-28 11:47.
 * @Description :描述
 */
public class FeedStorageListData {


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
        @SerializedName("Farm")
        public Farm farm;
        @SerializedName("Region")
        public Region region;
        @SerializedName("Manufacturer")
        public String manufacturer;
        @SerializedName("Enterprise")
        public String enterprise;
        @SerializedName("Brand")
        public String brand;
        @SerializedName("Batch")
        public String batch;
        @SerializedName("BeBornDate")
        public String beBornDate;
        @SerializedName("Validity")
        public String validity;
        @SerializedName("DepositorUser")
        public DepositorUser depositorUser;
        @SerializedName("WarehousingTime")
        public String warehousingTime;
        public String _PartId;
        @SerializedName("GoodsType")
        public GoodsType goodsType;
        @SerializedName("GoodsNumber")
        public int goodsNumber;
        @SerializedName("GoodsName")
        public String goodsName;
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

        public static class Farm {
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class Region {
            @SerializedName("ID")
            public int iD;
            @SerializedName("RI1")
            public int rI1;
            @SerializedName("RI2")
            public int rI2;
            @SerializedName("RI3")
            public int rI3;
            @SerializedName("RI4")
            public int rI4;
            @SerializedName("RI5")
            public int rI5;
            @SerializedName("RegionCode")
            public String regionCode;
            @SerializedName("RegionName")
            public String regionName;
            @SerializedName("RegionLevel")
            public int regionLevel;
            @SerializedName("RegionFullName")
            public String regionFullName;
            @SerializedName("RegionParentID")
            public int regionParentID;
        }

        public static class DepositorUser {
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class GoodsType {
            public String key;
            @SerializedName("Name")
            public String name;
        }
    }
}
