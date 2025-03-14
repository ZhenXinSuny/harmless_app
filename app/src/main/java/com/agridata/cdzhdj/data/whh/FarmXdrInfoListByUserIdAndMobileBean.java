package com.agridata.cdzhdj.data.whh;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-12-26 10:19.
 * @Description :描述
 */
public class FarmXdrInfoListByUserIdAndMobileBean {


    /**
     * code
     */
    @SerializedName("code")
    public Integer code;
    /**
     * msg
     */
    @SerializedName("msg")
    public String msg;
    /**
     * data
     */
    @SerializedName("data")
    public Data data;

    public static class Data {
        /**
         * isNormal
         */
        @SerializedName("IsNormal")
        public Integer isNormal;
        /**
         * unNormalMessage
         */
        @SerializedName("UnNormalMessage")
        public String unNormalMessage;
        /**
         * naturalLegal
         */
        @SerializedName("NaturalLegal")
        public NaturalLegal naturalLegal;
        /**
         * farmList
         */
        @SerializedName("FarmList")
        public List<FarmList> farmList;
        /**
         * bindFarm
         */
        @SerializedName("BindFarm")
        public List<BindFarm> bindFarm;

        public static class NaturalLegal {
            /**
             * mid
             */
            @SerializedName("mid")
            public String mid;
            /**
             * xDRMid
             */
            @SerializedName("XDRMid")
            public Object xDRMid;
            /**
             * mobile
             */
            @SerializedName("Mobile")
            public String mobile;
            /**
             * userType
             */
            @SerializedName("UserType")
            public String userType;
            /**
             * region
             */
            @SerializedName("Region")
            public Region region;
            /**
             * userName
             */
            @SerializedName("UserName")
            public String userName;
            /**
             * village
             */
            @SerializedName("Village")
            public String village;
            /**
             * detailAddress
             */
            @SerializedName("DetailAddress")
            public String detailAddress;
            /**
             * certNo
             */
            @SerializedName("CertNo")
            public String certNo;
            /**
             * corpName
             */
            @SerializedName("CorpName")
            public Object corpName;
            /**
             * certificateSno
             */
            @SerializedName("CertificateSno")
            public Object certificateSno;

            public static class Region {
                /**
                 * regionParentID
                 */
                @SerializedName("RegionParentID")
                public Integer regionParentID;
                /**
                 * regionCode
                 */
                @SerializedName("RegionCode")
                public String regionCode;
                /**
                 * regionName
                 */
                @SerializedName("RegionName")
                public String regionName;
                /**
                 * regionLevel
                 */
                @SerializedName("RegionLevel")
                public Integer regionLevel;
                /**
                 * ri2
                 */
                @SerializedName("RI2")
                public Integer ri2;
                /**
                 * id
                 */
                @SerializedName("ID")
                public Integer id;
                /**
                 * ri1
                 */
                @SerializedName("RI1")
                public Integer ri1;
                /**
                 * ri4
                 */
                @SerializedName("RI4")
                public Integer ri4;
                /**
                 * ri3
                 */
                @SerializedName("RI3")
                public Integer ri3;
                /**
                 * regionFullName
                 */
                @SerializedName("RegionFullName")
                public String regionFullName;
                /**
                 * ri5
                 */
                @SerializedName("RI5")
                public Integer ri5;
            }
        }

        public static class FarmList {
            /**
             * id
             */
            @SerializedName("IsBind")
            public Boolean isBind;


