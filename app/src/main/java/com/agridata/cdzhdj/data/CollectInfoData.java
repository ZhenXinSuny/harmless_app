package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 14:16.
 */
public class CollectInfoData implements Serializable {

    public String applyGUID;
    public String creatorId;

    public String collectNo;//收集单号
    public AnimalBean animal;//动物ID
    public AnimalUnitBean animalUnit;//收集单位
    public String dieAmount;//核验数量
    public String dieWeight;//总重量
    public int scale;//存栏量
    public boolean isDisinfect;//是否消毒
    public String disinfect;//消毒药品
    public boolean disease;//是否重大疫病
    public String dieReasonType;//死亡原因
    public boolean isInsurance;//是否购买保险
    public String latitude;
    public String longitude;
    public int collectType =1;
    public CarInfoBean carInfo;
    public InsuranceCompanyBean insuranceCompany;//保险公司

    public String bankName;
    public String idcard;
    public String bankCardNo;
    public  String  factoryGUID;
    public  String  remark;
    public ImgFilesBean imgFiles;
    public RegionBean  region;

    public  List<DataItemBean> itemDatas;

    public WokerBean worker;
    public String farmMid;

    public String xdrName;
    public String xdrMobile;
    public String xdrAddress;
    public int  bankType;
    public boolean isFoster;
    public String fosterCareName ;
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
        public String RI5RegionName;
        public String RegionFullName;
        public int RegionParentID;
    }
    public static class CarInfoBean implements Serializable {
        public String Mid;
        public String Name;

        @Override
        public String toString() {
            return "CarInfoBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public static class AnimalUnitBean implements Serializable {
        public String key;
        public String UnitName;

        @Override
        public String toString() {
            return "AnimalUnitBean{" +
                    "key='" + key + '\'' +
                    ", UnitName='" + UnitName + '\'' +
                    '}';
        }
    }

    public static class AnimalBean implements Serializable {
        public String key;
        public String AnimalName;

        @Override
        public String toString() {
            return "AnimalBean{" +
                    "key='" + key + '\'' +
                    ", AnimalName='" + AnimalName + '\'' +
                    '}';
        }
    }

    public static class InsuranceCompanyBean implements Serializable {
        public String key;
        public String InsuranceCompanyName;

        @Override
        public String toString() {
            return "InsuranceCompanyBean{" +
                    "key='" + key + '\'' +
                    ", InsuranceCompanyName='" + InsuranceCompanyName + '\'' +
                    '}';
        }
    }

    public static class ImgFilesBean implements Serializable {
        public String IdCardPic;
        public String BankPic;
        public List<ImgMidBean> SiChuPicList;
        public List<ImgMidBean> ShiTiPicList;
        public List<ImgMidBean> ZhuangChePicList;
        public String ShouYunYuanPic;
        public String YangZhiChangHuPic;
        public String CollectCertPic;


        public static class ImgMidBean implements Serializable {
            public String Mid;
        }

        @Override
        public String toString() {
            return "ImgFilesBean{" +
                    "IdCardPic='" + IdCardPic + '\'' +
                    ", BankPic='" + BankPic + '\'' +
                    ", SiChuPicList=" + SiChuPicList +
                    ", ShiTiPicList=" + ShiTiPicList +
                    ", ZhuangChePicList=" + ZhuangChePicList +
                    ", ShouYunYuanPic='" + ShouYunYuanPic + '\'' +
                    ", YangZhiChangHuPic='" + YangZhiChangHuPic + '\'' +
                    ", CollectCertPic='" + CollectCertPic + '\'' +
                    '}';
        }
    }

    public static  class  DataItemBean implements Serializable{
        public String EarTagNo;
        public String AnimalID;
        public String BodyWeight;
        public String Name;
        public int AnimalType;
        public int IsSow;//0 否 1是 仅限 AnimalType=1 猪大于20kg以上
        public String Amount;//数量

        @Override
        public String toString() {
            return "DataItemBean{" +
                    "EarTagNo='" + EarTagNo + '\'' +
                    ", AnimalID='" + AnimalID + '\'' +
                    ", BodyWeight='" + BodyWeight + '\'' +
                    ", Name='" + Name + '\'' +
                    ", AnimalType=" + AnimalType +
                    ", IsSow=" + IsSow +
                    '}';
        }
    }

    public static  class  WokerBean implements Serializable {
        public String Mid;
        public String Name;
        public String Mobile;

        @Override
        public String toString() {
            return "WokerBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    ", Mobile='" + Mobile + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CollectInfoData{" +
                "applyGUID='" + applyGUID + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", collectNo='" + collectNo + '\'' +
                ", animal=" + animal +
                ", animalUnit=" + animalUnit +
                ", dieAmount='" + dieAmount + '\'' +
                ", dieWeight='" + dieWeight + '\'' +
                ", scale=" + scale +
                ", isDisinfect=" + isDisinfect +
                ", disinfect='" + disinfect + '\'' +
                ", disease=" + disease +
                ", dieReasonType='" + dieReasonType + '\'' +
                ", isInsurance=" + isInsurance +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", collectType=" + collectType +
                ", carInfo=" + carInfo +
                ", insuranceCompany=" + insuranceCompany +
                ", bankName='" + bankName + '\'' +
                ", idcard='" + idcard + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", factoryGUID='" + factoryGUID + '\'' +
                ", remark='" + remark + '\'' +
                ", imgFiles=" + imgFiles +
                ", region=" + region +
                ", itemDatas=" + itemDatas +
                ", worker=" + worker +
                ", farmMid='" + farmMid + '\'' +
                ", xdrName='" + xdrName + '\'' +
                ", xdrMobile='" + xdrMobile + '\'' +
                ", xdrAddress='" + xdrAddress + '\'' +
                ", bankType=" + bankType +
                ", isFoster=" + isFoster +
                ", fosterCareName='" + fosterCareName + '\'' +
                '}';
    }
}
