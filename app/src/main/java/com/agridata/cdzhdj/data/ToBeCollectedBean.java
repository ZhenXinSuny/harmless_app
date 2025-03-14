package com.agridata.cdzhdj.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ToBeCollectedBean {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public Result result;

    @Override
    public String toString() {
        return "ToBeCollectedBean{" +
                "status=" + status +
                ", errorCode=" + errorCode +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public static class Result {
        public int pageSize;
        public int pageNo;
        public int pageCount;
        public int totalCount;
        public int itemCount;
        public List<PageItems> pageItems;

        @Override
        public String toString() {
            return "Result{" +
                    "pageSize=" + pageSize +
                    ", pageNo=" + pageNo +
                    ", pageCount=" + pageCount +
                    ", totalCount=" + totalCount +
                    ", itemCount=" + itemCount +
                    ", pageItems=" + pageItems +
                    '}';
        }

        public static class PageItems {
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

            @Override
            public String toString() {
                return "PageItems{" +
                        "mid='" + mid + '\'' +
                        ", sourceId=" + sourceId +
                        ", name=" + name +
                        ", userName='" + userName + '\'' +
                        ", mobile='" + mobile + '\'' +
                        ", dieAmount='" + dieAmount + '\'' +
                        ", dieWeight=" + dieWeight +
                        ", animalID='" + animalID + '\'' +
                        ", animal=" + animal +
                        ", applyType=" + applyType +
                        ", farmMid='" + farmMid + '\'' +
                        ", remark=" + remark +
                        ", applyTime='" + applyTime + '\'' +
                        ", checkUser=" + checkUser +
                        ", checkTime=" + checkTime +
                        ", checkSignature=" + checkSignature +
                        ", checkRemark=" + checkRemark +
                        ", _PartId='" + _PartId + '\'' +
                        ", checkStatus=" + checkStatus +
                        ", region=" + region +
                        ", regionID=" + regionID +
                        ", regionRI1=" + regionRI1 +
                        ", regionRI2=" + regionRI2 +
                        ", regionRI3=" + regionRI3 +
                        ", regionRI4=" + regionRI4 +
                        ", regionRI5=" + regionRI5 +
                        ", applyCategory='" + applyCategory + '\'' +
                        ", applyPoint=" + applyPoint +
                        ", applyAddress='" + applyAddress + '\'' +
                        ", isApplySelf=" + isApplySelf +
                        ", applyNo='" + applyNo + '\'' +
                        ", imgFiles=" + imgFiles +
                        ", iDCard='" + iDCard + '\'' +
                        ", bankName='" + bankName + '\'' +
                        ", bankCardNo='" + bankCardNo + '\'' +
                        ", sourceType=" + sourceType +
                        ", createAt=" + createAt +
                        ", lastUpdate=" + lastUpdate +
                        ", createAtStr='" + createAtStr + '\'' +
                        ", lastUpdateStr='" + lastUpdateStr + '\'' +
                        ", creatorId='" + creatorId + '\'' +
                        ", modifierId='" + modifierId + '\'' +
                        ", creatorName='" + creatorName + '\'' +
                        ", modifierName='" + modifierName + '\'' +
                        ", partitionId='" + partitionId + '\'' +
                        ", partitionIds=" + partitionIds +
                        ", categoryId='" + categoryId + '\'' +
                        ", categoryName='" + categoryName + '\'' +
                        ", categoryType='" + categoryType + '\'' +
                        '}';
            }

            public static class Animal {
                @SerializedName("ID")
                public int iD;
                @SerializedName("Name")
                public String name;

                @Override
                public String toString() {
                    return "Animal{" +
                            "iD=" + iD +
                            ", name='" + name + '\'' +
                            '}';
                }
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

                @Override
                public String toString() {
                    return "Region{" +
                            "iD=" + iD +
                            ", rI1=" + rI1 +
                            ", rI2=" + rI2 +
                            ", rI3=" + rI3 +
                            ", rI4=" + rI4 +
                            ", rI5=" + rI5 +
                            ", regionCode='" + regionCode + '\'' +
                            ", regionName='" + regionName + '\'' +
                            ", regionLevel=" + regionLevel +
                            ", rI1RegionName='" + rI1RegionName + '\'' +
                            ", rI2RegionName='" + rI2RegionName + '\'' +
                            ", rI3RegionName='" + rI3RegionName + '\'' +
                            ", rI4RegionName='" + rI4RegionName + '\'' +
                            ", regionFullName='" + regionFullName + '\'' +
                            ", regionParentID=" + regionParentID +
                            '}';
                }
            }

            public static class ApplyPoint {
                @SerializedName("ID")
                public String iD;
                @SerializedName("Name")
                public String name;

                @Override
                public String toString() {
                    return "ApplyPoint{" +
                            "iD='" + iD + '\'' +
                            ", name='" + name + '\'' +
                            '}';
                }
            }

            public static class ImgFiles {
                @SerializedName("BankPic")
                public String bankPic;
                @SerializedName("IdCardPic")
                public String idCardPic;
                @SerializedName("IdCardPicBg")
                public String idCardPicBg;

                @Override
                public String toString() {
                    return "ImgFiles{" +
                            "bankPic='" + bankPic + '\'' +
                            ", idCardPic='" + idCardPic + '\'' +
                            ", idCardPicBg='" + idCardPicBg + '\'' +
                            '}';
                }
            }
        }
    }
}
