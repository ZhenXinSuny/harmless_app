package com.agridata.cdzhdj.data.law;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-14 15:06.
 * @Description :描述
 */
public class EnforcementHomeData {


    @SerializedName("Status")
    public int status;
    @SerializedName("ErrorCode")
    public int errorCode;
    @SerializedName("Message")
    public Object message;
    @SerializedName("Result")
    public List<Result> result;

    public static class Result {
        @SerializedName("Mid")
        public String mid;
        @SerializedName("SourceId")
        public Object sourceId;
        @SerializedName("Name")
        public Object name;
        @SerializedName("SponsorEnforcementUnit")
        public SponsorEnforcementUnit sponsorEnforcementUnit;
        @SerializedName("Organizer")
        public Organizer organizer;
        @SerializedName("CoOrganizeEnforcementUnits")
        public CoOrganizeEnforcementUnits coOrganizeEnforcementUnits;
        @SerializedName("CoOrganizer")
        public CoOrganizer coOrganizer;
        @SerializedName("InspectionField")
        public InspectionField inspectionField;
        @SerializedName("FrontViewOfTheInspectedUnit")
        public String frontViewOfTheInspectedUnit;
        @SerializedName("CompanyName")
        public String companyName;
        @SerializedName("LegalRepresentative")
        public String legalRepresentative;
        @SerializedName("Tel")
        public String tel;
        @SerializedName("Region")
        public Region region;
        @SerializedName("DetailedAddress")
        public String detailedAddress;
        @SerializedName("EyewitnessSignature")
        public String eyewitnessSignature;
        @SerializedName("UnitUnderInspectionSignature")
        public String unitUnderInspectionSignature;
        @SerializedName("SignatureOfOrganizer")
        public String signatureOfOrganizer;
        @SerializedName("InspectionResult")
        public InspectionResult inspectionResult;
        @SerializedName("OtherProblems")
        public String otherProblems;
        @SerializedName("Review")
        public Object review;
        @SerializedName("DeadlineForRectification")
        public Object deadlineForRectification;
        @SerializedName("ReviewOfLawEnforcementAgencies")
        public Object reviewOfLawEnforcementAgencies;
        @SerializedName("ReviewOfLawEnforcementOfficers")
        public Object reviewOfLawEnforcementOfficers;
        @SerializedName("ReviewTheSituation")
        public Object reviewTheSituation;
        @SerializedName("ReviewTime")
        public Object reviewTime;
        @SerializedName("SignatureOfRechecker")
        public Object signatureOfRechecker;
        @SerializedName("SignatureOfThePersonReviewed")
        public Object signatureOfThePersonReviewed;
        @SerializedName("Remark")
        public Object remark;
        @SerializedName("ContentOfRandomCheck")
        public Object contentOfRandomCheck;
        @SerializedName("SamplePhotos")
        public SamplePhotos samplePhotos;
        @SerializedName("SignatureEnforcementOfficer")
        public Object signatureEnforcementOfficer;
        @SerializedName("SignedPersonChargeInspection")
        public Object signedPersonChargeInspection;
        @SerializedName("CheckType")
        public int checkType;
        @SerializedName("TestResult")
        public Object testResult;
        public String _PartId;
        @SerializedName("SeedField")
        public SeedField seedField;
        @SerializedName("VeterinaryDrugField")
        public VeterinaryDrugField veterinaryDrugField;
        @SerializedName("PesticideField")
        public PesticideField pesticideField;
        @SerializedName("AgriculturalMachineryField")
        public AgriculturalMachineryField agriculturalMachineryField;
        @SerializedName("FertilizerField")
        public FertilizerField fertilizerField;
        @SerializedName("AgriculturalProductSafetyField")
        public AgriculturalProductSafetyField agriculturalProductSafetyField;
        @SerializedName("FeedField")
        public FeedField feedField;
        @SerializedName("HogSlaughteringArea")
        public HogSlaughteringArea hogSlaughteringArea;
        @SerializedName("FieldPlantQuarantine")
        public FieldPlantQuarantine fieldPlantQuarantine;
        @SerializedName("LivestockPoultryFields")
        public LivestockPoultryFields livestockPoultryFields;
        @SerializedName("FisheryArea")
        public FisheryArea fisheryArea;
        @SerializedName("CheckList")
        public CheckList checkList;
        @SerializedName("ProblemShooting")
        public ProblemShooting problemShooting;
        @SerializedName("ReportingAgency")
        public ReportingAgencyBean reportingAgency;
        @SerializedName("CommitTime")
        public String commitTime;
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

