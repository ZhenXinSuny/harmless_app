package com.agridata.cdzhdj.data.pigbreed;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-27 15:03.
 * @Description :描述
 */
public class XdrFeedData {


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
        @SerializedName("OldID")
        public Object oldID;
        @SerializedName("XDRType")
        public int xDRType;
        @SerializedName("HasCert")
        public int hasCert;
        @SerializedName("HasAllow")
        public Object hasAllow;
        @SerializedName("Owner")
        public Object owner;
        @SerializedName("AEPCNO")
        public Object aEPCNO;
        @SerializedName("LegalPerson")
        public String legalPerson;
        @SerializedName("LivestockLicence")
        public Object livestockLicence;
        @SerializedName("LivestockLicenceTerm")
        public Object livestockLicenceTerm;
        @SerializedName("DesignInventory")
        public int designInventory;
        @SerializedName("CurrentInventory")
        public int currentInventory;
        @SerializedName("SupervisorInfo")
        public Object supervisorInfo;
        @SerializedName("VillageGroupID")
        public Object villageGroupID;
        @SerializedName("OVs")
        public Object oVs;
        @SerializedName("PVs")
        public Object pVs;
        @SerializedName("VVs")
        public Object vVs;
        @SerializedName("Keeper")
        public Object keeper;
        @SerializedName("WaterDeviceCount")
        public Object waterDeviceCount;
        @SerializedName("MaterialDeviceCount")
        public Object materialDeviceCount;
        @SerializedName("TemperDevice")
        public Object temperDevice;
        @SerializedName("LimitBarCount")
        public Object limitBarCount;
        @SerializedName("BirthBarCount")
        public Object birthBarCount;
        @SerializedName("ConservationBarCount")
        public Object conservationBarCount;
        @SerializedName("AddAmount")
        public Object addAmount;
        @SerializedName("IDCard")
        public Object iDCard;
        @SerializedName("IsSyncData")
        public Object isSyncData;
        @SerializedName("WaresDesignLiveStock")
        public Object waresDesignLiveStock;
        @SerializedName("WaresPreLiveStock")
        public Object waresPreLiveStock;
        @SerializedName("IDCardNo")
        public String iDCardNo;
        @SerializedName("Bank")
        public Object bank;
        @SerializedName("BankAccount")
        public String bankAccount;
        @SerializedName("RecordTypeText")
        public Object recordTypeText;
        @SerializedName("CreditCode")
        public Object creditCode;
        @SerializedName("CreditCodeEffectiveDate")
        public Object creditCodeEffectiveDate;
        @SerializedName("YearDesignLiveStock")
        public Object yearDesignLiveStock;
        @SerializedName("YearPreLiveStock")
        public Object yearPreLiveStock;
        @SerializedName("BuildDate")
        public Object buildDate;
        @SerializedName("CoveredArea")
        public Object coveredArea;
        @SerializedName("ImmuneType")
        public Object immuneType;
        @SerializedName("Generation")
        public Object generation;
        @SerializedName("HarmlessType")
        public Object harmlessType;
        @SerializedName("HarmlessCount")
        public Object harmlessCount;
        @SerializedName("DefecationCraft")
        public Object defecationCraft;
        @SerializedName("FluidDefecationUseType")
        public Object fluidDefecationUseType;
        @SerializedName("SolidDefecationUseType")
        public Object solidDefecationUseType;
        @SerializedName("IsAutonomy")
        public Object isAutonomy;
        @SerializedName("OwnName")
        public Object ownName;
        @SerializedName("OwnPhone")
        public Object ownPhone;
        public String _PartId;
        @SerializedName("DisplayName")
        public String displayName;
        @SerializedName("Mobile")
        public String mobile;
        @SerializedName("RegionID")
        public int regionID;
        @SerializedName("Region")
        public Region region;
        @SerializedName("Addresss")
        public String addresss;
        @SerializedName("ExamineUserId")
        public Object examineUserId;
        @SerializedName("ExamineTime")
        public Object examineTime;
        @SerializedName("ExamineStatus")
        public int examineStatus;
        @SerializedName("BusinessLicensePhoto")
        public Object businessLicensePhoto;
        @SerializedName("AEPCImgID")
        public Object aEPCImgID;
        @SerializedName("IDCardPhoto")
        public String iDCardPhoto;
        @SerializedName("AllowPhoto")
        public Object allowPhoto;
        @SerializedName("UserMid")
        public String userMid;
        @SerializedName("PhotoID")
        public Object photoID;
        @SerializedName("ItRaise")
        public String itRaise;
        @SerializedName("LegalPersonTel")
        public String legalPersonTel;
        @SerializedName("IDCardNos")
        public Object iDCardNos;
        @SerializedName("IDCardPhotos")
        public Object iDCardPhotos;
        @SerializedName("UserId")
        public String userId;
        @SerializedName("AnimalVariety")
        public List<AnimalVariety> animalVariety;
        @SerializedName("AnimalIDs")
        public Object animalIDs;
        @SerializedName("MOD11Code")
        public String mOD11Code;
        @SerializedName("CreateCodeStatus")
        public String createCodeStatus;
        public Object code;
        @SerializedName("SlaughterHouseStatus")
        public String slaughterHouseStatus;
        @SerializedName("RejectCause")
        public Object rejectCause;
        @SerializedName("BankPic")
        public String bankPic;
        @SerializedName("FarmScale")
        public FarmScale farmScale;
        @SerializedName("IsSendChengdu")
        public int isSendChengdu;
        @SerializedName("ZhiWeiGUID")
        public Object zhiWeiGUID;
        @SerializedName("NaturalLegalMid")
        public String naturalLegalMid;
        @SerializedName("AgriculturalLandReference")
        public Object agriculturalLandReference;
        @SerializedName("HogControl")
        public Object hogControl;
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
        @SerializedName("Dep_NaturalLegalMid")
        public DepNaturalLegalMid dep_NaturalLegalMid;

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
            @SerializedName("RI1RegionName")
            public String rI1RegionName;
            @SerializedName("RI2RegionName")
            public String rI2RegionName;
            @SerializedName("RI3RegionName")
            public String rI3RegionName;
            @SerializedName("RI4RegionName")
            public String rI4RegionName;
            @SerializedName("RegionFullName")
            public String regionFullName;
            @SerializedName("RegionParentID")
            public int regionParentID;
        }

        public static class FarmScale {
            public int key;
            @SerializedName("Name")
            public String name;
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

        public static class DepNaturalLegalMid {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("Name")
            public Object name;
            @SerializedName("UserId")
            public String userId;
            @SerializedName("Mobile")
            public String mobile;
            @SerializedName("UserType")
            public String userType;
            @SerializedName("NaturalPersonInfo")
            public NaturalPersonInfo naturalPersonInfo;
            @SerializedName("LegalPersonInfo")
            public LegalPersonInfo legalPersonInfo;
            public String _PartId;
            @SerializedName("UserName")
            public String userName;
            @SerializedName("ZhiWeiGUID")
            public Object zhiWeiGUID;

            public static class NaturalPersonInfo {
                @SerializedName("CertNo")
                public String certNo;
                @SerializedName("CertType")
                public String certType;
                @SerializedName("UserName")
                public String userName;
                @SerializedName("CreateTime")
                public String createTime;
                @SerializedName("UserMobile")
                public String userMobile;
                @SerializedName("UserRealLv")
                public String userRealLv;
                @SerializedName("CertEffDate")
                public String certEffDate;
                @SerializedName("CertExpDate")
                public String certExpDate;
                @SerializedName("CertTypeCode")
                public String certTypeCode;
            }

            public static class LegalPersonInfo {
                @SerializedName("CorpName")
                public String corpName;
                @SerializedName("CorpType")
                public String corpType;
                @SerializedName("LegalName")
                public String legalName;
                @SerializedName("CorpStatus")
                public String corpStatus;
                @SerializedName("LegalCertNo")
                public String legalCertNo;
                @SerializedName("LegalMobile")
                public String legalMobile;
                @SerializedName("LegalCertType")
                public String legalCertType;
                @SerializedName("CertificateSno")
                public String certificateSno;
                @SerializedName("LegalCertnoEndDate")
                public String legalCertnoEndDate;
                @SerializedName("LegalCertnoBeginDate")
                public String legalCertnoBeginDate;
            }
        }

        public static class AnimalVariety {
            @SerializedName("ID")
            public int iD;
            @SerializedName("Name")
            public String name;
            @SerializedName("CurrentInventory")
            public String currentInventory;
            @SerializedName("EartagCode")
            public int eartagCode;
        }
    }
}
