package com.agridata.cdzhdj.data.entrycheck;

import com.agridata.cdzhdj.data.CollectedDetailBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-28 16:50.
 * @Description :描述 上传入场查验信息
 */
public class UpECETBean {
    public String Mid;
    public String CategoryId =  "e4ce92598ac54a06ae03d3c597f506be";
    public String  CategoryName = "7EFF7BD6B670DEA1-T_Entry_Check";
    public  String _PartId="d5896b31964e425382df52f655dedfc2";
    public String CertNo;
    public String CheckType;
    public List<String> EarTags;
    public String  ErrorEarTags;//错误耳标
    public List<String> Imgs;
    public int Counts;
    public String CarHead;
    public int CheckResult;
    public int TimeIsPass;


    public int AddressIsPass;
    public int CarIsPass;
    public int RoadIsPass;
    public int NumberIsPass;
    public int EarTagIsPass;
    public String CheckTime;
    public CheckUserBean CheckUser;
    public String SlaughterHouseID;

    public RegionBean Region;

    public String Owner;
    public String SlaughterHouseName;
    public String  AnimalName;
    public String   PlaceDepartureName;
    public String CarNumber;

    public int  ActualNumber;
    public int   AnomalyNumber;
    public int  DeathNumber;

    public String AppVersion;

    public  String  CertAmount;

    public String OtherProvincesCertPhoto;

    public static class RegionBean {
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
        public String RegionFullName;
        public int RegionParentID;

        @Override
        public String toString() {
            return "RegionBean{" +
                    "id=" + id +
                    ", RI1=" + RI1 +
                    ", RI2=" + RI2 +
                    ", RI3=" + RI3 +
                    ", RI4=" + RI4 +
                    ", RI5=" + RI5 +
                    ", RegionCode='" + RegionCode + '\'' +
                    ", RegionName='" + RegionName + '\'' +
                    ", RegionLevel=" + RegionLevel +
                    ", RegionFullName='" + RegionFullName + '\'' +
                    ", RegionParentID=" + RegionParentID +
                    '}';
        }
    }

    public static class CheckUserBean {
        public String Key;
        public String Name;

        @Override
        public String toString() {
            return "CheckUserBean{" +
                    "Key='" + Key + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public String CertType;

    @Override
    public String toString() {
        return "UpECETBean{" +
                "Mid='" + Mid + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", _PartId='" + _PartId + '\'' +
                ", CertNo='" + CertNo + '\'' +
                ", CheckType='" + CheckType + '\'' +
                ", EarTags=" + EarTags +
                ", ErrorEarTags='" + ErrorEarTags + '\'' +
                ", Imgs=" + Imgs +
                ", Counts=" + Counts +
                ", CarHead='" + CarHead + '\'' +
                ", CheckResult=" + CheckResult +
                ", TimeIsPass=" + TimeIsPass +
                ", AddressIsPass=" + AddressIsPass +
                ", CarIsPass=" + CarIsPass +
                ", RoadIsPass=" + RoadIsPass +
                ", NumberIsPass=" + NumberIsPass +
                ", EarTagIsPass=" + EarTagIsPass +
                ", CheckTime='" + CheckTime + '\'' +
                ", CheckUser=" + CheckUser +
                ", SlaughterHouseID='" + SlaughterHouseID + '\'' +
                ", Region=" + Region +
                ", Owner='" + Owner + '\'' +
                ", SlaughterHouseName='" + SlaughterHouseName + '\'' +
                ", AnimalName='" + AnimalName + '\'' +
                ", PlaceDepartureName='" + PlaceDepartureName + '\'' +
                ", CarNumber='" + CarNumber + '\'' +
                ", ActualNumber=" + ActualNumber +
                ", AnomalyNumber=" + AnomalyNumber +
                ", DeathNumber=" + DeathNumber +
                ", AppVersion='" + AppVersion + '\'' +
                ", CertType='" + CertType + '\'' +
                '}';
    }
}
