package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 11:50.
 */


public class BeiAnBean  {

    public String Mid;
    public String SourceId;
    public String Name;
    public String UserName;
    public String Mobile;
    public String DieAmount;
    public String DieWeight;
    public String AnimalID;
    public AnimalInfo Animal;
    public String ApplyType;
    public String FarmMid;
    public String Remark;
    public String ApplyTime;
    public String CheckUser;
    public String CheckTime;
    public String CheckSignature;
    public String CheckRemark;
    public String _PartId;
    public int CheckStatus;

    public RegionBean Region;
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

    public ImgFilesBean ImgFiles;
    public String IDCard;
    public String BankName;
    public String BankCardNo;
    public long CreateAt;
    public long LastUpdate;
    public String CreateAtStr;
    public String LastUpdateStr;
    public String CreatorId;
    public String ModifierId;
    public String CreatorName;
    public String ModifierName;
    public String PartitionId;
    public String CategoryId;
    public String CategoryName;
    public String CategoryType;
    public String Longitude;
    public String Latitude;
    public String Point;
    public String Cst;


    public CarInfoBean CarInfo;


    public static  class  AnimalInfo{

        public String ID;
        public String Name;


    }

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
        public String RI1RegionName;
        public String RI2RegionName;
        public String RI3RegionName;
        public String RI4RegionName;
        public String RI5RegionName;
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
                    ", RI1RegionName='" + RI1RegionName + '\'' +
                    ", RI2RegionName='" + RI2RegionName + '\'' +
                    ", RI3RegionName='" + RI3RegionName + '\'' +
                    ", RI4RegionName='" + RI4RegionName + '\'' +
                    ", RI5RegionName='" + RI5RegionName + '\'' +
                    ", RegionFullName='" + RegionFullName + '\'' +
                    ", RegionParentID=" + RegionParentID +
                    '}';
        }
    }


    public static class ApplyPointBean  {
        @SerializedName("ID")
        public String id;
        public String Name;

        @Override
        public String toString() {
            return "ApplyPointBean{" +
                    "id='" + id + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }


    public static class ImgFilesBean {
        public String BankPic;
        public String IdCardPic;
        public String IdCardPicBg;

        @Override
        public String toString() {
            return "ImgFilesBean{" +
                    "BankPic='" + BankPic + '\'' +
                    ", IdCardPic='" + IdCardPic + '\'' +
                    ", IdCardPicBg='" + IdCardPicBg + '\'' +
                    '}';
        }
    }

    public static class CarInfoBean {
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

    @Override
    public String toString() {
        return "BeiAnBean{" +
                "Mid='" + Mid + '\'' +
                ", SourceId=" + SourceId +
                ", Name=" + Name +
                ", UserName='" + UserName + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", DieAmount=" + DieAmount +
                ", DieWeight=" + DieWeight +
                ", AnimalID=" + AnimalID +
                ", Animal=" + Animal +
                ", ApplyType=" + ApplyType +
                ", FarmMid=" + FarmMid +
                ", Remark=" + Remark +
                ", ApplyTime=" + ApplyTime +
                ", CheckUser=" + CheckUser +
                ", CheckTime=" + CheckTime +
                ", CheckSignature=" + CheckSignature +
                ", CheckRemark=" + CheckRemark +
                ", _PartId='" + _PartId + '\'' +
                ", CheckStatus=" + CheckStatus +
                ", Region=" + Region +
                ", RegionID=" + RegionID +
                ", RegionRI1=" + RegionRI1 +
                ", RegionRI2=" + RegionRI2 +
                ", RegionRI3=" + RegionRI3 +
                ", RegionRI4=" + RegionRI4 +
                ", RegionRI5=" + RegionRI5 +
                ", ApplyCategory='" + ApplyCategory + '\'' +
                ", ApplyPoint=" + ApplyPoint +
                ", ApplyAddress='" + ApplyAddress + '\'' +
                ", IsApplySelf=" + IsApplySelf +
                ", ApplyNo='" + ApplyNo + '\'' +
                ", ImgFiles=" + ImgFiles +
                ", IDCard='" + IDCard + '\'' +
                ", BankName='" + BankName + '\'' +
                ", BankCardNo='" + BankCardNo + '\'' +
                ", CreateAt=" + CreateAt +
                ", LastUpdate=" + LastUpdate +
                ", CreateAtStr='" + CreateAtStr + '\'' +
                ", LastUpdateStr='" + LastUpdateStr + '\'' +
                ", CreatorId='" + CreatorId + '\'' +
                ", ModifierId='" + ModifierId + '\'' +
                ", CreatorName='" + CreatorName + '\'' +
                ", ModifierName='" + ModifierName + '\'' +
                ", PartitionId='" + PartitionId + '\'' +
                ", CategoryId='" + CategoryId + '\'' +
                ", CategoryName='" + CategoryName + '\'' +
                ", CategoryType='" + CategoryType + '\'' +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                ", Point=" + Point +
                ", Cst=" + Cst +
                ", CarInfo=" + CarInfo +
                '}';
    }


}


