package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RuKuDetailBean {


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
        @SerializedName("StockNo")
        public String stockNo;
        @SerializedName("FactoryGUID")
        public String factoryGUID;
        @SerializedName("Worker")
        public Worker worker;
        @SerializedName("CarInfo")
        public CarInfo carInfo;
        @SerializedName("DieAmount")
        public int dieAmount;
        @SerializedName("DieWeight")
        public double dieWeight;
        @SerializedName("BindAmount")
        public int bindAmount;
        @SerializedName("Disinfect")
        public String disinfect;
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
        @SerializedName("InRegion")
        public InRegion inRegion;
        @SerializedName("InRegionID")
        public int inRegionID;
        @SerializedName("InRegionRI1")
        public int inRegionRI1;
        @SerializedName("InRegionRI2")
        public int inRegionRI2;
        @SerializedName("InRegionRI3")
        public int inRegionRI3;
        @SerializedName("InRegionRI4")
        public int inRegionRI4;
        @SerializedName("InRegionRI5")
        public int inRegionRI5;
        @SerializedName("ItemDatas")
        public List<ItemDatas> itemDatas;
        @SerializedName("IsDisinfect")
        public boolean isDisinfect;
        @SerializedName("CollectType")
        public int collectType;
        @SerializedName("DoHarmlessStatus")
        public int doHarmlessStatus;
        @SerializedName("DoHarmlessGUID")
        public Object doHarmlessGUID;
        @SerializedName("HarmlessSlagGUID")
        public Object harmlessSlagGUID;
        @SerializedName("HarmlessSlagStatus")
        public int harmlessSlagStatus;
        @SerializedName("DoHarmlessCheckStatus")
        public int doHarmlessCheckStatus;
        @SerializedName("CollectStockUser")
        public CollectStockUser collectStockUser;
        @SerializedName("CheckIdea")
        public Object checkIdea;
        @SerializedName("CLRemark")
        public Object cLRemark;
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
        @SerializedName("Dep_FactoryGUID")
        public DepFactoryGUID dep_FactoryGUID;

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

        public static class ImgFiles {
            @SerializedName("RuKuQianMing")
            public String ruKuQianMing;
            @SerializedName("ShenHeQianMing")
            public String shenHeQianMing;
        }

        public static class InRegion {
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

        public static class CollectStockUser {
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
            @SerializedName("Cst")
            public Object cst;
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public Object name;
            @SerializedName("CarID")
            public Object carID;
            @SerializedName("Point")
            public Object point;
            @SerializedName("Scale")
            public int scale;
            @SerializedName("Animal")
            public Animal animal;
            @SerializedName("IDCard")
            public String iDCard;
            @SerializedName("Region")
            public Region region;
            @SerializedName("Remark")
            public String remark;
            @SerializedName("Worker")
            public Worker worker;
            @SerializedName("CarInfo")
            public CarInfo carInfo;
            @SerializedName("Disease")
            public boolean disease;
            @SerializedName("EarTags")
            public Object earTags;
            public String _PartId;
            @SerializedName("BankName")
            public Object bankName;
            @SerializedName("CreateAt")
            public long createAt;
            @SerializedName("ImgFiles")
            public ImgFiles imgFiles;
            @SerializedName("Latitude")
            public Object latitude;
            @SerializedName("RegionID")
            public int regionID;
            @SerializedName("SourceId")
            public Object sourceId;
            @SerializedName("WorkerID")
            public Object workerID;
            @SerializedName("ApplyGUID")
            public String applyGUID;
            @SerializedName("CheckTime")
            public String checkTime;
            @SerializedName("CheckUser")
            public String checkUser;
            @SerializedName("CollectNo")
            public String collectNo;
            @SerializedName("CreatorId")
            public String creatorId;
            @SerializedName("DieAmount")
            public String dieAmount;
            @SerializedName("DieWeight")
            public String dieWeight;
            @SerializedName("Disinfect")
            public String disinfect;
            @SerializedName("ItemDatas")
            public List<ItemDatasSj> itemDatas;
            @SerializedName("Longitude")
            public Object longitude;
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
            @SerializedName("AnimalUnit")
            public AnimalUnit animalUnit;
            @SerializedName("BankCardNo")
            public String bankCardNo;
            @SerializedName("CategoryId")
            public String categoryId;
            @SerializedName("LastUpdate")
            public long lastUpdate;
            @SerializedName("ModifierId")
            public String modifierId;
            @SerializedName("CheckRemark")
            public String checkRemark;
            @SerializedName("CheckStatus")
            public int checkStatus;
            @SerializedName("CollectType")
            public int collectType;
            @SerializedName("CreateAtStr")
            public String createAtStr;
            @SerializedName("CreatorName")
            public String creatorName;
            @SerializedName("FactoryGUID")
            public String factoryGUID;
            @SerializedName("IsDisinfect")
            public boolean isDisinfect;
            @SerializedName("IsInsurance")
            public boolean isInsurance;
            @SerializedName("PartitionId")
            public String partitionId;
            @SerializedName("CategoryName")
            public String categoryName;
            @SerializedName("CategoryType")
            public String categoryType;
            @SerializedName("ModifierName")
            public String modifierName;
            @SerializedName("PartitionIds")
            public List<String> partitionIds;
            @SerializedName("CheckUnitType")
            public String checkUnitType;
            @SerializedName("Dep_ApplyGUID")
            public DepApplyGUID dep_ApplyGUID;
            @SerializedName("DieReasonType")
            public String dieReasonType;
            @SerializedName("InStoreStatus")
            public int inStoreStatus;
            @SerializedName("LastUpdateStr")
            public String lastUpdateStr;
            @SerializedName("CheckSignature")
            public Object checkSignature;
            @SerializedName("CheckboxStatus")
            public int checkboxStatus;
            @SerializedName("ZongZhongLiang")
            public int zongZhongLiang;
            @SerializedName("CollectCategory")
            public String collectCategory;
            @SerializedName("InsuranceCompany")
            public InsuranceCompany insuranceCompany;

            public static class Animal {
                public String key;
                @SerializedName("AnimalName")
                public String animalName;
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

            public static class ImgFiles {
                @SerializedName("BankPic")
                public String bankPic;
                @SerializedName("IdCardPic")
                public String idCardPic;
                @SerializedName("ShiTiPicList")
                public List<ShiTiPicList> shiTiPicList;
                @SerializedName("SiChuPicList")
                public List<SiChuPicList> siChuPicList;
                @SerializedName("ShenHeQianMing")
                public String shenHeQianMing;
                @SerializedName("ShouYunYuanPic")
                public String shouYunYuanPic;
                @SerializedName("ZhuangChePicList")
                public List<ZhuangChePicList> zhuangChePicList;
                @SerializedName("YangZhiChangHuPic")
                public String yangZhiChangHuPic;

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

            public static class AnimalUnit {
                public String key;
                @SerializedName("UnitName")
                public String unitName;
            }

            public static class DepApplyGUID {
                @SerializedName("Cst")
                public Object cst;
                @SerializedName("Mid")
                public String mid;
                @SerializedName("Name")
                public Object name;
                @SerializedName("Point")
                public Object point;
                @SerializedName("Animal")
                public Animal animal;
                @SerializedName("IDCard")
                public String iDCard;
                @SerializedName("Mobile")
                public String mobile;
                @SerializedName("Region")
                public Region region;
                @SerializedName("Remark")
                public Object remark;
                @SerializedName("ApplyNo")
                public String applyNo;
                @SerializedName("FarmMid")
                public String farmMid;
                public String _PartId;
                @SerializedName("AnimalID")
                public String animalID;
                @SerializedName("BankName")
                public Object bankName;
                @SerializedName("ImgFiles")
                public ImgFiles imgFiles;
                @SerializedName("Latitude")
                public Object latitude;
                @SerializedName("RegionID")
                public int regionID;
                @SerializedName("SourceId")
                public Object sourceId;
                @SerializedName("UserName")
                public String userName;
                @SerializedName("ApplyTime")
                public String applyTime;
                @SerializedName("ApplyType")
                public Object applyType;
                @SerializedName("CheckTime")
                public Object checkTime;
                @SerializedName("CheckUser")
                public Object checkUser;
                @SerializedName("DieAmount")
                public String dieAmount;
                @SerializedName("DieWeight")
                public Object dieWeight;
                @SerializedName("Longitude")
                public Object longitude;
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
                @SerializedName("ApplyPoint")
                public ApplyPoint applyPoint;
                @SerializedName("BankCardNo")
                public String bankCardNo;
                @SerializedName("SourceType")
                public int sourceType;
                @SerializedName("CheckRemark")
                public Object checkRemark;
                @SerializedName("CheckStatus")
                public int checkStatus;
                @SerializedName("IsApplySelf")
                public boolean isApplySelf;
                @SerializedName("ApplyAddress")
                public String applyAddress;
                @SerializedName("ApplyCategory")
                public String applyCategory;
                @SerializedName("CheckSignature")
                public Object checkSignature;

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
                    @SerializedName("IdCardPicBg")
                    public String idCardPicBg;
                }

                public static class ApplyPoint {
                    @SerializedName("ID")
                    public String iD;
                    @SerializedName("Name")
                    public String name;
                }
            }

            public static class InsuranceCompany {
                public String key;
                @SerializedName("InsuranceCompanyName")
                public String insuranceCompanyName;
            }

            public static class ItemDatasSj {
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
}
