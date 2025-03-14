package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-27 15:26.
 * @Description :描述
 */
public class EntryCheckBBean implements Serializable {

    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public String message;
    @SerializedName("Result")
    public Result result;

    public static class Result implements Serializable{

        @SerializedName("IsRemedy")
        public int isRemedy=0;
        @SerializedName("Mid")
        public String mid;
        @SerializedName("SourceId")
        public Object sourceId;
        @SerializedName("Name")
        public Object name;
        @SerializedName("GUID")
        public String gUID;
        @SerializedName("CCType")
        public int cCType;
        @SerializedName("QRCode")
        public String qRCode;
        @SerializedName("FactoryCode")
        public String factoryCode;
        @SerializedName("OwnerTel")
        public String ownerTel;
        @SerializedName("Amount")
        public int amount;
        @SerializedName("StartingPlaceName")
        public String startingPlaceName;
        @SerializedName("DestinationPlaceRegion")
        public DestinationPlaceRegion destinationPlaceRegion;
        @SerializedName("DestinationPlaceName")
        public String destinationPlaceName;
        @SerializedName("DestinationVillage")
        public String destinationVillage;
        @SerializedName("TransportNumber")
        public String transportNumber;
        @SerializedName("AgengcyName")
        public String agengcyName;
        @SerializedName("DateOfIssue")
        public String dateOfIssue;
        @SerializedName("Remark")
        public String remark;
        @SerializedName("ClientCreateTime")
        public String clientCreateTime;
        public String _PartId;
        @SerializedName("DeathNumber")
        public int deathNumber;
        @SerializedName("AnomalyNumber")
        public int anomalyNumber;
        @SerializedName("ActualNumber")
        public int actualNumber;
        @SerializedName("RemarkColumn")
        public String remarkColumn;
        @SerializedName("CarNumber")
        public String carNumber;
        @SerializedName("NormalNumber")
        public Object normalNumber;
        @SerializedName("IsOnline")
        public int isOnline;
        @SerializedName("AnimalType")
        public AnimalType animalType;
        @SerializedName("Unit")
        public Unit unit;
        @SerializedName("UsageType")
        public UsageType usageType;
        @SerializedName("StartingPlaceType")
        public StartingPlaceType startingPlaceType;
        @SerializedName("DestinationPlaceType")
        public DestinationPlaceType destinationPlaceType;
        @SerializedName("Status")
        public Status status;
        @SerializedName("PrintStatus")
        public PrintStatus printStatus;
        @SerializedName("PlaceOfOrigin")
        public Object placeOfOrigin;
        @SerializedName("Declare_Mid")
        public Object declare_Mid;
        @SerializedName("DestinationPlaceCompany")
        public DestinationPlaceCompany destinationPlaceCompany;
        @SerializedName("AgencyMid")
        public Object agencyMid;
        @SerializedName("AgencyJson")
        public Object agencyJson;
        @SerializedName("StartingPlaceRegion")
        public StartingPlaceRegion startingPlaceRegion;
        @SerializedName("StartingPlaceRegionID")
        public int startingPlaceRegionID;
        @SerializedName("StartingPlaceRegionRI1")
        public int startingPlaceRegionRI1;
        @SerializedName("StartingPlaceRegionRI2")
        public int startingPlaceRegionRI2;
        @SerializedName("StartingPlaceRegionRI3")
        public int startingPlaceRegionRI3;
        @SerializedName("StartingPlaceRegionRI4")
        public int startingPlaceRegionRI4;
        @SerializedName("StartingPlaceRegionRI5")
        public int startingPlaceRegionRI5;
        @SerializedName("WxSendStatus")
        public int wxSendStatus;
        @SerializedName("CertSource")
        public CertSource certSource;
        @SerializedName("Recycling_People")
        public RecyclingPeople recycling_People;
        @SerializedName("DataSource")
        public int dataSource;
        @SerializedName("Owner")
        public Owner owner;
        @SerializedName("StartingPlaceCompany")
        public StartingPlaceCompany startingPlaceCompany;
        @SerializedName("EarTags")
        public String earTags;
        @SerializedName("QuarantineOfficer")
        public QuarantineOfficer quarantineOfficer;
        @SerializedName("QUADECPoint")
        public QUADECPoint qUADECPoint;
        @SerializedName("QUADECPointRegion")
        public QUADECPointRegion qUADECPointRegion;
        @SerializedName("ReciverQuarantinePointId")
        public Object reciverQuarantinePointId;
        @SerializedName("Reception")
        public int reception;
        @SerializedName("DestinationPlaceAddress")
        public String destinationPlaceAddress;
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
        @SerializedName("Longitude")
        public Object longitude;
        @SerializedName("Latitude")
        public Object latitude;
        @SerializedName("Point")
        public Object point;
        @SerializedName("Cst")
        public Object cst;

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

        public static class AnimalType implements Serializable{
            public int id;
            @SerializedName("Name")
            public String name;
        }

        public static class Unit implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class UsageType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class StartingPlaceType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class DestinationPlaceType implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class Status implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class PrintStatus implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class DestinationPlaceCompany implements Serializable{
            public String mid;
            @SerializedName("Name")
            public String name;
        }

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

        public static class CertSource implements Serializable{
            public int key;
            @SerializedName("Name")
            public String name;
        }

        public static class RecyclingPeople implements Serializable{
            public String key;
            @SerializedName("Name")
            public String name;
        }

        public static class Owner implements Serializable{
            public String mid;
            @SerializedName("Name")
            public String name;
            public String type;
        }

        public static class StartingPlaceCompany implements Serializable{
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class QuarantineOfficer implements Serializable{
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class QUADECPoint implements Serializable{
            public String mid;
            public String name;
        }

        public static class QUADECPointRegion implements Serializable{
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
}
