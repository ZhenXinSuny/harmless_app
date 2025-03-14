package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class XdrCollectListBean {


    public int code;
    public String msg;
    public List<Data> data;

    public static class Data {
        @SerializedName("BankName")
        public String bankName;
        @SerializedName("InsuranceCompany")
        public InsuranceCompany insuranceCompany;
        @SerializedName("CollectType")
        public int collectType;
        @SerializedName("Disease")
        public boolean disease;
        public List<String> partition_ids;
        public String last_update;
        @SerializedName("RegionID")
        public int regionID;
        public String app_id;
        @SerializedName("ImgFiles")
        public ImgFiles imgFiles;
        public double longitude;
        @SerializedName("IDCard")
        public String iDCard;
        @SerializedName("DieAmount")
        public String dieAmount;
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
        public String _PartId;
        @SerializedName("IsInsurance")
        public boolean isInsurance;
        @SerializedName("CarInfo")
        public CarInfo carInfo;
        @SerializedName("Animal")
        public Animal animal;
        @SerializedName("InStoreStatus")
        public int inStoreStatus;
        public String creator_id;
        @SerializedName("Region")
        public Region region;
        public double latitude;
        @SerializedName("CheckStatus")
        public int checkStatus;
        public String mid;
        @SerializedName("AnimalUnit")
        public AnimalUnit animalUnit;
        public String partition_id;
        public String category_id;
        public int data_id;
        @SerializedName("DieReasonType")
        public String dieReasonType;
        @SerializedName("BankCardNo")
        public String bankCardNo;
        @SerializedName("Worker")
        public Worker worker;
        public boolean disabled;
        @SerializedName("ItemDatas")
        public ItemDatas itemDatas;
        @SerializedName("CollectNo")
        public String collectNo;
        @SerializedName("CheckTime")
        public String checkTime;
        @SerializedName("Disinfect")
        public String disinfect;
        @SerializedName("ApplyGUID")
        public String applyGUID;
        @SerializedName("CheckUser")
        public String checkUser;
        @SerializedName("Scale")
        public int scale;
        @SerializedName("DieWeight")
        public String dieWeight;
        @SerializedName("IsDisinfect")
        public boolean isDisinfect;
        @SerializedName("FactoryGUID")
        public String factoryGUID;
        public boolean deleted;
        @SerializedName("CollectCategory")
        public String collectCategory;
        public String modifier_id;
        @SerializedName("CheckUnitType")
        public String checkUnitType;
        public String create_at;

        public static class InsuranceCompany {
            public String type;
            public String value;
        }

        public static class ImgFiles {
            public String type;
            public String value;
        }

        public static class CarInfo {
            public String type;
            public String value;
        }

        public static class Animal {
            public String type;
            public String value;
        }

        public static class Region {
            public String type;
            public String value;
        }

        public static class AnimalUnit {
            public String type;
            public String value;
        }

        public static class Worker {
            public String type;
            public String value;
        }

        public static class ItemDatas {
            public String type;
            public String value;
        }
    }
}
