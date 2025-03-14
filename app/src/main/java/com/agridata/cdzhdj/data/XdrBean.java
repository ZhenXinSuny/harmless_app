package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class XdrBean {


    public int code;
    public String msg;
    public List<Data> data;

    public static class Data {
        @SerializedName("Bank")
        public String bank;
        public double latitude;
        public String mid;
        public List<String> partition_ids;
        @SerializedName("IDCardPhoto")
        public String iDCardPhoto;
        public String partition_id;
        @SerializedName("IDCardNo")
        public String iDCardNo;
        public String category_id;
        public int data_id;
        public String last_update;
        @SerializedName("DisplayName")
        public String displayName;
        @SerializedName("IDCardPhotos")
        public String iDCardPhotos;
        public boolean disabled;
        public String app_id;
        public double longitude;
        @SerializedName("CreateCodeStatus")
        public String createCodeStatus;
        @SerializedName("Addresss")
        public String addresss;
        @SerializedName("BankAccount")
        public String bankAccount;
        public int whhcount;
        public String _PartId;
        @SerializedName("Mobile")
        public String mobile;
        @SerializedName("LegalPersonTel")
        public String legalPersonTel;
        @SerializedName("BankPic")
        public String bankPic;
        public boolean deleted;
        public String creator_id;
        public String modifier_id;
        @SerializedName("Region")
        public Region region;
        public String create_at;
        public int cst;
        @SerializedName("XDRType")
        public int xDRType;
        @SerializedName("RegionID")
        public int regionID;
        @SerializedName("AnimalVariety")
        public AnimalVariety animalVariety;
        @SerializedName("UserMid")
        public String userMid;

        public static class Region {
            public String type;
            public String value;
        }

        public static class AnimalVariety {
            public String type;
            public String value;
        }
    }
}
