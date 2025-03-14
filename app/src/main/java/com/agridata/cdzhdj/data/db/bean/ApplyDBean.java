package com.agridata.cdzhdj.data.db.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;



/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 15:33.
 * @Description :描述
 */
@Entity
public class ApplyDBean {

    @NonNull
    @PrimaryKey
    @SerializedName("Mid")
    public String mid;
    @SerializedName("UserName")
    public String userName;
    @SerializedName("Mobile")
    public String mobile;
    @SerializedName("DieAmount")
    public String dieAmount;
    @SerializedName("DieWeight")
    public String dieWeight;
    @SerializedName("AnimalID")
    public String animalID;
    @Embedded
    @SerializedName("Animal")
    public AnimalBean animal;
    @SerializedName("ApplyType")
    public String applyType;
    @SerializedName("FarmMid")
    public String farmMid;
    @SerializedName("Remark")
    public String remark;
    @SerializedName("ApplyTime")
    public String applyTime;
    @SerializedName("CheckUser")
    public String checkUser;
    @SerializedName("CheckTime")
    public String checkTime;
    @SerializedName("CheckSignature")
    public String checkSignature;
    @SerializedName("CheckRemark")
    public String checkRemark;
    @SerializedName("CheckStatus")
    public int checkStatus;
    @Embedded
    @SerializedName("Region")
    public RegionBean region;
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

    @Embedded
    @SerializedName("ApplyPoint")
    public ApplyPointBean applyPoint;
    @SerializedName("ApplyAddress")
    public String applyAddress;
    @SerializedName("IsApplySelf")
    public boolean isApplySelf;
    @SerializedName("ApplyNo")
    public String applyNo;

    @Embedded
    @SerializedName("ImgFiles")
    public ImgFilesBean imgFiles;
    @SerializedName("IDCard")
    public String iDCard;
    @SerializedName("BankName")
    public String bankName;
    @SerializedName("BankCardNo")
    public String bankCardNo;
    @SerializedName("SourceType")
    public int sourceType;
    @SerializedName("CreateAt")
    public long createAt;
    @SerializedName("LastUpdate")
    public long lastUpdate;
    @SerializedName("CreateAtStr")
    public String createAtStr;
    @SerializedName("LastUpdateStr")
    public String lastUpdateStr;


    public static class AnimalBean {
        @ColumnInfo(name = "AnimalBeanID")
        @SerializedName("ID")
        public int iD;
        @ColumnInfo(name = "AnimalBeanName")
        @SerializedName("Name")
        public String name;
    }

    public static class RegionBean {
        @ColumnInfo(name = "RegionBeanID")
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

    public static class ApplyPointBean {
        @ColumnInfo(name = "ApplyPointBeanID")
        @SerializedName("ID")
        public String iD;
        @ColumnInfo(name = "ApplyPointBeanName")
        @SerializedName("Name")
        public String name;
    }

    public static class ImgFilesBean {
        @SerializedName("BankPic")
        public String bankPic;
        @SerializedName("IdCardPic")
        public String idCardPic;
        @SerializedName("IdCardPicBg")
        public String idCardPicBg;

        @Override
        public String toString() {
            return "ImgFilesBean{" +
                    "bankPic='" + bankPic + '\'' +
                    ", idCardPic='" + idCardPic + '\'' +
                    ", idCardPicBg='" + idCardPicBg + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResultBean{" + "mid='" + mid + '\'' + ", userName='" + userName + '\'' + ", mobile='" + mobile + '\'' + ", dieAmount='" + dieAmount + '\'' + ", dieWeight='" + dieWeight + '\'' + ", animalID='" + animalID + '\'' + ", animal=" + animal + ", applyType='" + applyType + '\'' + ", farmMid='" + farmMid + '\'' + ", remark=" + remark + ", applyTime='" + applyTime + '\'' + ", checkUser=" + checkUser + ", checkTime=" + checkTime + ", checkSignature=" + checkSignature + ", checkRemark=" + checkRemark + ", checkStatus=" + checkStatus + ", region=" + region + ", regionID=" + regionID + ", regionRI1=" + regionRI1 + ", regionRI2=" + regionRI2 + ", regionRI3=" + regionRI3 + ", regionRI4=" + regionRI4 + ", regionRI5=" + regionRI5 + ", applyCategory='" + applyCategory + '\'' + ", applyPoint=" + applyPoint + ", applyAddress='" + applyAddress + '\'' + ", isApplySelf=" + isApplySelf + ", applyNo='" + applyNo + '\'' + ", imgFiles=" + imgFiles + ", iDCard='" + iDCard + '\'' + ", bankName=" + bankName + ", bankCardNo='" + bankCardNo + '\'' + ", sourceType=" + sourceType + ", createAt=" + createAt + ", lastUpdate=" + lastUpdate + ", createAtStr='" + createAtStr + '\'' + ", lastUpdateStr='" + lastUpdateStr + '\'' + '}';
    }
}


