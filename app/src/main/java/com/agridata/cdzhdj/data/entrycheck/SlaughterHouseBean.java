package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-25 19:54.
 * @Description :描述
 */
public class SlaughterHouseBean {


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
        @SerializedName("OldID")
        public int oldID;
        @SerializedName("LegalPerson")
        public String legalPerson;
        @SerializedName("LegalIDCardNo")
        public Object legalIDCardNo;
        @SerializedName("BusinessLicenseNo")
        public String businessLicenseNo;
        @SerializedName("AEPCNo")
        public String aEPCNo;
        @SerializedName("SlaughterLicenseNo")
        public String slaughterLicenseNo;
        @SerializedName("FomesLicenceNo")
        public String fomesLicenceNo;
        @SerializedName("FSLNo")
        public Object fSLNo;
        @SerializedName("DesignYearCount")
        public Object designYearCount;
        @SerializedName("HarmlessType")
        public Object harmlessType;
        @SerializedName("BusinessModel")
        public Object businessModel;
        @SerializedName("InspectorCount")
        public Object inspectorCount;
        @SerializedName("ResidentOVCount")
        public Object residentOVCount;
        @SerializedName("Remark")
        public Object remark;
        @SerializedName("ExamineStatus")
        public String examineStatus;
        @SerializedName("DisplayName")
        public String displayName;
        @SerializedName("Mobile")
        public String mobile;
        @SerializedName("Region")
        public Region region;
        @SerializedName("ExamineUserId")
        public Object examineUserId;
        @SerializedName("ExamineTime")
        public Object examineTime;
        public String _PartId;
        @SerializedName("Addresss")
        public String addresss;
        @SerializedName("SlaughterType")
        public Object slaughterType;
        @SerializedName("BuildDate")
        public Object buildDate;
        @SerializedName("BuildArea")
        public Object buildArea;
        @SerializedName("SlaughterTypes")
        public List<List<String>> slaughterTypes;
        @SerializedName("OfficeCode")
        public Object officeCode;
        @SerializedName("BusinessLicensePhoto")
        public Object businessLicensePhoto;
        @SerializedName("AEPCPhoto")
        public Object aEPCPhoto;
        @SerializedName("LegalIDCardPhoto")
        public Object legalIDCardPhoto;
        @SerializedName("SlaughterLicensePhoto")
        public Object slaughterLicensePhoto;
        @SerializedName("FomesLicencePhoto")
        public Object fomesLicencePhoto;
        @SerializedName("FSLPhoto")
        public Object fSLPhoto;
        @SerializedName("SlaughterHouseStatus")
        public String slaughterHouseStatus;
        @SerializedName("RrejectInfo")
        public Object rrejectInfo;
        @SerializedName("UserMid")
        public Object userMid;
        @SerializedName("MOD11Code")
        public String mOD11Code;
        @SerializedName("CreateCodeStatus")
        public String createCodeStatus;
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
        public double longitude;
        @SerializedName("Latitude")
        public double latitude;
        @SerializedName("Point")
        public Point point;
        @SerializedName("Cst")
        public int cst;

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

        public static class Point {
            public String type;
            public Object properties;
            public Geometry geometry;
            public Object id;

            public static class Geometry {
                public String type;
                public List<Double> coordinates;
            }
        }
    }
}
