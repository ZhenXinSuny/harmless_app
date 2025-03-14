package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-16 15:42.
 * @Description :描述
 */
public class FaFangEarTagBean {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public Result result;

    public static class Result {
        public int pageSize;
        public int pageNo;
        public int pageCount;
        public int totalCount;
        public int itemCount;
        public List<PageItems> pageItems;

        public static class PageItems {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("Name")
            public Object name;
            @SerializedName("TransType")
            public int transType;
            @SerializedName("AnimalID")
            public int animalID;
            @SerializedName("Prefix")
            public String prefix;
            @SerializedName("RangeStart")
            public String rangeStart;
            @SerializedName("RangeEnd")
            public String rangeEnd;
            @SerializedName("SendDate")
            public String sendDate;
            @SerializedName("TotalNumber")
            public int totalNumber;
            @SerializedName("IsSignFor")
            public int isSignFor;
            @SerializedName("InRegionID")
            public int inRegionID;
            @SerializedName("StockSerialID")
            public Object stockSerialID;
            public String _PartId;
            @SerializedName("Region")
            public Region region;
            @SerializedName("AgencyID")
            public String agencyID;
            @SerializedName("OutUser")
            public String outUser;
            @SerializedName("InUserJson")
            public InUserJson inUserJson;
            @SerializedName("InPrId")
            public Object inPrId;
            @SerializedName("OutUserJson")
            public OutUserJson outUserJson;
            @SerializedName("Grant_Region_person")
            public String grant_Region_person;
            @SerializedName("Sign_In_Region_Person")
            public String sign_In_Region_Person;
            @SerializedName("InUser")
            public String inUser;
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
            @SerializedName("Longitude")
            public Object longitude;
            @SerializedName("Latitude")
            public Object latitude;
            @SerializedName("Point")
            public Object point;
            @SerializedName("Cst")
            public Object cst;

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

            public static class InUserJson {
                public String key;
                @SerializedName("Name")
                public String name;
            }

            public static class OutUserJson {
                public String mid;
                @SerializedName("Name")
                public String name;
            }
        }
    }
}
