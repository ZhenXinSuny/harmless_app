package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChuLiDetailBean {


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
        @SerializedName("HarmlessNo")
        public String harmlessNo;
        @SerializedName("FactoryGUID")
        public String factoryGUID;
        @SerializedName("HandleType")
        public String handleType;
        @SerializedName("Describe")
        public Object describe;
        @SerializedName("Remark")
        public String remark;
        @SerializedName("CheckUser")
        public String checkUser;
        @SerializedName("CheckTime")
        public String checkTime;
        @SerializedName("CheckStatus")
        public String checkStatus;
        @SerializedName("CheckSignature")
        public String checkSignature;
        @SerializedName("CheckRemark")
        public String checkRemark;
        @SerializedName("ImgFiles")
        public ImgFiles imgFiles;
        public String _PartId;
        @SerializedName("HandleCategory")
        public HandleCategory handleCategory;
        @SerializedName("StartTime")
        public String startTime;
        @SerializedName("FinishTime")
        public String finishTime;
        @SerializedName("ItemDatas")
        public List<ItemDatas> itemDatas;
        @SerializedName("Region")
        public Region region;
        @SerializedName("RegionID")
        public int regionID;
        @SerializedName("RegionRI1")
        public int regionRI1;
        @SerializedName("RegionRI2")
        public int regionRI2;
        @SerializedName("RegionRI3")
        public int regionRI3;
        @SerializedName("RegionRI4")
        public int regionRI4;
        @SerializedName("RegionRI5")
        public int regionRI5;
        @SerializedName("HandleUser")
        public HandleUser handleUser;
        @SerializedName("CollectStockGUID")
        public Object collectStockGUID;
        @SerializedName("SubsidyStatus")
        public int subsidyStatus;
        @SerializedName("SubsidyGUID")
        public Object subsidyGUID;
        @SerializedName("BindWeight")
        public double bindWeight;
        @SerializedName("Amount")
        public double amount;
        @SerializedName("Weight")
        public double weight;
        @SerializedName("BindAmount")
        public double bindAmount;
        @SerializedName("DataType")
        public int dataType;
        @SerializedName("HarmlessSlagGUID")
        public Object harmlessSlagGUID;
        @SerializedName("HarmlessSlagStatus")
        public int harmlessSlagStatus;
        @SerializedName("HarmlessSlagTime")
        public Object harmlessSlagTime;
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
        public Object modifierName;
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
        @SerializedName("Dep_FactoryGUID")
        public DepFactoryGUID dep_FactoryGUID;

        public static class ImgFiles {
            @SerializedName("ShenHeQianMing")
            public String shenHeQianMing;
            @SerializedName("ChuLiYuanQianMing")
            public String chuLiYuanQianMing;
        }

        public static class HandleCategory {
            public String key;
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

        public static class HandleUser {
            @SerializedName("ID")
            public String iD;
            @SerializedName("Name")
            public String name;
        }

        public static class DepFactoryGUID {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("Name")
            public Object name;
            @SerializedName("Code")
            public Object code;
            @SerializedName("DisposeName")
            public String disposeName;
            @SerializedName("UnitType")
            public String unitType;
            @SerializedName("UnitName")
            public String unitName;
            @SerializedName("AffIDCardNo")
            public Object affIDCardNo;
            @SerializedName("LegalPerson")
            public String legalPerson;
            @SerializedName("OperationState")
            public String operationState;
            @SerializedName("Principal")
            public String principal;
            @SerializedName("Mobile")
            public String mobile;
            @SerializedName("ProcessMode")
            public String processMode;
            @SerializedName("DeviceName")
            public Object deviceName;
            @SerializedName("Manufacturer")
            public Object manufacturer;
            @SerializedName("RadiateName")
            public Object radiateName;
            @SerializedName("Region")
            public Region region;
            @SerializedName("Address")
            public String address;
            @SerializedName("FloorSpace")
            public Object floorSpace;
            @SerializedName("Invest")
            public Object invest;
            @SerializedName("SiteArea")
            public Object siteArea;
            @SerializedName("DesignCapacity")
            public Object designCapacity;
            @SerializedName("ActualCapacity")
            public Object actualCapacity;
            @SerializedName("CompletionDate")
            public Object completionDate;
            @SerializedName("StartTime")
            public Object startTime;
            @SerializedName("Supervisor")
            public int supervisor;
            @SerializedName("StationVeterinarian")
            public Object stationVeterinarian;
            @SerializedName("OfficialVeterinarian")
            public Object officialVeterinarian;
            @SerializedName("IDNumber")
            public String iDNumber;
            @SerializedName("CertificateNumber")
            public String certificateNumber;
            @SerializedName("PersonName")
            public Object personName;
            @SerializedName("StaffPositions")
            public Object staffPositions;
            @SerializedName("VehicleMeg")
            public Object vehicleMeg;
            @SerializedName("Comment")
            public Object comment;
            public String _PartId;
            @SerializedName("ExamineStatus")
            public String examineStatus;
            @SerializedName("ExamineUserId")
            public Object examineUserId;
            @SerializedName("ExamineTime")
            public Object examineTime;
            @SerializedName("MOD11Code")
            public String mOD11Code;
            @SerializedName("CreateCodeStatus")
            public String createCodeStatus;
            @SerializedName("UserId")
            public Object userId;
            @SerializedName("HotLine")
            public String hotLine;

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
        }

        public static class ItemDatas {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("StockNo")
            public String stockNo;

            @SerializedName("CheckTime")
            public String checkTime;
        }
    }
}
