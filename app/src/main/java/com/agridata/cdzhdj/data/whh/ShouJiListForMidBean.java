package com.agridata.cdzhdj.data.whh;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-03-14 14:49.
 * @Description :描述
 */
public class ShouJiListForMidBean {

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
         * applyGUID
         */
        @SerializedName("ApplyGUID")
        public String applyGUID;
        /**
         * collectNo
         */
        @SerializedName("CollectNo")
        public String collectNo;
        /**
         * collectType
         */
        @SerializedName("CollectType")
        public Integer collectType;
        /**
         * factoryGUID
         */
        @SerializedName("FactoryGUID")
        public String factoryGUID;
        /**
         * collectCategory
         */
        @SerializedName("CollectCategory")
        public String collectCategory;
        /**
         * checkUnitType
         */
        @SerializedName("CheckUnitType")
        public String checkUnitType;
        /**
         * dieAmount
         */
        @SerializedName("DieAmount")
        public String dieAmount;
        /**
         * dieWeight
         */
        @SerializedName("DieWeight")
        public String dieWeight;
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
         * workerID
         */
        @SerializedName("WorkerID")
        public Object workerID;
        /**
         * carID
         */
        @SerializedName("CarID")
        public Object carID;
        /**
         * disinfect
         */
        @SerializedName("Disinfect")
        public String disinfect;
        /**
         * dieReasonType
         */
        @SerializedName("DieReasonType")
        public String dieReasonType;
        /**
         * remark
         */
        @SerializedName("Remark")
        public Object remark;
        /**
         * region
         */
        @SerializedName("Region")
        public Region region;
        /**
         * scale
         */
        @SerializedName("Scale")
        public Integer scale;
        /**
         * earTags
         */
        @SerializedName("EarTags")
        public Object earTags;
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
        public Integer checkStatus;
        /**
         * checkSignature
         */
        @SerializedName("CheckSignature")
        public Object checkSignature;
        /**
         * checkRemark
         */
        @SerializedName("CheckRemark")
        public Object checkRemark;
        /**
         * regionID
         */
        @SerializedName("RegionID")
        public Integer regionID;
        /**
         * regionRI1
         */
        @SerializedName("RegionRI1")
        public Integer regionRI1;
        /**
         * regionRI2
         */
        @SerializedName("RegionRI2")
        public Integer regionRI2;
        /**
         * regionRI3
         */
        @SerializedName("RegionRI3")
        public Integer regionRI3;
        /**
         * regionRI4
         */
        @SerializedName("RegionRI4")
        public Integer regionRI4;
        /**
         * regionRI5
         */
        @SerializedName("RegionRI5")
        public Integer regionRI5;
        /**
         * isInsurance
         */
        @SerializedName("IsInsurance")
        public Boolean isInsurance;
        /**
         * isDisinfect
         */
        @SerializedName("IsDisinfect")
        public Boolean isDisinfect;
        /**
         * insuranceCompany
         */
        @SerializedName("InsuranceCompany")
        public InsuranceCompany insuranceCompany;
        /**
         * iDCard
         */
        @SerializedName("IDCard")
        public String iDCard;
        /**
         * bankName
         */
        @SerializedName("BankName")
        public Object bankName;
        /**
         * bankCardNo
         */
        @SerializedName("BankCardNo")
        public String bankCardNo;
        /**
         * animalUnit
         */
        @SerializedName("AnimalUnit")
        public AnimalUnit animalUnit;
        /**
         * animal
         */
        @SerializedName("Animal")
        public Animal animal;
        /**
         * itemDatas
         */
        @SerializedName("ItemDatas")
        public List<ItemDatas> itemDatas;
        /**
         * inStoreStatus
         */
        @SerializedName("InStoreStatus")
        public Integer inStoreStatus;
        /**
         * disease
         */
        @SerializedName("Disease")
        public Boolean disease;
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
         * longitude
         */
        @SerializedName("Longitude")
        public Object longitude;
        /**
         * latitude
         */
        @SerializedName("Latitude")
        public Object latitude;
        /**
         * point
         */
        @SerializedName("Point")
        public Object point;
        /**
         * cst
         */
        @SerializedName("Cst")
        public Object cst;
        /**
         * depApplyguid
         */
        @SerializedName("Dep_ApplyGUID")
        public DepApplyGUID depApplyguid;

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

        public static class Region {
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

        public static class ImgFiles {
            /**
             * bankPic
             */
            @SerializedName("BankPic")
            public String bankPic;
            /**
             * idCardPic
             */
            @SerializedName("IdCardPic")
            public String idCardPic;
            /**
             * shiTiPicList
             */
            @SerializedName("ShiTiPicList")
            public List<Worker> shiTiPicList;
            /**
             * siChuPicList
             */
            @SerializedName("SiChuPicList")
            public List<Worker> siChuPicList;
            /**
             * collectCertPic
             */
            @SerializedName("CollectCertPic")
            public String collectCertPic;
            /**
             * shenHeQianMing
             */
            @SerializedName("ShenHeQianMing")
            public String shenHeQianMing;
            /**
             * shouYunYuanPic
             */
            @SerializedName("ShouYunYuanPic")
            public String shouYunYuanPic;
            /**
             * zhuangChePicList
             */
            @SerializedName("ZhuangChePicList")
            public List<Worker> zhuangChePicList;
            /**
             * yangZhiChangHuPic
             */
            @SerializedName("YangZhiChangHuPic")
            public String yangZhiChangHuPic;
        }