        @SerializedName("ReviewStatus")
        public int  reviewStatus;
        @SerializedName("AssignmentStatus")
        public int  assignmentStatus;

        @SerializedName("AssigningAgency")
        public AssigningAgency assigningAgency;
        @SerializedName("AssignPerson")
        public AssignPerson assignPerson;
        @SerializedName("SpotCheckStatus")
        public int spotCheckStatus;

        static class AssigningAgency {
            public String Mid;
            public String Name;
        }

        static class AssignPerson {
            public String Mid;
            public String Name;

        }

        public static class SponsorEnforcementUnit {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class Organizer {
            @SerializedName("Name")
            public String name;
            @SerializedName("UserID")
            public String userID;
        }

        public static class CoOrganizeEnforcementUnits {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class CoOrganizer {
            @SerializedName("Mid")
            public String mid;
            @SerializedName("Name")
            public String name;
        }

        public static class InspectionField {
            @SerializedName("Id")
            public String id;
            @SerializedName("Name")
            public String name;
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
            @SerializedName("RegionFullName")
            public String regionFullName;
            @SerializedName("RegionParentID")
            public int regionParentID;
        }

        public static class InspectionResult {
            @SerializedName("Id")
            public String id;
            @SerializedName("Name")
            public String name;
        }

        public static class SeedField {
            public int beian;
            public int dengJi;
            public int biaoqian;
            public int songjian;
            public int baozhaung;
            public int fakePlant;
            public String creditCode;
            public String businessCode;
            public int infringePlant;
            public int jingyingdangan;
            public int biaoqianneirong;
            public int shengchandangan;
        }

        public static class VeterinaryDrugField {
            public int piqianfa;
            public int youXiaoqi;
            public int caigouruku;
            @SerializedName("GMPZhengShu")
            public int gMPZhengShu;
            public int guifanyinzhi;
            public int piHaoGuiDang;
            public int shebeiqiquan;
            public int wenJianJiZai;
            public int zhaohuizhidu;
            public int guizhangzhidu;
            public int biaozhuerweima;
            public int chenliechuxuOne;
            public int chenliechuxuTwo;
            public int shifouyouxiaoqi;
            public int zhiLiangJianYan;
            @SerializedName("GMPhouxujianguan")
            public int gMPhouxujianguan;
            public int biaoQianshuoming;
            public int chenliechuxuFour;
            public int caoZuoRenQianMing;
            public int chenliechuxuThree;
            public int shouyaoxiaoshouOne;
            public int shouyaoxiaoshouTwo;
            public int yuanLiaoFuLiaoFuHe;
            @SerializedName("GMPhouxujianguanTwo")
            public int gMPhouxujianguanTwo;
            @SerializedName("StorageAndSafekeeping")
            public int storageAndSafekeeping;
            public int shouyaoxukezhengzizhi;
            public int shouyaoshengwuzhipinOne;
            public int shouyaoshengwuzhipinTwo;
            public int shouyaoshengwuzhipinThree;
            public int jingyingchangsuoxiangshiying;
            public int anquanshengchananquanshengchan;
        }

        public static class PesticideField {
            public int taiZhang;
            public int jinXianNongYaoOne;
            public int jinXianNongYaoTwo;
            public int wangShangXiaoShou;
            public int chanPinBiaoQianOne;
            public int chanPinBiaoQianTwo;
            public int chanPinZhiLiangOne;
            public int chanPinZhiLiangTwo;
            public int chanPinBiaoQianThree;
            public int productionAndManagementOne;
            public int productionAndManagementTwo;
            public int productionAndManagementFour;
            public int productionAndManagementThree;
        }

        public static class AgriculturalMachineryField {
            public int zhizi;
            public int idcard;
            public int danganziliao;
            public int jianshizhizhi;
            public int niandujianyan;
            public int caozuoPersonOne;
            public int caozuoPersonTwo;
            public int danganziliaoTwo;
            public int dengjiPersonOne;
            public int dengjiPersonTwo;
            public int weixiudianzhizi;
            public int caozuoPersonFour;
            public int dengjizhucengOne;
            public int dengjizhucengTwo;
            public int caozuoPersonThree;
            public int dengjiPersonThree;
            public int dengjizhucengThree;
            public int weixiuqingkuangOne;
            public int weixiuqingkuangTwo;
            public int niandujianyanPerson;
            public int jianshiPeiXunYeWuOne;
            public int jianshiPeiXunYeWuTwo;
            public int weixiuqingkuangThree;
            public int jianshiPeiXunYeWuThree;
        }

