package com.agridata.cdzhdj.model;

import java.io.Serializable;

/**
 * @auther 56454
 * @date 2022/6/9
 * @time 10:57.
 */
public class GetCertInfoByCodeData implements Serializable {

    public DataBean Data;

    public static class DataBean implements Serializable {
        public CertInfoBean CertInfo;
        public CheckInfoBean CheckInfo;
        public CheckSlaughterHouseBean CheckSlaughterHouse;


        public static class CertInfoBean implements Serializable {
            public String CertType;
            public String CertNo;
            public String CertQRCode;
            public String Owner;
            public String ShippersTel;
            public String TypeName;
            public int Amount;
            public String UnitName;
            public String StartPlace;
            public String DestinationPlace;
            public String Usage;
            public String Carrier;
            public String CarrierTel;
            public String TranType;
            public String VehicleNumber;
            public String Veterinary;
            public String DateOfIssue;
            public int DateOfIssueDIFF;
            public String EarLabel;
            public String Disinfection;
            public String DJSTel;
            public int CheckID;
            public String CertGUID;
            public int IsReceive;
            public int IsValid;
            public int IsUseEarTag;
            public String Remark;
            public Object SlaughterGUID;
            public Object SlaughterID;
            public Object SlaughterName;
        }


        public static class CheckInfoBean implements Serializable {
            public String CertType;
            public int ID;
            public String CertGUID;
            public String CertNo;
            public int CheckType;
            public String EarTags;
            public String Imgs;
            public int Counts;
            public String CarHead;
            public int CheckResult;
            public int TimeIsPass;
            public int AddressIsPass;
            public int CarIsPass;
            public int RoadIsPass;
            public int NumberIsPass;
            public int EarTagIsPass;
            public int BaseType;
            public String Remark;
            public int CreatedBy;
            public String CreatedOn;
            public int ModifiedBy;
            public String ModifiedOn;
            public String VehicleNumber;
            public String Owner;
            public String AnimalName;
            public String Amount;
            public String StartPlace;
        }


        public static class CheckSlaughterHouseBean implements Serializable {
            public int IsPass;
            public InfoBean Info;


            public static class InfoBean implements Serializable {

                public String XDRGUID;
                public String DisplayName;
            }
        }
    }
}
