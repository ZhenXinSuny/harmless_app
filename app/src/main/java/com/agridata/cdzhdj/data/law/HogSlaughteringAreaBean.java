package com.agridata.cdzhdj.data.law;

import retrofit2.http.PUT;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-12 16:12.
 * @Description :描述 生猪屠宰领域
 */
public class HogSlaughteringAreaBean {

    //成都市孵化场动物卫生监督执法检查记录表
    public int hatchJCFYSS1 =-1;//生产布局符合规定
    public int hatchJCFYSS2 =-1;//设施设备齐全
    public String hatchPlayEggs;//进场种蛋数
    public String hatchEggs;//孵化种蛋数
    public String hatchBroodHatched;//孵出雏苗数
    public String managementSystem;//管理制度 免疫制度  □检疫申报制度  □用药制度  □疫情报告制度  □消毒制度  □无害化处理制度  □其他（                    ）
    public int hatchJCDJ1 =-1;//有效的疫病检测报告
    public int hatchJCDJ2 =-1;//种蛋入场登记
    public int hatchJCDJ3 =-1;//雏苗销售登记
    public int hatchWSXD1 =-1;//设施设备运行正常
    public int hatchWSXD2 =-1;//种蛋、车辆、场地消毒
    public int hatchWHH1 =-1;//设备运行正常
    public int hatchWHH2 =-1;//废蛋、病、死雏及废弃物无害化处理
    public int hatchYQBG =-1;//疫情报告 □是□否□无疫
    public  String lastJanuaryOutNum;//最近1月销售雏苗数
    public  String quarantineReport;//申报检疫数
    public int hatchGXJL =-1;//各项记录是否齐全、规范


    /************  生猪two  成都市生猪屠宰厂（场）执法检查记录表***************/

    public int TZZD1 =-1;//检疫申报制度
    public int TZZD2 =-1;//消毒制度
    public int TZZD3 =-1;//无害化处理制度
    public int TZZD4 =-1;//疫情报告制度
    public int TZZD5 =-1;//品质检验制度
    public int TZZD6 =-1;//肉精及非洲猪瘟自检制度
    public int TZZD7 =-1;//□生猪进厂（场）查验登记制度
    public int TZZD8 =-1;//生猪产品出厂（场）记录制度
    public int TZZD9 =-1;//产品追溯制度
    public int TZZD10 =-1;//签订委托屠宰协议制度

    public int TZSSSBOne =-1;//屠宰设施设备能否正常运行。
    public int TZSSSBTwo =-1;//无害化处理专用冷藏间能否正常使用。

    public int TZJCOne =-1;//是否查验《动物检疫合格证明》。
    public int TZJCTwo =-1;//是否对进场生猪进行临床健康检查。
    public int TZJCThree =-1;//是否查验畜禽标识佩戴情况。

    public int TZDZOne =-1;//是否按要求分圈编号。
    public int TZDZTwo =-1;//是否及时对生猪体表进行清洁。
    public int TZDZThree =-1;//是否达到宰前停食静养的要求。
    public int TZDZFour =-1;//对临床健康检查状况异常生猪是否进行隔离观察或者按检验规程急宰。
    public int TZDZFive =-1;//是否按规定进行检疫申报。
    public int TZDZSix =-1;//是否如实记录待宰生猪数量、临床健康检查情况、隔离观察情况、停食静养情况，以及货主等信息。

    public int TZWHHOne =-1;//是否按照屠宰工艺流程进行屠宰操作。
    public int TZWHHTwo =-1;//是否按照检验规程进行肉品品质检验。

    public int TZCCSZOne =-1;//是否摘除肾上腺、甲状腺、病变淋巴结，是否对检验不合格的生猪产品进行修割。
    public int TZCCSZTwo =-1;//是否对待宰生猪或者在屠宰过程中进行“瘦肉精”等检验。
    public int TZCCSZThree =-1;//是否对检验合格的生猪产品出具《肉品品质检验合格证》，在胴体上加盖检验合格印章。


    public int TZRYTJOne =-1;//是否对屠宰车间、屠宰设备、器械及时清洗、消毒。
    public int TZRYTJTwo =-1;//是否如实完整记录肉品品质检验、“瘦肉精”“非洲猪瘟”等检验结果。