        public static class FertilizerField {
            public int zhiliang;
            public int feiliaodengjiOne;
            public int feiliaodengjiTwo;
            public int chanpinbiaoqianOne;
            public int chanpinbiaoqianTwo;
            public int feiliaodengjiThree;
            public int chanpinbiaoqianFour;
            public int chanpinbiaoqianThree;
        }

        public static class AgriculturalProductSafetyField {
            public int dlbz;
            public int lsyj;
            public int bzfqw;
            public int anquan;
            public int trpFlOne;
            public int trpFlTwo;
            public int trpNyOne;
            public int trpNyTwo;
            public int zhiliang;
            public int trpNyFive;
            public int trpNyFour;
            public int trpNyThree;
            public int shengchanjiluOne;
            public int shengchanjiluTwo;
        }

        public static class FeedField {
            public int tjjscly;
            public int tjjaqsc1;
            public int tjjaqsc2;
            public int tjjaqsc3;
            public int tjjaqsc4;
            public int tjjaqsc5;
            public int tjjgfqk1;
            public int tjjgfqk2;
            public int tjjgfqk3;
            public int tjjgfqk4;
            public int tjjgfqk5;
            public int tjjsccp1;
            public int tjjsccp2;
            public int tjjsccp3;
            public int tjjsccp4;
            public int tjjsccp5;
            public int tjjsctj1;
            public int tjjsctj2;
            public int tjjsctj3;
            public int tjjsctj4;
            public int tjjsctj5;
            public int tjjylsy1;
            public int tjjylsy2;
            public int tjjzhizhi1;
            public int tjjzhizhi2;
            public int gouxiangjiluOne;
            public int gouxiangjiluTwo;
            public int gouxiangjiluThree;
            public int jingyingchanpinOne;
            public int jingyingchanpinSix;
            public int jingyingchanpinTwo;
            public int jingyingchanpinFive;
            public int jingyingchanpinFour;
            public int jingyingchanpinNine;
            public int jingyingtiaojianOne;
            public int jingyingtiaojianTwo;
            public int jingyingchanpinEight;
            public int jingyingchanpinSeven;
            public int jingyingchanpinThree;
            public int jingyingtiaojianThree;
        }

        public static class HogSlaughteringArea {
            @SerializedName("TZZD1")
            public int tZZD1;
            @SerializedName("TZZD2")
            public int tZZD2;
            @SerializedName("TZZD3")
            public int tZZD3;
            @SerializedName("TZZD4")
            public int tZZD4;
            @SerializedName("TZZD5")
            public int tZZD5;
            @SerializedName("TZZD6")
            public int tZZD6;
            @SerializedName("TZZD7")
            public int tZZD7;
            @SerializedName("TZZD8")
            public int tZZD8;
            @SerializedName("TZZD9")
            public int tZZD9;
            @SerializedName("TZDAGL")
            public int tZDAGL;
            @SerializedName("TZZD10")
            public int tZZD10;
            public int ledger;
            @SerializedName("TZDZOne")
            public int tZDZOne;
            @SerializedName("TZDZSix")
            public int tZDZSix;
            @SerializedName("TZDZTwo")
            public int tZDZTwo;
            @SerializedName("TZJCOne")
            public int tZJCOne;
            @SerializedName("TZJCTwo")
            public int tZJCTwo;
            @SerializedName("TZDZFive")
            public int tZDZFive;
            @SerializedName("TZDZFour")
            public int tZDZFour;
            @SerializedName("TZWHHOne")
            public int tZWHHOne;
            @SerializedName("TZWHHTwo")
            public int tZWHHTwo;
            public int clinical;
            @SerializedName("TZCCSZOne")
            public int tZCCSZOne;
            @SerializedName("TZCCSZTwo")
            public int tZCCSZTwo;
            @SerializedName("TZDZThree")
            public int tZDZThree;
            @SerializedName("TZJCThree")
            public int tZJCThree;
            @SerializedName("TZRYTJOne")
            public int tZRYTJOne;
            @SerializedName("TZRYTJTwo")
            public int tZRYTJTwo;
            @SerializedName("TZSSSBOne")
            public int tZSSSBOne;
            @SerializedName("TZSSSBTwo")
            public int tZSSSBTwo;
            @SerializedName("TZXXBSOne")
            public int tZXXBSOne;
            @SerializedName("TZXXBSTwo")
            public int tZXXBSTwo;
            public int hatchGXJL;
            public int hatchWHH1;
            public int hatchWHH2;
            public int hatchYQBG;
            public int hatchJCDJ1;
            public int hatchJCDJ2;
            public int hatchJCDJ3;
            public int hatchWSXD1;
            public int hatchWSXD2;
            public int slaughter1;
            public int slaughter2;
            public int slaughter3;
            public int slaughter4;
            public int slaughter5;
            public int slaughter6;
            public int slaughter7;
            public int slaughter8;
            public int slaughter9;
            public int wasteWater;
            @SerializedName("TZCCSZThree")
            public int tZCCSZThree;
            @SerializedName("TZXXBSThree")
            public int tZXXBSThree;
            public int slaughter10;
            public int slaughter11;
            public int slaughter12;
            public int slaughter13;
            public int slaughter14;
            public int slaughter15;
            public int slaughter16;
            public int slaughter17;
            public int hatchJCFYSS1;
            public int hatchJCFYSS2;
            public int giveTreatment;
            public int waterflooding;
            public int carRequirement;
            public int countrysideOne;
            public int countrysideSix;
            public int countrysideTwo;
            public int harmlessAnimal;
            public int reportEpidemic;
            public int requiredRecord;
            public int countrysideFive;
            public int countrysideFour;
            public int drugProhibition;
            public int recordsComplete;
            public int standardFilling;
            public int countrysideEight;
            public int countrysideSeven;
            public int countrysideThree;
            public int organizationName;
            public int termPreservation;
            public int designatedChannel;
            public int healthCertificate;
            public int useVeterinaryDrugs;
            public int consistentSituation;
            public int vehicleDisinfection;
            public int wasteDisposalMethod;
            public int departmentalApproval;
            public int employeesInformation;
            public int prescribedMedication;
            public int reportFilingAuthority;
            public int reportIssuingAuthority;
            public int peopleHealthCertificate;
            public int diagnosisTreatmentManagement;
            public int traffickQuarantineCertificate;