            @SerializedName("id")
            public Integer id;
            /**
             * longitude
             */
            @SerializedName("longitude")
            public Double longitude;
            /**
             * latitude
             */
            @SerializedName("latitude")
            public Double latitude;
            /**
             * code
             */
            @SerializedName("code")
            public Object code;
            /**
             * mid
             */
            @SerializedName("Mid")
            public String mid;
            /**
             * oldID
             */
            @SerializedName("OldID")
            public Object oldID;
            /**
             * xDRType
             */
            @SerializedName("XDRType")
            public Integer xDRType;
            /**
             * hasCert
             */
            @SerializedName("HasCert")
            public Object hasCert;
            /**
             * hasAllow
             */
            @SerializedName("HasAllow")
            public Object hasAllow;
            /**
             * owner
             */
            @SerializedName("Owner")
            public Object owner;
            /**
             * aepcno
             */
            @SerializedName("AEPCNO")
            public Object aepcno;
            /**
             * legalPerson
             */
            @SerializedName("LegalPerson")
            public String legalPerson;
            /**
             * livestockLicence
             */
            @SerializedName("LivestockLicence")
            public Object livestockLicence;
            /**
             * livestockLicenceTerm
             */
            @SerializedName("LivestockLicenceTerm")
            public Object livestockLicenceTerm;
            /**
             * designInventory
             */
            @SerializedName("DesignInventory")
            public Object designInventory;
            /**
             * currentInventory
             */
            @SerializedName("CurrentInventory")
            public Integer currentInventory;
            /**
             * supervisorInfo
             */
            @SerializedName("SupervisorInfo")
            public Object supervisorInfo;
            /**
             * villageGroupID
             */
            @SerializedName("VillageGroupID")
            public Object villageGroupID;
            /**
             * oVs
             */
            @SerializedName("OVs")
            public Object oVs;
            /**
             * pVs
             */
            @SerializedName("PVs")
            public Object pVs;
            /**
             * vVs
             */
            @SerializedName("VVs")
            public Object vVs;
            /**
             * keeper
             */
            @SerializedName("Keeper")
            public Object keeper;
            /**
             * waterDeviceCount
             */
            @SerializedName("WaterDeviceCount")
            public Object waterDeviceCount;
            /**
             * materialDeviceCount
             */
            @SerializedName("MaterialDeviceCount")
            public Object materialDeviceCount;
            /**
             * temperDevice
             */
            @SerializedName("TemperDevice")
            public Object temperDevice;
            /**
             * limitBarCount
             */
            @SerializedName("LimitBarCount")
            public Object limitBarCount;
            /**
             * birthBarCount
             */
            @SerializedName("BirthBarCount")
            public Object birthBarCount;
            /**
             * conservationBarCount
             */
            @SerializedName("ConservationBarCount")
            public Object conservationBarCount;
            /**
             * addAmount
             */
            @SerializedName("AddAmount")
            public Object addAmount;
            /**
             * iDCard
             */
            @SerializedName("IDCard")
            public Object iDCard;
            /**
             * isSyncData
             */
            @SerializedName("IsSyncData")
            public Object isSyncData;
            /**
             * waresDesignLiveStock
             */
            @SerializedName("WaresDesignLiveStock")
            public Object waresDesignLiveStock;
            /**
             * waresPreLiveStock
             */
            @SerializedName("WaresPreLiveStock")
            public Object waresPreLiveStock;
            /**
             * iDCardNo
             */
            @SerializedName("IDCardNo")
            public String iDCardNo;
            /**
             * bank
             */
            @SerializedName("Bank")
            public String bank;
            /**
             * bankAccount
             */
            @SerializedName("BankAccount")
            public String bankAccount;
            /**
             * recordTypeText
             */
            @SerializedName("RecordTypeText")
            public Object recordTypeText;
            /**
             * creditCode
             */
            @SerializedName("CreditCode")
            public Object creditCode;
            /**
             * creditCodeEffectiveDate
             */
            @SerializedName("CreditCodeEffectiveDate")
            public Object creditCodeEffectiveDate;
            /**
             * yearDesignLiveStock
             */
            @SerializedName("YearDesignLiveStock")
            public Object yearDesignLiveStock;
            /**
             * yearPreLiveStock
             */
            @SerializedName("YearPreLiveStock")
            public Object yearPreLiveStock;
            /**
             * buildDate
             */
            @SerializedName("BuildDate")
            public Object buildDate;
            /**
             * coveredArea
             */
            @SerializedName("CoveredArea")
            public Object coveredArea;
            /**
             * immuneType
             */
            @SerializedName("ImmuneType")
            public Object immuneType;
            /**
             * generation
             */
            @SerializedName("Generation")
            public Object generation;
            /**
             * harmlessType
             */
            @SerializedName("HarmlessType")
            public Object harmlessType;
            /**
             * harmlessCount
             */
            @SerializedName("HarmlessCount")
            public Object harmlessCount;
            /**
             * defecationCraft
             */
            @SerializedName("DefecationCraft")
            public Object defecationCraft;
            /**
             * fluidDefecationUseType
             */
            @SerializedName("FluidDefecationUseType")
            public Object fluidDefecationUseType;
            /**
             * solidDefecationUseType
             */
            @SerializedName("SolidDefecationUseType")
            public Object solidDefecationUseType;
            /**
             * isAutonomy
             */
            @SerializedName("IsAutonomy")
            public Object isAutonomy;
            /**
             * ownName
             */
            @SerializedName("OwnName")
            public Object ownName;
            /**
             * ownPhone
             */
            @SerializedName("OwnPhone")
            public Object ownPhone;
            /**
             * displayName
             */
            @SerializedName("DisplayName")
            public String displayName;
            /**
             * mobile
             */
            @SerializedName("Mobile")
            public String mobile;
            /**
             * regionID
             */
            @SerializedName("RegionID")
            public Integer regionID;
            /**
             * region
             */
            @SerializedName("Region")
            public Region region;
            /**
             * addresss
             */
            @SerializedName("Addresss")
            public String addresss;
            /**
             * examineStatus
             */
            @SerializedName("ExamineStatus")
            public Integer examineStatus;
            /**
             * businessLicensePhoto
             */
            @SerializedName("BusinessLicensePhoto")
            public Object businessLicensePhoto;
            /**
             * aEPCImgID
             */
            @SerializedName("AEPCImgID")
            public Object aEPCImgID;
            /**
             * iDCardPhoto
             */
            @SerializedName("IDCardPhoto")
            public String iDCardPhoto;
            /**
             * allowPhoto
             */
            @SerializedName("AllowPhoto")
            public Object allowPhoto;
            /**
             * userMid
             */
            @SerializedName("UserMid")
            public String userMid;
            /**
             * photoID
             */
            @SerializedName("PhotoID")
            public Object photoID;
            /**
             * itRaise
             */
            @SerializedName("ItRaise")
            public Object itRaise;
            /**
             * legalPersonTel
             */
            @SerializedName("LegalPersonTel")
            public Object legalPersonTel;
            /**
             * iDCardNos
             */
            @SerializedName("IDCardNos")
            public Object iDCardNos;
            /**
             * iDCardPhotos
             */
            @SerializedName("IDCardPhotos")
            public String iDCardPhotos;
            /**
             * userId
             */
            @SerializedName("UserId")
            public Object userId;
            /**
             * animalVariety
             */
            @SerializedName("AnimalVariety")
            public List<AnimalVariety> animalVariety;
            /**
             * animalIDs
             */
            @SerializedName("AnimalIDs")
            public Object animalIDs;
            /**
             * mOD11Code
             */
            @SerializedName("MOD11Code")
            public Object mOD11Code;
            /**
             * createCodeStatus
             */
            @SerializedName("CreateCodeStatus")
            public String createCodeStatus;
            /**
             * slaughterHouseStatus
             */
            @SerializedName("SlaughterHouseStatus")
            public Object slaughterHouseStatus;
            /**
             * rejectCause
             */
            @SerializedName("RejectCause")
            public Object rejectCause;
            /**
             * bankPic
             */
            @SerializedName("BankPic")
            public String bankPic;
            /**
             * farmScale
             */
            @SerializedName("FarmScale")
            public Object farmScale;
            /**
             * afterPlayingFirstState
             */
            @SerializedName("AfterPlayingFirstState")
            public Object afterPlayingFirstState;
            /**
             * isSendChengdu
             */
            @SerializedName("IsSendChengdu")
            public Object isSendChengdu;
            /**
             * zhiWeiGUID
             */
            @SerializedName("ZhiWeiGUID")
            public String zhiWeiGUID;
            /**
             * naturalLegalMid
             */
            @SerializedName("NaturalLegalMid")
            public String naturalLegalMid;
            /**
             * harmlessCounts
             */
            @SerializedName("HarmlessCounts")
            public Integer harmlessCounts;
            /**
             * checkStatus
             */
            @SerializedName("CheckStatus")
            public Integer checkStatus;
            /**
             * checkResult
             */
            @SerializedName("CheckResult")
            public String checkResult;

