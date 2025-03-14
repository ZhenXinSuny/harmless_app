package com.agridata.cdzhdj.data.whh;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-03-14 14:21.
 * @Description :描述
 */
public class RuKuListForMidBean {

    /**
     * status
     */
    @SerializedName("Status")
    public Integer status;
    /**
     * errorCode
     */
    @SerializedName("ErrorCode")
    public Integer errorCode;
    /**
     * message
     */
    @SerializedName("Message")
    public Object message;
    /**
     * result
     */
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        /**
         * mid
         */
        @SerializedName("Mid")
        public String mid;
        /**
         * sourceId
         */
        @SerializedName("SourceId")
        public Object sourceId;
        /**
         * name
         */
        @SerializedName("Name")
        public Object name;
        /**
         * stockNo
         */
        @SerializedName("StockNo")
        public String stockNo;
        /**
         * factoryGUID
         */
        @SerializedName("FactoryGUID")
        public String factoryGUID;
        /**
         * worker
         */
        @SerializedName("Worker")
        public Worker worker;
        /**
         * carInfo
         */
        @SerializedName("CarInfo")
        public Worker carInfo;
        /**
         * dieAmount
         */
        @SerializedName("DieAmount")
        public Integer dieAmount;
        /**
         * dieWeight
         */
        @SerializedName("DieWeight")
        public Double dieWeight;
        /**
         * bindAmount
         */
        @SerializedName("BindAmount")
        public Integer bindAmount;
        /**
         * disinfect
         */
        @SerializedName("Disinfect")
        public String disinfect;
        /**
         * remark
         */
        @SerializedName("Remark")
        public Object remark;
        /**
         * checkUser
         */
        @SerializedName("CheckUser")
        public String checkUser;
        /**
         * checkTime
         */
        @SerializedName("CheckTime")
        public String checkTime;
        /**
         * checkStatus
         */
        @SerializedName("CheckStatus")
        public String checkStatus;
        /**
         * checkSignature
         */
        @SerializedName("CheckSignature")
        public String checkSignature;
        /**
         * checkRemark
         */
        @SerializedName("CheckRemark")
        public Object checkRemark;
        /**
         * imgFiles
         */
        @SerializedName("ImgFiles")
        public ImgFiles imgFiles;
        /**
         * partid
         */
        @SerializedName("_PartId")
        public String partid;
        /**
         * inRegion
         */
        @SerializedName("InRegion")
        public InRegion inRegion;
        /**
         * inRegionID
         */
        @SerializedName("InRegionID")
        public Integer inRegionID;
        /**
         * inRegionRI1
         */
        @SerializedName("InRegionRI1")
        public Integer inRegionRI1;
        /**
         * inRegionRI2
         */
        @SerializedName("InRegionRI2")
        public Integer inRegionRI2;
        /**
         * inRegionRI3
         */
        @SerializedName("InRegionRI3")
        public Integer inRegionRI3;
        /**
         * inRegionRI4
         */
        @SerializedName("InRegionRI4")
        public Integer inRegionRI4;
        /**
         * inRegionRI5
         */
        @SerializedName("InRegionRI5")
        public Integer inRegionRI5;
        /**
         * itemDatas
         */
        @SerializedName("ItemDatas")
        public List<ItemDatas> itemDatas;
        /**
         * isDisinfect
         */
        @SerializedName("IsDisinfect")
        public Boolean isDisinfect;
        /**
         * collectType
         */
        @SerializedName("CollectType")
        public Integer collectType;
        /**
         * doHarmlessStatus
         */
        @SerializedName("DoHarmlessStatus")
        public Integer doHarmlessStatus;
        /**
         * doHarmlessGUID
         */
        @SerializedName("DoHarmlessGUID")
        public Object doHarmlessGUID;
        /**
         * harmlessSlagGUID
         */
        @SerializedName("HarmlessSlagGUID")
        public Object harmlessSlagGUID;
        /**
         * harmlessSlagStatus
         */
        @SerializedName("HarmlessSlagStatus")
        public Integer harmlessSlagStatus;
        /**
         * doHarmlessCheckStatus
         */
        @SerializedName("DoHarmlessCheckStatus")
        public Integer doHarmlessCheckStatus;
        /**
         * collectStockUser
         */
        @SerializedName("CollectStockUser")
        public CollectStockUser collectStockUser;
        /**
         * checkIdea
         */
        @SerializedName("CheckIdea")
        public Object checkIdea;
        /**
         * cLRemark
         */
        @SerializedName("CLRemark")
        public Object cLRemark;
        /**
         * checkUserObject
         */
        @SerializedName("CheckUserObject")
        public Worker checkUserObject;
        /**
         * zhiWeiAsync
         */
        @SerializedName("ZhiWeiAsync")
        public Integer zhiWeiAsync;
        /**
         * diePigAmount
         */
        @SerializedName("DiePigAmount")
        public Object diePigAmount;
        /**
         * createAt
         */
        @SerializedName("CreateAt")
        public Long createAt;
        /**
         * lastUpdate
         */
        @SerializedName("LastUpdate")
        public Long lastUpdate;
        /**
         * createAtStr
         */
        @SerializedName("CreateAtStr")
        public String createAtStr;
        /**
         * lastUpdateStr
         */
        @SerializedName("LastUpdateStr")
        public String lastUpdateStr;
        /**
         * creatorId
         */
        @SerializedName("CreatorId")
        public String creatorId;
        /**
         * modifierId
         */
        @SerializedName("ModifierId")
        public String modifierId;
        /**
         * creatorName
         */
        @SerializedName("CreatorName")
        public String creatorName;
        /**
         * modifierName
         */
        @SerializedName("ModifierName")
        public String modifierName;
        /**
         * partitionId
         */
        @SerializedName("PartitionId")
        public String partitionId;
        /**
         * partitionIds
         */
        @SerializedName("PartitionIds")
        public List<String> partitionIds;
        /**
         * categoryId
         */
        @SerializedName("CategoryId")
        public String categoryId;
        /**
         * categoryName
         */
        @SerializedName("CategoryName")
        public String categoryName;
        /**
         * categoryType
         */
        @SerializedName("CategoryType")
        public String categoryType;
        /**
         * depFactoryguid
         */
        @SerializedName("Dep_FactoryGUID")
        public DepFactoryGUID depFactoryguid;
        /**
         * depDoharmlessguid
         */
        @SerializedName("Dep_DoHarmlessGUID")
        public Object depDoharmlessguid;
        /**
         * depHarmlessslagguid
         */
        @SerializedName("Dep_HarmlessSlagGUID")
        public Object depHarmlessslagguid;

