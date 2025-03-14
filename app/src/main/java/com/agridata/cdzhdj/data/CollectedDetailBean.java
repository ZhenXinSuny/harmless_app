package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectedDetailBean {


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
        @SerializedName("ApplyGUID")
        public String applyGUID;
        @SerializedName("CollectNo")
        public String collectNo;
        @SerializedName("CollectType")
        public int collectType;
        @SerializedName("FactoryGUID")
        public String factoryGUID;
        @SerializedName("CollectCategory")
        public String collectCategory;
        @SerializedName("CheckUnitType")
        public String checkUnitType;
        @SerializedName("DieAmount")
        public String dieAmount;
        @SerializedName("DieWeight")
        public String dieWeight;
        @SerializedName("Worker")
        public Worker worker;
        @SerializedName("CarInfo")
        public CarInfo carInfo;
        @SerializedName("WorkerID")
        public Object workerID;
        @SerializedName("CarID")
        public Object carID;
        @SerializedName("Disinfect")
        public String disinfect;
        @SerializedName("DieReasonType")
        public String dieReasonType;
        @SerializedName("Remark")
        public String remark;
        @SerializedName("Region")
        public Region region;
        @SerializedName("Scale")
        public int scale;
        @SerializedName("EarTags")
        public Object earTags;
        @SerializedName("ImgFiles")
        public ImgFiles imgFiles;
        public String _PartId;
        @SerializedName("CheckUser")
        public String checkUser;
        @SerializedName("CheckTime")
        public String checkTime;
        @SerializedName("CheckStatus")
        public int checkStatus;
        @SerializedName("CheckSignature")
        public Object checkSignature;
        @SerializedName("CheckRemark")
        public String checkRemark;
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
        @SerializedName("IsInsurance")
        public boolean isInsurance;
        @SerializedName("IsDisinfect")
        public boolean isDisinfect;
        @SerializedName("InsuranceCompany")
        public InsuranceCompany insuranceCompany;
        @SerializedName("IDCard")
        public String iDCard;
        @SerializedName("BankName")
        public Object bankName;
        @SerializedName("BankCardNo")
        public String bankCardNo;
        @SerializedName("AnimalUnit")
        public AnimalUnit animalUnit;
        @SerializedName("Animal")
        public Animal animal;
        @SerializedName("ItemDatas")
        public List<ItemDatas> itemDatas;
        @SerializedName("InStoreStatus")
        public int inStoreStatus;
        @SerializedName("Disease")
        public boolean disease;
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
        public Object modifierId;
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
        @SerializedName("Longitude")
        public Object longitude;
        @SerializedName("Latitude")
        public Object latitude;
        @SerializedName("Point")
        public Object point;
        @SerializedName("Cst")
        public Object cst;
        @SerializedName("Dep_ApplyGUID")
        public DepApplyGUID dep_ApplyGUID;

        public static class Worker {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public String name;
            @SerializedName("Mobile")
            public String mobile;
        }

        public static class CarInfo {
            @SerializedName("Mid")
            public String mid;
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

        public static class ImgFiles {
            @SerializedName("BankPic")
            public String bankPic;
            @SerializedName("IdCardPic")
            public String idCardPic;
            @SerializedName("ShiTiPicList")
            public List<ShiTiPicList> shiTiPicList;
            @SerializedName("SiChuPicList")
            public List<SiChuPicList> siChuPicList;
            @SerializedName("ShouYunYuanPic")
            public String shouYunYuanPic;
            @SerializedName("ZhuangChePicList")
            public List<ZhuangChePicList> zhuangChePicList;
            @SerializedName("YangZhiChangHuPic")
            public String yangZhiChangHuPic;

            @SerializedName("ShenHeQianMing")
            public String shenHeQianMing;

            public static class ShiTiPicList {
                @SerializedName("Mid")
                public String mid;
            }

            public static class SiChuPicList {
                @SerializedName("Mid")
                public String mid;
            }

            public static class ZhuangChePicList {
                @SerializedName("Mid")
                public String mid;
            }
        }

        public static class InsuranceCompany {
            public String key;
            @SerializedName("InsuranceCompanyName")
            public String insuranceCompanyName;
        }

        public static class AnimalUnit {
            public String key;
            @SerializedName("UnitName")
            public String unitName;
        }

        public static class Animal {
            public String key;
            @SerializedName("AnimalName")
            public String animalName;
        }

        public static class DepApplyGUID {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("Name")
            public Object name;
            @SerializedName("UserName")
            public String userName;
            @SerializedName("Mobile")
            public String mobile;
            @SerializedName("DieAmount")
            public String dieAmount;
            @SerializedName("DieWeight")
            public Object dieWeight;
            @SerializedName("AnimalID")
            public String animalID;
            @SerializedName("Animal")
            public Animal animal;
            @SerializedName("ApplyType")
            public Object applyType;
            @SerializedName("FarmMid")
            public String farmMid;
            @SerializedName("Remark")
            public Object remark;
            @SerializedName("ApplyTime")
            public String applyTime;
            @SerializedName("CheckUser")
            public Object checkUser;
            @SerializedName("CheckTime")
            public Object checkTime;
            @SerializedName("CheckSignature")
            public Object checkSignature;
            @SerializedName("CheckRemark")
            public Object checkRemark;
            public String _PartId;
            @SerializedName("CheckStatus")
            public int checkStatus;
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
            @SerializedName("ApplyCategory")
            public String applyCategory;
            @SerializedName("ApplyPoint")
            public ApplyPoint applyPoint;
            @SerializedName("ApplyAddress")
            public String applyAddress;
            @SerializedName("IsApplySelf")
            public boolean isApplySelf;
            @SerializedName("ApplyNo")
            public String applyNo;
            @SerializedName("ImgFiles")
            public ImgFiles imgFiles;
            @SerializedName("IDCard")
            public String iDCard;
            @SerializedName("BankName")
            public Object bankName;
            @SerializedName("BankCardNo")
            public String bankCardNo;
            @SerializedName("SourceType")
            public int sourceType;
            @SerializedName("Longitude")
            public Object longitude;
            @SerializedName("Latitude")
            public Object latitude;
            @SerializedName("Point")
            public Object point;
            @SerializedName("Cst")
            public Object cst;

            public static class Animal {
                @SerializedName("ID")
                public int iD;
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
                @SerializedName("RI1RegionName")
                public String rI1RegionName;
                @SerializedName("RI2RegionName")
                public String rI2RegionName;
                @SerializedName("RI3RegionName")
                public String rI3RegionName;
                @SerializedName("RI4RegionName")
                public String rI4RegionName;
                @SerializedName("RI5RegionName")
                public Object rI5RegionName;
                @SerializedName("RegionFullName")
                public String regionFullName;
                @SerializedName("RegionParentID")
                public int regionParentID;
            }

            public static class ApplyPoint {
                @SerializedName("ID")
                public String iD;
                @SerializedName("Name")
                public String name;
            }

            public static class ImgFiles {
                @SerializedName("BankPic")
                public String bankPic;
                @SerializedName("IdCardPic")
                public String idCardPic;
                @SerializedName("IdCardPicBg")
                public String idCardPicBg;
            }
        }

        public static class ItemDatas {
            @SerializedName("Name")
            public String name;
            @SerializedName("IsSow")
            public int isSow;
            @SerializedName("Amount")
            public String amount;
            @SerializedName("AnimalID")
            public String animalID;
            @SerializedName("EarTagNo")
            public String earTagNo;
            @SerializedName("AnimalType")
            public int animalType;
            @SerializedName("BodyWeight")
            public String bodyWeight;
        }
    }
}