            public static class Region {
                /**
                 * rI3RegionName
                 */
                @SerializedName("RI3RegionName")
                public String rI3RegionName;
                /**
                 * rI4RegionName
                 */
                @SerializedName("RI4RegionName")
                public String rI4RegionName;
                /**
                 * regionCode
                 */
                @SerializedName("RegionCode")
                public String regionCode;
                /**
                 * rI1RegionName
                 */
                @SerializedName("RI1RegionName")
                public String rI1RegionName;
                /**
                 * rI2RegionName
                 */
                @SerializedName("RI2RegionName")
                public String rI2RegionName;
                /**
                 * regionParentID
                 */
                @SerializedName("RegionParentID")
                public Integer regionParentID;
                /**
                 * regionName
                 */
                @SerializedName("RegionName")
                public String regionName;
                /**
                 * regionLevel
                 */
                @SerializedName("RegionLevel")
                public Integer regionLevel;
                /**
                 * ri2
                 */
                @SerializedName("RI2")
                public Integer ri2;
                /**
                 * id
                 */
                @SerializedName("ID")
                public Integer id;
                /**
                 * ri1
                 */
                @SerializedName("RI1")
                public Integer ri1;
                /**
                 * ri4
                 */
                @SerializedName("RI4")
                public Integer ri4;
                /**
                 * ri3
                 */
                @SerializedName("RI3")
                public Integer ri3;
                /**
                 * regionFullName
                 */
                @SerializedName("RegionFullName")
                public String regionFullName;
                /**
                 * ri5
                 */
                @SerializedName("RI5")
                public Integer ri5;
            }

            public static class AnimalVariety {
                /**
                 * currentInventory
                 */
                @SerializedName("CurrentInventory")
                public String currentInventory;
                /**
                 * id
                 */
                @SerializedName("ID")
                public Integer id;
                /**
                 * name
                 */
                @SerializedName("Name")
                public String name;
            }
        }

        public static class BindFarm {
            /**
             * isBind
             */
            @SerializedName("IsBind")
            public Boolean isBind;
            /**
             * animalVariety
             */
            @SerializedName("AnimalVariety")
            public List<FarmList.AnimalVariety> animalVariety;
            /**
             * naturalLegalMobile
             */
            @SerializedName("NaturalLegalMobile")
            public String naturalLegalMobile;
            /**
             * isNaturalLegal
             */
            @SerializedName("IsNaturalLegal")
            public Boolean isNaturalLegal;
            /**
             * displayName
             */
            @SerializedName("DisplayName")
            public String displayName;
            /**
             * mid
             */
            @SerializedName("Mid")
            public String mid;
            /**
             * mobile
             */
            @SerializedName("Mobile")
            public String mobile;
            /**
             * regionFullName
             */
            @SerializedName("RegionFullName")
            public String regionFullName;
        }
    }
}
