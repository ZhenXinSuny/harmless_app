package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-02-27 14:32.
 * @Description :描述
 */
public class XdrBindListData {
    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        @SerializedName("IsNormal")
        public int isNormal;
        @SerializedName("UnNormalMessage")
        public String unNormalMessage;
        @SerializedName("NaturalLegal")
        public NaturalLegalBean naturalLegal;
        @SerializedName("FarmList")
        public List<FarmListBean> farmList;
        @SerializedName("BindFarm")
        public Object bindFarm;

        public static class NaturalLegalBean {
            public String mid;
            @SerializedName("XDRMid")
            public Object xDRMid;
            @SerializedName("Mobile")
            public String mobile;
            @SerializedName("UserType")
            public String userType;
            @SerializedName("Region")
            public RegionBean region;
            @SerializedName("UserName")
            public String userName;
            @SerializedName("Village")
            public Object village;
            @SerializedName("DetailAddress")
            public Object detailAddress;
            @SerializedName("CertNo")
            public String certNo;
            @SerializedName("CorpName")
            public Object corpName;
            @SerializedName("CertificateSno")
            public Object certificateSno;

            public static class RegionBean {
                @SerializedName("RegionParentID")
                public int regionParentID;
                @SerializedName("RegionCode")
                public String regionCode;
                @SerializedName("RegionName")
                public String regionName;
                @SerializedName("RegionLevel")
                public int regionLevel;
                @SerializedName("RI2")
                public int rI2;
                @SerializedName("ID")
                public int iD;
                @SerializedName("RI1")
                public int rI1;
                @SerializedName("RI4")
                public int rI4;
                @SerializedName("RI3")
                public int rI3;
                @SerializedName("RegionFullName")
                public String regionFullName;
                @SerializedName("RI5")
                public int rI5;
            }
        }

        public static class FarmListBean {
            public int id;
            public double longitude;
            public double latitude;
            public Object code;
            @SerializedName("Mid")
            public String mid;
            @SerializedName("OldID")
            public Object oldID;
            @SerializedName("XDRType")
            public int xDRType;
            @SerializedName("HasCert")
            public Object hasCert;
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
            public Object designInventory;
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
            public String bank;
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
            @SerializedName("DisplayName")
            public String displayName;
            @SerializedName("Mobile")
            public String mobile;
            @SerializedName("RegionID")
            public Object regionID;
            @SerializedName("Region")
            public RegionBean region;
            @SerializedName("Addresss")
            public String addresss;
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
            public Object userMid;
            @SerializedName("PhotoID")
            public Object photoID;
            @SerializedName("ItRaise")
            public Object itRaise;
            @SerializedName("LegalPersonTel")
            public String legalPersonTel;
            @SerializedName("IDCardNos")
            public Object iDCardNos;
            @SerializedName("IDCardPhotos")
            public String iDCardPhotos;
            @SerializedName("UserId")
            public Object userId;
            @SerializedName("AnimalVariety")
            public List<AnimalVarietyBean> animalVariety;
            @SerializedName("AnimalIDs")
            public Object animalIDs;
            @SerializedName("MOD11Code")
            public String mOD11Code;
            @SerializedName("CreateCodeStatus")
            public String createCodeStatus;
            @SerializedName("SlaughterHouseStatus")
            public String slaughterHouseStatus;
            @SerializedName("RejectCause")
            public Object rejectCause;
            @SerializedName("BankPic")
            public String bankPic;
            @SerializedName("FarmScale")
            public Object farmScale;
            @SerializedName("AfterPlayingFirstState")
            public Object afterPlayingFirstState;
            @SerializedName("IsSendChengdu")
            public int isSendChengdu;
            @SerializedName("ZhiWeiGUID")
            public String zhiWeiGUID;
            @SerializedName("NaturalLegalMid")
            public String naturalLegalMid;
            @SerializedName("HarmlessCounts")
            public int harmlessCounts;
            @SerializedName("CheckStatus")
            public int checkStatus;
            @SerializedName("CheckResult")
            public String checkResult;
            @SerializedName("IsBind")
            public boolean isBind;

            public static class RegionBean {
                @SerializedName("RI3RegionName")
                public String rI3RegionName;
                @SerializedName("RI4RegionName")
                public String rI4RegionName;
                @SerializedName("RegionCode")
                public String regionCode;
                @SerializedName("RI1RegionName")
                public String rI1RegionName;
                @SerializedName("RI2RegionName")
                public String rI2RegionName;
                @SerializedName("RegionParentID")
                public int regionParentID;
                @SerializedName("RegionName")
                public String regionName;
                @SerializedName("RegionLevel")
                public int regionLevel;
                @SerializedName("RI2")
                public int rI2;
                @SerializedName("ID")
                public int iD;
                @SerializedName("RI1")
                public int rI1;
                @SerializedName("RI4")
                public int rI4;
                @SerializedName("RI3")
                public int rI3;
                @SerializedName("RI5RegionName")
                public String rI5RegionName;
                @SerializedName("RegionFullName")
                public String regionFullName;
                @SerializedName("RI5")
                public int rI5;
            }

            public static class AnimalVarietyBean {
                @SerializedName("CurrentInventory")
                public String currentInventory;
                @SerializedName("ID")
                public int iD;
                @SerializedName("Name")
                public String name;
            }
        }
    }
}