            public int tuzai1;
            public int tuzai2;
            public int tuzai3;
            public int tuzai4;
            public int tuzai5;
            public int tuzai6;
            public int tuzai7;
        }

        public static class FieldPlantQuarantine {
            public int plant1;
            public int plant2;
            public int plant3;
            public int plant4;
            public int plant5;
            public int plant6;
            public int plant7;
            public int plant8;
            public int plant9;
            public int plant10;
            public int plant11;
            public int plant12;
            public int plant13;
        }

        public static class LivestockPoultryFields {
            public int zxq1;
            public int zxq2;
            public int zxq3;
            public int zxq4;
            public int zxq5;
            public int zxq6;
            public int zxq7;
            public int zxq8;
            public int zxq9;
            public int zxq10;
            public int zxq11;
            public int zxq12;
            public int zxqTwo1;
            public int zxqTwo2;
            public int zxqTwo3;
            public int zxqTwo4;
            public int zxqTwo5;
            public int zxqTwo6;
            public int zxqTwo7;
            public int zxqTwo8;
            public int zxqTwo9;
        }

        public static class FisheryArea {
            public int fishOne1;
            public int fishOne2;
            public int fishOne3;
            public int fishOne4;
            public int fishOne5;
            public int fishOne6;
            public int fishOne7;
            public int fishOne8;
            public int fishOne9;
            public int fishTwo1;
            public int fishTwo2;
            public int fishTwo3;
            public int fishTwo4;
            public int fishTwo5;
            public int fishTwo6;
            public int fishTwo7;
            public int fishTwo8;
            public int fishTwo9;
            public int fishOne10;
            public int fishOne11;
            public int fishOne12;
            public int fishOne13;
            public int fishOne14;
            public int fishOne15;
            public int fishThree1;
            public int fishThree2;
            public int fishThree3;
            public int fishThree4;
            public int fishThree5;
            public int fishThree6;
            public int fishThree7;
            public int fishThree8;
            public int fishThree9;
            public int fishThree10;
            public int fishThree11;
            public int fishThree12;
            public int fishThree13;
            public int fishThree14;
            public int fishThree15;
            public int fishThree16;
        }

        public static class CheckList {
            @SerializedName("Id")
            public String id;
            @SerializedName("Name")
            public String name;
        }

        public static class ProblemShooting {
            public List<String> imageList;
        }
        public static class SamplePhotos {
            public  List<String> imageList;
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
    }
}
