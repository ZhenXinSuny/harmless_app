package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-23 16:22.
 * @Description :描述
 */
public class QueryImmuneDetailsBean {


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
        @SerializedName("BranchID")
        public Object branchID;
        @SerializedName("Batch")
        public String batch;
        @SerializedName("Capacity")
        public String capacity;
        @SerializedName("Unit")
        public String unit;
        @SerializedName("VaccineCode")
        public Object vaccineCode;
        @SerializedName("VaccineFactory")
        public String vaccineFactory;
        @SerializedName("Specification")
        public Object specification;
        @SerializedName("CapacityUnit")
        public Object capacityUnit;
        @SerializedName("VaccineStyle")
        public Object vaccineStyle;
        @SerializedName("VaccinePzwh")
        public Object vaccinePzwh;
        @SerializedName("VaccineTel")
        public String vaccineTel;
        public String _PartId;
        @SerializedName("IIST")
        public int iIST;
        @SerializedName("AnimalID")
        public int animalID;
        @SerializedName("DiseaseID")
        public DiseaseID diseaseID;
        @SerializedName("VaccineId")
        public VaccineId vaccineId;
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

        @SerializedName("CategoryId")
        public String categoryId;
        @SerializedName("CategoryName")
        public String categoryName;
        @SerializedName("CategoryType")
        public String categoryType;
        @SerializedName("Dep_AnimalID")
        public DepAnimalID dep_AnimalID;

        public static class DiseaseID {
            public String key;
            @SerializedName("Name")
            public String name;
        }

        public static class VaccineId {
            public String key;
            @SerializedName("Name")
            public String name;
        }

        public static class DepAnimalID {
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
