package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/7/7
 * @time 17:35.
 */

public class ShouJiBianJiBean implements Serializable {


    public int Status;
    public int ErrorCode;
    public String Message;
    public ResultBean Result;


    public static class ResultBean implements Serializable {
        public String Mid;
        public Object SourceId;
        public Object Name;
        public String ApplyGUID;
        public String CollectNo;
        public int CollectType;
        public String FactoryGUID;
        public String CollectCategory;
        public String CheckUnitType;
        public String DieAmount;
        public String DieWeight;
        public WorkerBean Worker;
        public CarInfoBean CarInfo;
        public Object WorkerID;
        public Object CarID;
        public String Disinfect;
        public boolean Disease;
        public String DieReasonType;
        public String Remark;
        public RegionBean Region;
        public int Scale;
        public Object EarTags;
        public ImgFilesBean ImgFiles;
        public String _PartId;
        public Object CheckUser;
        public Object CheckTime;
        public int CheckStatus;
        public Object CheckSignature;
        public String CheckRemark;
        public int RegionID;
        public int RegionRI1;
        public int RegionRI2;
        public int RegionRI3;
        public int RegionRI4;
        public int RegionRI5;
        public boolean IsInsurance;
        public boolean IsDisinfect;
        public String FosterCareName;
        public boolean IsFoster;
        public InsuranceCompanyBean InsuranceCompany;
        public String IDCard;
        public String BankName;
        public String BankCardNo;
        public AnimalUnitBean AnimalUnit;
        public AnimalBean Animal;
        public int InStoreStatus;
        public long CreateAt;
        public long LastUpdate;
        public String CreateAtStr;
        public String LastUpdateStr;
        public String CreatorId;
        public String ModifierId;
        public Object CreatorName;
        public String ModifierName;
        public String PartitionId;
        public String CategoryId;
        public String CategoryName;
        public String CategoryType;
        public Object Longitude;
        public Object Latitude;
        public Object Point;
        public Object Cst;
        public DepApplyGUIDBean Dep_ApplyGUID;
        public List<ItemDatasBean> ItemDatas;
        public List<String> PartitionIds;
        public  int BankType;


        public static class WorkerBean implements Serializable {
            public String Mid;
            public String Name;
            public String Mobile;
        }

        public static class  CarInfoBean implements Serializable{
            public  String Mid;
            public String Name;
        }
        public static class RegionBean implements Serializable {
            @SerializedName("ID")
            public int id;
            public int RI1;
            public int RI2;
            public int RI3;
            public int RI4;
            public int RI5;
            public String RegionCode;
            public String RegionName;
            public int RegionLevel;
            public String RI1RegionName;
            public String RI2RegionName;
            public String RI3RegionName;
            public String RI4RegionName;
            public String RegionFullName;
            public int RegionParentID;
        }


        public static class ImgFilesBean implements Serializable {
            public String BankPic;
            public String IdCardPic;
            public String ShouYunYuanPic;
            public String YangZhiChangHuPic;
            public List<PicBen> ShiTiPicList;
            public List<PicBen> SiChuPicList;
            public List<PicBen> ZhuangChePicList;
            public String CollectCertPic;


            public static class PicBen implements Serializable {
                public String Mid;
            }
        }




        public static class InsuranceCompanyBean implements Serializable {
            public String key;
            public String InsuranceCompanyName;
        }


        public static class AnimalUnitBean implements Serializable {
            public String key;
            public String UnitName;
        }


        public static class AnimalBean implements Serializable {
            public String key;
            public String AnimalName;
        }


        public static class DepApplyGUIDBean implements Serializable {
            public String Mid;
            public Object SourceId;
            public Object Name;
            public String UserName;
            public String Mobile;
            public Object DieAmount;
            public Object DieWeight;
            public Object AnimalID;
            public Object Animal;
            public Object ApplyType;
            public String FarmMid;
            public String Remark;
            public String ApplyTime;
            public Object CheckUser;
            public Object CheckTime;
            public Object CheckSignature;
            public Object CheckRemark;
            public String _PartId;
            public int CheckStatus;
            public RegionBeanX Region;
            public int RegionID;
            public int RegionRI1;
            public int RegionRI2;
            public int RegionRI3;
            public int RegionRI4;
            public int RegionRI5;
            public String ApplyCategory;
            public ApplyPointBean ApplyPoint;
            public String ApplyAddress;
            public boolean IsApplySelf;
            public String ApplyNo;
            public ImgFilesBeanX ImgFiles;
            public String IDCard;
            public String BankName;
            public String BankCardNo;
            public Object Longitude;
            public Object Latitude;
            public Object Point;
            public Object Cst;


            public static class RegionBeanX implements Serializable {
                @SerializedName("ID")
                public int id;
                public int RI1;
                public int RI2;
                public int RI3;
                public int RI4;
                public int RI5;
                public String RegionCode;
                public String RegionName;
                public int RegionLevel;
                public String RI1RegionName;
                public String RI2RegionName;
                public String RI3RegionName;
                public String RI4RegionName;
                public Object RI5RegionName;
                public String RegionFullName;
                public int RegionParentID;
            }


            public static class ApplyPointBean implements Serializable {
                @SerializedName("ID")
                public String id;
                public String Name;
            }


            public static class ImgFilesBeanX implements Serializable {
                public String BankPic;
                public String IdCardPic;
                public String IdCardPicBg;
            }
        }


        public static class ItemDatasBean implements Serializable {
            public String Name;
            public String AnimalID;
            public String EarTagNo;
            public String BodyWeight;
            public int AnimalType;
            public String Amount;
            public int IsSow;
        }
    }
}