    public int TZXXBSOne =-1;//是否对待宰死亡生猪、检验检疫不合格生猪或者生猪产品进行无害化处理；是否有驻场官方兽医出具的判定书。
    public int TZXXBSTwo =-1;//是否如实记录无害化处理病害生猪或者生猪产品数量、处理时间、处理人员等；是否保留有关影像资料。
    public int TZXXBSThree =-1;//出场肉类是否附有《动物检疫合格证明》，《肉品品检合格证》。
    public int TZDAGL =-1;//胴体外表面是否加盖检验合格章、动物检疫验讫印章，经包装生猪产品是否附具检验合格标志、加施检疫标志。



    public int tuzai1 =-1;//是否如实记录出场生猪产品名称、规格、数量、动物检疫证明号、肉品品检合格证、屠宰日期、出厂（场）日期以及购货者名称、销售地址、联系方式等信息。
    public int tuzai2=-1;//肉品品质检验人员是否经考核合格。
    public int tuzai3=-1;//肉品品质检验人员和屠宰技术人员是否持有依法取得的健康证明。
    public int tuzai4=-1;//是否按要求报告动物疫情。
    public int tuzai5=-1;//是否按照国家《生猪等畜禽屠宰统计报表制度》的要求，及时报送屠宰相关信息。
    public int tuzai6=-1;//是否按要求报告安全生产信息。
    public int tuzai7=-1;//是否将生猪进厂（场）检验、待宰检验、品质检验、“瘦肉精”“非洲猪瘟”等检验记录、无害化处理、消毒、生猪产品出厂（场）、设施设备检验检测保养记录等归档。

    /*****************   生猪领域 成都市畜禽收购贩运户动物卫生监督检查记录表**************/
    public String recordNo;//运输工具(备案编号）
    public String carNum;//车牌号码
    public String traffickingAnimal;//动物种类 猪□牛□羊□禽□其他
    public String traffickedNum;//贩运数 头（只、羽）
    public int clinical=-1;//临床观察异常
    public int traffickQuarantineCertificate=-1;//贩运畜禽的检疫证明
    public String badgesNotWorn;//未佩戴标识数
    public int waterflooding=-1;//检查时，发现注水或添加其他物质行为
    public int carRequirement=-1;//运载工具、垫料符合动物防疫要求
    public int vehicleDisinfection =-1;//运载工具消毒
    public int wasteDisposalMethod =-1;//废弃物处理合法
    public int designatedChannel =-1;//经指定通道入川
    public int ledger =-1;//购销台账记录情况 □齐全□不全□无
    public int healthCertificate =-1;//健康合格证


    /*****************   生猪领域4  成都市动物诊疗机构动物卫生监督执法检查记录表**************/

    public String animalMedicalPermit;//《动物诊疗许可证》号码
    public String licenceIssued;//发证日期
    public int consistentSituation =-1;//当前情况与许可证内容一致
    public String medicalInstitutionsType;//诊疗机构类型  □医院□诊所
    public String employeesNum;//从业人员总数
    public String licensedVeterinarianNum;//执业兽医
    public String practicingAssistantVeterinarianNum;//执业助理兽医
    public String practiceProject;//执业项目 □动物疫病诊断□动物疫病治疗□动物免疫□动物护理和保健□动物小型手术□动物三腔（颅腔、胸腔、腹腔）手术□动物健康检查□宠物芯片埋植□其它
    public int diagnosisTreatmentManagement =-1;//管理制度
    public int employeesInformation =-1;//在诊疗场所显著位置悬挂动物诊疗许可证和从业人员基本情况
    public int prescribedMedication =-1;//按照规定用药
    public int useVeterinaryDrugs =-1;//使用假劣兽药
    public  int drugProhibition =-1;//禁用药
    public  int departmentalApproval =-1;//安装、使用具有放射性的诊疗设备依法经环境保护部门批准
    public String useRegistration;//使用登记  □疫苗□免疫证（标）□诊断液□药品（毒性药品、麻醉药品）□器械□其他
    public  int organizationName =-1;//病历、处方笺规范并印有动物诊疗机构名称
    public  int termPreservation =-1;//病历档案按规定期限保存
    public  int reportEpidemic =-1;//按规定报告疫情□是□否□未发现染疫病例
    public  int giveTreatment =-1;//发现动物患有或疑似患有国家规定应当扑杀的疫病时，未擅自进行治疗□是□否□未发现染疫病例
    public  int harmlessAnimal =-1;//按照规定无害化处理病死动物和病理组织
    public  int wasteWater =-1;//按规定处理医疗废弃物及废水
    public  int reportIssuingAuthority =-1;//按规定将上年度动物诊疗活动情况向发证机关报告
    public  int peopleHealthCertificate =-1;//从业兽医人员取得《健康合格证》
    public  int requiredRecord =-1;//执业兽医按规定备案
    public  int standardFilling =-1;//规范填写处方笺、病历
    public  int reportFilingAuthority =-1;//按规定将上年度动物诊疗活动情况向备案机关报告
    public  int recordsComplete =-1;//各项记录记载完整