        public static class InsuranceCompany {
            /**
             * key
             */
            @SerializedName("key")
            public String key;
            /**
             * insuranceCompanyName
             */
            @SerializedName("InsuranceCompanyName")
            public String insuranceCompanyName;
        }

        public static class AnimalUnit {
            /**
             * key
             */
            @SerializedName("key")
            public String key;
            /**
             * unitName
             */
            @SerializedName("UnitName")
            public String unitName;
        }

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

        public static class DepApplyGUID {
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
             * userName
             */
            @SerializedName("UserName")
            public String userName;
            /**
             * mobile
             */
            @SerializedName("Mobile")
            public String mobile;
            /**
             * dieAmount
             */
            @SerializedName("DieAmount")
            public String dieAmount;
            /**
             * dieWeight
             */
            @SerializedName("DieWeight")
            public Object dieWeight;
            /**
             * animalID
             */
            @SerializedName("AnimalID")
            public String animalID;
            /**
             * animal
             */
            @SerializedName("Animal")
            public Animal animal;
            /**
             * applyType
             */
            @SerializedName("ApplyType")
            public Object applyType;
            /**
             * farmMid
             */
            @SerializedName("FarmMid")
            public String farmMid;
            /**
             * remark
             */
            @SerializedName("Remark")
            public Object remark;
            /**
             * applyTime
             */
            @SerializedName("ApplyTime")
            public String applyTime;
            /**
             * checkUser
             */
            @SerializedName("CheckUser")
            public Object checkUser;
            /**
             * checkTime
             */
            @SerializedName("CheckTime")
            public Object checkTime;
            /**
             * checkSignature
             */
            @SerializedName("CheckSignature")
            public Object checkSignature;
            /**
             * checkRemark
             */
            @SerializedName("CheckRemark")
            public Object checkRemark;
            /**
             * partid
             */
            @SerializedName("_PartId")
            public String partid;
            /**
             * checkStatus
             */
            @SerializedName("CheckStatus")
            public Integer checkStatus;
            /**
             * region
             */
            @SerializedName("Region")
            public Region region;
            /**
             * regionID
             */
            @SerializedName("RegionID")
            public Integer regionID;
            /**
             * regionRI1
             */
            @SerializedName("RegionRI1")
            public Integer regionRI1;
            /**
             * regionRI2
             */
            @SerializedName("RegionRI2")
            public Integer regionRI2;
            /**
             * regionRI3
             */
            @SerializedName("RegionRI3")
            public Integer regionRI3;
            /**
             * regionRI4
             */
            @SerializedName("RegionRI4")
            public Integer regionRI4;
            /**
             * regionRI5
             */
            @SerializedName("RegionRI5")
            public Integer regionRI5;
            /**
             * applyCategory
             */
            @SerializedName("ApplyCategory")
            public String applyCategory;
            /**
             * applyPoint
             */
            @SerializedName("ApplyPoint")
            public ApplyPoint applyPoint;
            /**
             * applyAddress
             */
            @SerializedName("ApplyAddress")
            public String applyAddress;
            /**
             * isApplySelf
             */
            @SerializedName("IsApplySelf")
            public Boolean isApplySelf;
            /**
             * applyNo
             */
            @SerializedName("ApplyNo")
            public String applyNo;
            /**
             * imgFiles
             */
            @SerializedName("ImgFiles")
            public ImgFiles imgFiles;
            /**
             * iDCard
             */
            @SerializedName("IDCard")
            public String iDCard;
            /**
             * bankName
             */
            @SerializedName("BankName")
            public Object bankName;
            /**
             * bankCardNo
             */
            @SerializedName("BankCardNo")
            public String bankCardNo;
            /**
             * sourceType
             */
            @SerializedName("SourceType")
            public Integer sourceType;
            /**
             * longitude
             */
            @SerializedName("Longitude")
            public Object longitude;
            /**
             * latitude
             */
            @SerializedName("Latitude")
            public Object latitude;
            /**
             * point
             */
            @SerializedName("Point")
            public Object point;
            /**
             * cst
             */
            @SerializedName("Cst")
            public Object cst;

            public static class Animal {
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

            public static class ApplyPoint {
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

            public static class ImgFiles {
                /**
                 * bankPic
                 */
                @SerializedName("BankPic")
                public String bankPic;
                /**
                 * idCardPic
                 */
                @SerializedName("IdCardPic")
                public String idCardPic;
                /**
                 * idCardPicBg
                 */
                @SerializedName("IdCardPicBg")
                public String idCardPicBg;
            }
        }

        public static class ItemDatas {
            /**
             * name
             */
            @SerializedName("Name")
            public String name;
            /**
             * isSow
             */
            @SerializedName("IsSow")
            public Integer isSow;
            /**
             * amount
             */
            @SerializedName("Amount")
            public String amount;
            /**
             * animalID
             */
            @SerializedName("AnimalID")
            public String animalID;
            /**
             * earTagNo
             */
            @SerializedName("EarTagNo")
            public String earTagNo;
            /**
             * animalType
             */
            @SerializedName("AnimalType")
            public Integer animalType;
            /**
             * bodyWeight
             */
            @SerializedName("BodyWeight")
            public String bodyWeight;
        }
    }
}
