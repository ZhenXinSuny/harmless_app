package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-23 10:48.
 * @Description :描述
 */
public class QueryImmuneBean  implements Serializable {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public Result result;

    public static class Result  implements Serializable {
        public int pageSize;
        public int pageNo;
        public int pageCount;
        public int totalCount;
        public int itemCount;
        public List<PageItems> pageItems;

        public static class PageItems  implements Serializable {
            @SerializedName("ReplenishEarTagCode")
            public String replenishEarTagCode;

            @SerializedName("Mid")
            public String mid;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("Name")
            public Object name;
            @SerializedName("AHIUserID")
            public Object aHIUserID;
            @SerializedName("XDRCoreID")
            public String xDRCoreID;
            @SerializedName("XDRCoreInfo")
            public XDRCoreInfo xDRCoreInfo;
            @SerializedName("AnimalID")
            public int animalID;
            @SerializedName("ImmuneCount")
            public int immuneCount;
            @SerializedName("Immuned")
            public String immuned;
            @SerializedName("ImmuneType")
            public int immuneType;
            @SerializedName("EarTags")
            public String earTags;
            @SerializedName("PreLiveStock")
            public int preLiveStock;
            @SerializedName("ImmuneQuantity")
            public String immuneQuantity;
            @SerializedName("IsSelfWrite")
            public int isSelfWrite;
            @SerializedName("NotImmunedReason")
            public Object notImmunedReason;
            @SerializedName("IsEarTagAnimal")
            public int isEarTagAnimal;
            @SerializedName("Remark")
            public Object remark;
            @SerializedName("CurrentAge")
            public int currentAge;
            @SerializedName("FeedBackTime")
            public Object feedBackTime;
            @SerializedName("Region")
            public Region region;
            @SerializedName("EagTagsList")
            public List<String> eagTagsList;
            @SerializedName("AHIUser")
            public AHIUser aHIUser;
            @SerializedName("Animal")
            public Animal animal;
            @SerializedName("DetailID")
            public String detailID;
            @SerializedName("IsTransfer")
            public int isTransfer;
            @SerializedName("After_playing_first")
            public int after_playing_first;
            @SerializedName("Auditstatus")
            public Object auditstatus;
            @SerializedName("Cause")
            public Object cause;
            @SerializedName("Invoices")
            public Object invoices;
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
            @SerializedName("CategoryId")
            public String categoryId;
            @SerializedName("CategoryName")
            public String categoryName;
            @SerializedName("CategoryType")
            public String categoryType;
            @SerializedName("Dep_AnimalID")
            public DepAnimalID dep_AnimalID;

            public static class XDRCoreInfo  implements Serializable {
                @SerializedName("Key")
                public String key;
                @SerializedName("Name")
                public String name;
            }

            public static class AHIUser  implements Serializable {
                @SerializedName("Key")
                public String key;
                @SerializedName("Name")
                public String name;
            }
            public static class Region   implements Serializable {
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

            public static class Animal   implements Serializable {
                @SerializedName("Key")
                public String key;
                @SerializedName("Name")
                public String name;
            }

            public static class DepAnimalID   implements Serializable {
                @SerializedName("Mid")
                public String mid;
                @SerializedName("SourceId")
                public Object sourceId;
                @SerializedName("Name")
                public Object name;
                @SerializedName("ID")
                public int iD;
                @SerializedName("AnimalName")
                public String animalName;
                @SerializedName("AnimalLevel")
                public int animalLevel;
                @SerializedName("AnimalParentID")
                public int animalParentID;
                @SerializedName("AnimalLivedays")
                public int animalLivedays;
                @SerializedName("EartagCode")
                public int eartagCode;
                @SerializedName("AFIDV2")
                public int aFIDV2;
                @SerializedName("SortOrder")
                public int sortOrder;
                public String _PartId;
                @SerializedName("SystemStatus")
                public int systemStatus;
                @SerializedName("XDRHatcheryStatus")
                public Object xDRHatcheryStatus;
                @SerializedName("ImmuneStatus")
                public int immuneStatus;
                @SerializedName("QuarantineStatus")
                public int quarantineStatus;
                @SerializedName("XDRFarmStatus")
                public int xDRFarmStatus;
                @SerializedName("XDRCarStatus")
                public Object xDRCarStatus;
                @SerializedName("XDRSlaughterHouseStatus")
                public Object xDRSlaughterHouseStatus;
                @SerializedName("ProductTypeStatus")
                public Object productTypeStatus;
                @SerializedName("HarmlessStatus")
                public int harmlessStatus;

            }
        }
    }
}
