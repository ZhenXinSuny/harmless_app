package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-29 14:11.
 * @Description :描述
 */
public class EntryCheckABean implements Serializable {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public Result result;

    public static class Result implements Serializable{
        @SerializedName("Mid")
        public String mid;
        @SerializedName("SourceId")
        public Object sourceId;
        @SerializedName("Name")
        public Object name;
        public int old_id;
        @SerializedName("CCType")
        public int cCType;
        @SerializedName("QRCode")
        public String qRCode;
        @SerializedName("FactoryCode")
        public String factoryCode;
        @SerializedName("Owner")
        public String owner;
        @SerializedName("OwnerTel")
        public String ownerTel;
        @SerializedName("Amount")
        public int amount;
        @SerializedName("StartingPlaceRegion")
        public StartingPlaceRegion startingPlaceRegion;
        @SerializedName("StartingPlaceName")
        public String startingPlaceName;
        @SerializedName("DestinationPlaceRegion")
        public DestinationPlaceRegion destinationPlaceRegion;
        @SerializedName("DestinationPlaceTownName")
        public String destinationPlaceTownName;
        @SerializedName("DestinationPlaceName")
        public String destinationPlaceName;
        @SerializedName("Carrier")
        public String carrier;
        @SerializedName("CarrierTel")
        public String carrierTel;
        @SerializedName("TransportNumber")
        public String transportNumber;
        @SerializedName("TransportRecordNo")
        public Object transportRecordNo;
        @SerializedName("DisinfectType")
        public String disinfectType;
        @SerializedName("AgengcyName")
        public String agengcyName;
        @SerializedName("QuarantineOfficerName")
        public String quarantineOfficerName;
        @SerializedName("DateOfIssue")
        public String dateOfIssue;
        @SerializedName("EarTags")
        public String earTags;
        @SerializedName("Remark")
        public String remark;
        @SerializedName("AgencyTel")
        public String agencyTel;
        @SerializedName("ClientCreateTime")
        public String clientCreateTime;
        public String _PartId;
        @SerializedName("UUID")
        public String uUID;
        @SerializedName("CheckNormalCount")
        public Object checkNormalCount;
        @SerializedName("CheckAbnormalCount")
        public Object checkAbnormalCount;
        @SerializedName("CheckDeadCount")
        public Object checkDeadCount;
        @SerializedName("CheckRemark")
        public Object checkRemark;
        @SerializedName("CheckUserMid")
        public Object checkUserMid;
        @SerializedName("CheckTime")
        public Object checkTime;
        @SerializedName("DestinationPlaceRegionID")
        public int destinationPlaceRegionID;
        @SerializedName("DestinationPlaceRegionRI1")
        public int destinationPlaceRegionRI1;
        @SerializedName("DestinationPlaceRegionRI2")
        public int destinationPlaceRegionRI2;
        @SerializedName("DestinationPlaceRegionRI3")
        public int destinationPlaceRegionRI3;
        @SerializedName("DestinationPlaceRegionRI4")
        public int destinationPlaceRegionRI4;
        @SerializedName("DestinationPlaceRegionRI5")
        public int destinationPlaceRegionRI5;
        @SerializedName("Status")
        public Status status;
        @SerializedName("CCOutInType")
        public CCOutInType cCOutInType;
        @SerializedName("PrintStatus")
        public PrintStatus printStatus;
        @SerializedName("IsOnlineIssuing")
        public IsOnlineIssuing isOnlineIssuing;
        @SerializedName("ValidityDays")
        public ValidityDays validityDays;
        @SerializedName("TransportType")
        public TransportType transportType;
        @SerializedName("UsageType")
        public UsageType usageType;
        @SerializedName("DestinationPlaceType")
        public DestinationPlaceType destinationPlaceType;
        @SerializedName("StartingPlaceType")
        public StartingPlaceType startingPlaceType;
        @SerializedName("AnimalType")
        public AnimalType animalType;
        @SerializedName("Unit")
        public Unit unit;
        @SerializedName("StartingPlaceCompany")
        public StartingPlaceCompany startingPlaceCompany;
        @SerializedName("DestinationPlaceCompany")
        public DestinationPlaceCompany destinationPlaceCompany;
        @SerializedName("NewEarTag")
        public Object newEarTag;
        @SerializedName("CheckResult")
        public CheckResultBean checkResult;
        @SerializedName("CheckInValidityDays")
        public Object checkInValidityDays;
        @SerializedName("CheckNormalCountEquAmount")
        public Object checkNormalCountEquAmount;
        @SerializedName("CheckEartagEquCC")
        public Object checkEartagEquCC;
        @SerializedName("CheckCarDisinfected")
        public Object checkCarDisinfected;
        @SerializedName("CheckPointMid")
        public Object checkPointMid;
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

        public static class StartingPlaceRegion implements Serializable{
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

        public static class DestinationPlaceRegion implements Serializable{
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

        public static class Status implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class CCOutInType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class PrintStatus implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class IsOnlineIssuing implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class ValidityDays implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class TransportType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class UsageType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class DestinationPlaceType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }


        public static class CheckResultBean implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }


        public static class StartingPlaceType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class AnimalType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class Unit implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class StartingPlaceCompany implements Serializable{
            @SerializedName("Name")
            public String name;
            @SerializedName("UUID")
            public String uUID;
        }

        public static class DestinationPlaceCompany implements Serializable{
            @SerializedName("Name")
            public String name;
            @SerializedName("UUID")
            public String uUID;
        }
    }
}