        public static class Worker {
            /**
             * mid
             */
            @SerializedName("Mid")
            public String mid;
            /**
             * name
             */
            @SerializedName("Name")
            public String name;
            /**
             * mobile
             */
            @SerializedName("Mobile")
            public String mobile;
        }

        public static class ImgFiles {
            /**
             * ruKuQianMing
             */
            @SerializedName("RuKuQianMing")
            public String ruKuQianMing;
            /**
             * shenHeQianMing
             */
            @SerializedName("ShenHeQianMing")
            public String shenHeQianMing;
            /**
             * xianChangListPic
             */
            @SerializedName("XianChangListPic")
            public List<?> xianChangListPic;
        }

        public static class InRegion {
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
             * ri2
             */
            @SerializedName("RI2")
            public Integer ri2;
            /**
             * ri3
             */
            @SerializedName("RI3")
            public Integer ri3;
            /**
             * ri4
             */
            @SerializedName("RI4")
            public Integer ri4;
            /**
             * ri5
             */
            @SerializedName("RI5")
            public Integer ri5;
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
             * regionFullName
             */
            @SerializedName("RegionFullName")
            public String regionFullName;
            /**
             * regionParentID
             */
            @SerializedName("RegionParentID")
            public Integer regionParentID;
        }

        public static class CollectStockUser {
            /**
             * id
             */
            @SerializedName("ID")
            public String id;
            /**
             * name
             */
            @SerializedName("Name")
            public String name;
        }

