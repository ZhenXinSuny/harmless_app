package com.agridata.cdzhdj.data.law;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 17:54.
 * @Description :描述 执法填报
 */
public class EnforcementData {
    public String CategoryId="78c21f0133c6471bb3d6fa5421020137";
    public String CategoryName="8A0983F66D9360FB-T_YZT_Enforcement";
    public String _PartId="d5896b31964e425382df52f655dedfc2";

    public  SponsorEnforcementUnitBean  SponsorEnforcementUnit;//主办执法单位
    public  OrganizerBean  Organizer;//主办人员
    public  CoOrganizeEnforcementUnitsBean  CoOrganizeEnforcementUnits;//协办执法单位
    public  CoOrganizerBean  CoOrganizer;//协办人员
    public  InspectionFieldBean  InspectionField;//检查领域
    public   CheckListBean  CheckList;//检查表
    public String FrontViewOfTheInspectedUnit;//被检查单位正面照片
    public String CompanyName;//单位名称（个人姓名）
    public String LegalRepresentative;//法定代表人
    public String Tel;//联系电话
    public  RegionBean Region;//區劃
    public String  DetailedAddress;//详细地址
    public String EyewitnessSignature;//	见证人签名
    public String  UnitUnderInspectionSignature;//被检查单位负责人签名
    public String SignatureOfOrganizer;//主办人员签名
    public InspectionResultBean InspectionResult;//检查结果
    public String OtherProblems;//发现其他问题
    public  ProblemShootingBean  ProblemShooting;//问题照片
    public String DeadlineForRectification;//限期整改截止日期
    public int CheckType;//检查类型
    public SeedFieldBean SeedField; //种子领域
    public VeterinaryDrugFieldBean VeterinaryDrugField; //兽药领域
    public PesticideFieldBean PesticideField; //农药领域
    public AgriculturalMachineryFieldBean AgriculturalMachineryField;//农机
    public FertilizerFieldBean FertilizerField;//肥料
    public AgriculturalProductSafetyFieldBean  AgriculturalProductSafetyField;//农产品质量安全领域

    public FeedFieldBean  FeedField;//饲料领域
    public HogSlaughteringAreaBean  HogSlaughteringArea;//生猪屠宰领域
    public FieldPlantQuarantineBean  FieldPlantQuarantine;//植物领域
    public LivestockPoultryFieldsBean LivestockPoultryFields;//种畜禽领域
    public FisheryAreaBean FisheryArea;//渔业水产领域
    public  ReportingAgencyBean ReportingAgency;//上报机构
    public String CommitTime;


    public int  ReviewStatus;//复查状态
    public int   AssignmentStatus;//指派状态


    public SamplePhotosBean  SamplePhotos;//	抽查样品照片
    public String ContentOfRandomCheck;//抽查内容(抽查)
    public String SignatureEnforcementOfficer;//执法人员签名


    @Override
    public String toString() {
        return "EnforcementData{" +
                "SponsorEnforcementUnit=" + SponsorEnforcementUnit +
                ", Organizer=" + Organizer +
                ", CoOrganizeEnforcementUnits=" + CoOrganizeEnforcementUnits +
                ", CoOrganizer=" + CoOrganizer +
                ", InspectionField=" + InspectionField +
                ", CheckList=" + CheckList +
                ", FrontViewOfTheInspectedUnit='" + FrontViewOfTheInspectedUnit + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", LegalRepresentative='" + LegalRepresentative + '\'' +
                ", Tel='" + Tel + '\'' +
                ", Region=" + Region +
                ", DetailedAddress='" + DetailedAddress + '\'' +
                ", EyewitnessSignature='" + EyewitnessSignature + '\'' +
                ", UnitUnderInspectionSignature='" + UnitUnderInspectionSignature + '\'' +
                ", SignatureOfOrganizer='" + SignatureOfOrganizer + '\'' +
                ", InspectionResult=" + InspectionResult +
                ", OtherProblems='" + OtherProblems + '\'' +
                ", ProblemShooting=" + ProblemShooting +
                ", DeadlineForRectification='" + DeadlineForRectification + '\'' +
                ", CheckType=" + CheckType +
                ", SeedField=" + SeedField +
                '}';
    }





    public static class SponsorEnforcementUnitBean {
        public String Mid;
        public String Name;

        @Override
        public String toString() {
            return "SponsorEnforcementUnitBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public   static class OrganizerBean {
        public String UserID;
        public String Name;

        @Override
        public String toString() {
            return "OrganizerBean{" +
                    "UserID='" + UserID + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public  static class CoOrganizeEnforcementUnitsBean {
        public String Mid;
        public String Name;

        @Override
        public String toString() {
            return "CoOrganizeEnforcementUnitsBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }
    public   static class CoOrganizerBean {
        public String Mid;
        public String Name;

        @Override
        public String toString() {
            return "CoOrganizerBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public static class InspectionFieldBean {
        public String Id;
        public String Name;

        @Override
        public String toString() {
            return "InspectionFieldBean{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }
    public static class CheckListBean {
        public String Id;
        public String Name;

        @Override
        public String toString() {
            return "CheckListBean{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public  static class RegionBean implements Serializable {
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

    public static class InspectionResultBean {
        public String Id;
        public String Name;

        @Override
        public String toString() {
            return "InspectionResultBean{" +
                    "Id='" + Id + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }

    public  static class  ProblemShootingBean{
        public List<String> imageList;

        @Override
        public String toString() {
            return "ProblemShootingBean{" +
                    "imageList=" + imageList +
                    '}';
        }
    }

    public static class ReportingAgencyBean {
        public String Mid;
        public String Name;

        @Override
        public String toString() {
            return "ReportingAgencyBean{" +
                    "Mid='" + Mid + '\'' +
                    ", Name='" + Name + '\'' +
                    '}';
        }
    }
    public  static class  SamplePhotosBean{
        public List<String> imageList;

        @Override
        public String toString() {
            return "ProblemShootingBean{" +
                    "imageList=" + imageList +
                    '}';
        }
    }
}