    /*****************   生猪领域  成都市乡村动物诊疗市场动物卫生监督执法检查记录表 5 **************/

    public String countrysideAnimalMedicalPermit;//《动物诊疗许可证》号码
    public String countrysideDateIssue;//发证日期
    public String countrysideEmployeesTotalNum;//从业人员总数人
    public String countrysideLicensedVeterinarian;//执业兽医  人
    public String countrysidePracticingAssistantVeterinarian;//执业助理兽医
    public String countrysideRuralVeterinarian;//乡村兽医
    public String countrysideVeterinarianName;//执业或乡村兽医姓名
    public String countrysideEmploymentCertificateNumber;//从业证书编号

    public int countrysideOne=-1;//在本乡镇从事诊疗服务
    public int countrysideTwo=-1;//有固定的从业场所和必要的兽医器械
    public int countrysideThree=-1;//按照规定用药
    public int countrysideFour=-1;//如实记录用药情况
    public int countrysideFive=-1;//按规定处理使用过的兽医器械和医疗废弃物
    public int countrysideSix=-1;//按规定报告疫情，并采取隔离等控制措施，防止疫情扩散
    public int countrysideSeven=-1;//发现动物患有或疑似患有国家规定应当扑杀的疫病时，未擅自进行治疗
    public int countrysideEight=-1;//按照当地人民政府或者有关部门组织的要求参加动物疫病预防、控制和扑灭活动

    /*****************   生猪领域 畜禽屠宰企业安全生产监督检查记录表 6 **************/
    public int slaughter1=-1;//企业是否按规定设置安全生产管理机构或配备安全生产管理人员。
    public int slaughter2=-1;//.企业负责人是否履行安全生产管理职责。
    public int slaughter3=-1;//企业的其他责任人和安全生产管理人员是否履行安全生产管理职责。
    public int slaughter4=-1;//建立健全安全生产例会制度；安全教育培训制度；安全生产检查制度；设备、设施及人员安全管理制度；劳动防护用品管理制度；应急管理制度；事故报告制度；安全奖惩等制度。
    public int slaughter5=-1;//企业为具备的安全生产条件所必须的资金投入。
    public int slaughter6=-1;//6.为企业从业人员提供符合国家标准或者行业标准的劳动防护用品，并监督、教育从业人员按照使用规则佩戴、使用。
    public int slaughter7=-1;//.是否如实记录安全生产教育和培训情况。
    public int slaughter8=-1;//企业对从业人员进行安全生产教育和培训。未经安全生产教育和培训合格的从业人员，不得上岗作业。
    public int slaughter9=-1;//特种作业人员按照国家有关规定经专门的安全作业培训，取得相应资格。
    public int slaughter10=-1;//.企业建立健全生产安全事故隐患排查治理制度，采取技术，管理措施，及时发现并消除事故隐患。事故隐患排查治理情况应当如实记录，并向从业人员通报。
    public int slaughter11=-1;//.对不能及时排除的重大隐患，应当采取安全防范措施；危及人员安全的，应当暂时停止生产经营活动，防止事故发生。
    public int slaughter12=-1;//在有较大危险因素的生产经营场所和有关设施、设备上设置明显的安全警示标志。
    public int slaughter13=-1;//.是否根据国家有关规定对安全设施、设备进行经常性维护、保养的。
    public int slaughter14=-1;//.企业对重大危险源：登记建档、进行定期检查、评估、监控，并制定应急预案，告知从业人员和相关人员在紧急情况下应当采取的应急措施（液氨）。
    public int slaughter15=-1;//.生产、经营、储存使用危险物品的车间、仓库不得与员工宿舍在同一座建筑物内，并应当与员工宿舍保持安全距离。
    public int slaughter16=-1;//.生产经营场所和员工宿舍应当设有符合紧急疏散要求，标志明显、保持出口畅通，禁止封闭，堵塞生产经营场所或者员工宿舍的出口。
    public int slaughter17=-1;//17.建立有完善的安全生产预警机制及各类突发事件应急预案。



}