        public static class DepFactoryGUID {
            /**
             * mid
             */
            @SerializedName("Mid")
            public String mid;
            /**
             * sourceId
             */
            @SerializedName("SourceId")
            public Object sourceId;
            /**
             * name
             */
            @SerializedName("Name")
            public Object name;
            /**
             * code
             */
            @SerializedName("Code")
            public String code;
            /**
             * disposeName
             */
            @SerializedName("DisposeName")
            public String disposeName;
            /**
             * unitType
             */
            @SerializedName("UnitType")
            public String unitType;
            /**
             * unitName
             */
            @SerializedName("UnitName")
            public String unitName;
            /**
             * affIDCardNo
             */
            @SerializedName("AffIDCardNo")
            public Object affIDCardNo;
            /**
             * legalPerson
             */
            @SerializedName("LegalPerson")
            public String legalPerson;
            /**
             * operationState
             */
            @SerializedName("OperationState")
            public String operationState;
            /**
             * principal
             */
            @SerializedName("Principal")
            public String principal;
            /**
             * mobile
             */
            @SerializedName("Mobile")
            public String mobile;
            /**
             * processMode
             */
            @SerializedName("ProcessMode")
            public String processMode;
            /**
             * deviceName
             */
            @SerializedName("DeviceName")
            public Object deviceName;
            /**
             * manufacturer
             */
            @SerializedName("Manufacturer")
            public Object manufacturer;
            /**
             * radiateName
             */
            @SerializedName("RadiateName")
            public Object radiateName;
            /**
             * region
             */
            @SerializedName("Region")
            public InRegion region;
            /**
             * address
             */
            @SerializedName("Address")
            public String address;
            /**
             * floorSpace
             */
            @SerializedName("FloorSpace")
            public Object floorSpace;
            /**
             * invest
             */
            @SerializedName("Invest")
            public Object invest;
            /**
             * siteArea
             */
            @SerializedName("SiteArea")
            public Object siteArea;
            /**
             * designCapacity
             */
            @SerializedName("DesignCapacity")
            public String designCapacity;
            /**
             * actualCapacity
             */
            @SerializedName("ActualCapacity")
            public String actualCapacity;
            /**
             * completionDate
             */
            @SerializedName("CompletionDate")
            public String completionDate;
            /**
             * startTime
             */
            @SerializedName("StartTime")
            public String startTime;
            /**
             * supervisor
             */
            @SerializedName("Supervisor")
            public Integer supervisor;
            /**
             * stationVeterinarian
             */
            @SerializedName("StationVeterinarian")
            public String stationVeterinarian;
            /**
             * officialVeterinarian
             */
            @SerializedName("OfficialVeterinarian")
            public String officialVeterinarian;
            /**
             * iDNumber
             */
            @SerializedName("IDNumber")
            public String iDNumber;
            /**
             * certificateNumber
             */
            @SerializedName("CertificateNumber")
            public String certificateNumber;
            /**
             * personName
             */
            @SerializedName("PersonName")
            public Object personName;
            /**
             * staffPositions
             */
            @SerializedName("StaffPositions")
            public Object staffPositions;
            /**
             * vehicleMeg
             */
            @SerializedName("VehicleMeg")
            public Object vehicleMeg;
            /**
             * comment
             */
            @SerializedName("Comment")
            public Object comment;
            /**
             * partid
             */
            @SerializedName("_PartId")
            public String partid;
            /**
             * examineStatus
             */
            @SerializedName("ExamineStatus")
            public String examineStatus;
            /**
             * examineUserId
             */
            @SerializedName("ExamineUserId")
            public Integer examineUserId;
            /**
             * examineTime
             */
            @SerializedName("ExamineTime")
            public String examineTime;
            /**
             * mOD11Code
             */
            @SerializedName("MOD11Code")
            public String mOD11Code;
            /**
             * createCodeStatus
             */
            @SerializedName("CreateCodeStatus")
            public String createCodeStatus;
            /**
             * userId
             */
            @SerializedName("UserId")
            public String userId;
            /**
             * hotLine
             */
            @SerializedName("HotLine")
            public String hotLine;
            /**
             * zhiWeiGUID
             */
            @SerializedName("ZhiWeiGUID")
            public Object zhiWeiGUID;
            /**
             * longitude
             */
            @SerializedName("Longitude")
            public Double longitude;
            /**
             * latitude
             */
            @SerializedName("Latitude")
            public Double latitude;
            /**
             * point
             */
            @SerializedName("Point")
            public Point point;
            /**
             * cst
             */
            @SerializedName("Cst")
            public Integer cst;

            public static class Point {
                /**
                 * type
                 */
                @SerializedName("type")
                public String type;
                /**
                 * properties
                 */
                @SerializedName("properties")
                public Object properties;
                /**
                 * geometry
                 */
                @SerializedName("geometry")
                public Geometry geometry;
                /**
                 * id
                 */
                @SerializedName("id")
                public Object id;

                public static class Geometry {
                    /**
                     * type
                     */
                    @SerializedName("type")
                    public String type;
                    /**
                     * coordinates
                     */
                    @SerializedName("coordinates")
                    public List<Double> coordinates;
                }
            }
        }

        public static class ItemDatas {
            /**
             * mid
             */
            @SerializedName("Mid")
            public String mid;
            /**
             * animal
             */
            @SerializedName("Animal")
            public Animal animal;
            /**
             * collectNo
             */
            @SerializedName("CollectNo")
            public String collectNo;
            /**
             * dieAmount
             */
            @SerializedName("DieAmount")
            public String dieAmount;
            /**
             * dieWeight
             */
            @SerializedName("DieWeight")
            public Integer dieWeight;
            /**
             * zongZhongLiang
             */
            @SerializedName("ZongZhongLiang")
            public Integer zongZhongLiang;

            public static class Animal {
                /**
                 * key
                 */
                @SerializedName("key")
                public String key;
                /**
                 * animalName
                 */
                @SerializedName("AnimalName")
                public String animalName;
            }
        }
    }
}
