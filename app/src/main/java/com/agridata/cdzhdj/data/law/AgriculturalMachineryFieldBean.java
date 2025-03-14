package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-11 10:52.
 * @Description :描述
 */
public class AgriculturalMachineryFieldBean {
    //成都市农机专业合作社执法检查记录表
    public  int zhizi=-1;//具备营业执照、组织机构代码证等资质，且在有效期内
    public  int dengjizhucengOne=-1;//拖拉机、联合收割机按规定进行登记注册
    public  int dengjizhucengTwo=-1;//拖拉机、联合收割机操作人员按规定取得了操作证件
    public  int dengjizhucengThree=-1;//跨区作业手续齐全
    public  int danganziliao=-1;//建立了完整、准确、规范的使用、培训等制度
    public  int danganziliaoTwo=-1;//建立了完整、准确的使用、培训等记录
    public  int niandujianyan=-1;//拖拉机、联合收割机按规定完成年度安全检验

    /**************  two  成都市农机执法检查记录表（个人） ***********/

    public int idcard =-1;//个人身份证明齐全且在有效期内
    public int dengjiPersonOne =-1;//拖拉机、联合收割机按规定进行登记注册
    public int dengjiPersonTwo =-1;//拖拉机、联合收割机操作人员按规定取得驾驶证
    public int dengjiPersonThree =-1;//跨区作业手续齐全
    public int caozuoPersonOne =-1;//操作人员操作与本人操作证件规定不相符的拖拉机、联合收割机
    public int caozuoPersonTwo =-1;//操作人员操作未按照规定登记的拖拉机、联合收割机
    public int caozuoPersonThree =-1;//操作人员操作未按照规定检验的拖拉机、联合收割机
    public int caozuoPersonFour =-1;//拖拉机、联合收割机粘帖反光标识
    public int niandujianyanPerson =-1;//农业机械按规定完成年度安全检验


    /**************  three 成都市农机维修点执法检查记录表***********/

    public int weixiudianzhizi =-1;//具备营业执照、组织机构代码证等资质，且在有效期内
    public int weixiuqingkuangOne =-1;//建立了完整、准确、规范的维修等制度
    public int weixiuqingkuangTwo =-1;//私自拼装、改装农业机械
    public int weixiuqingkuangThree =-1;//承揽已报废农业机械维修业务

    /**************  four 成都市农机驾驶培训机构执法检查记录表 ***********/

    public int jianshizhizhi =-1;//具备营业执照、组织机构代码证等资质，且在有效期内
    public int jianshiPeiXunYeWuOne =-1;//严格执行农业农村部颁发的教学大纲，按照大纲内容开展教学培训，保证培训质量
    public int jianshiPeiXunYeWuTwo =-1;//聘用经省级人民政府农机主管部门考核合格的教学人员
    public int jianshiPeiXunYeWuThree =-1;//教练车应当按拖拉机登记有关规定取得教练车牌证

    @Override
    public String toString() {
        return "AgriculturalMachineryFieldBean{" +
                "zhizi=" + zhizi +
                ", dengjizhucengOne=" + dengjizhucengOne +
                ", dengjizhucengTwo=" + dengjizhucengTwo +
                ", dengjizhucengThree=" + dengjizhucengThree +
                ", danganziliao=" + danganziliao +
                ", danganziliaoTwo=" + danganziliaoTwo +
                ", niandujianyan=" + niandujianyan +
                ", idcard=" + idcard +
                ", dengjiPersonOne=" + dengjiPersonOne +
                ", dengjiPersonTwo=" + dengjiPersonTwo +
                ", dengjiPersonThree=" + dengjiPersonThree +
                ", caozuoPersonOne=" + caozuoPersonOne +
                ", caozuoPersonTwo=" + caozuoPersonTwo +
                ", caozuoPersonThree=" + caozuoPersonThree +
                ", caozuoPersonFour=" + caozuoPersonFour +
                ", niandujianyanPerson=" + niandujianyanPerson +
                ", weixiudianzhizi=" + weixiudianzhizi +
                ", weixiuqingkuangOne=" + weixiuqingkuangOne +
                ", weixiuqingkuangTwo=" + weixiuqingkuangTwo +
                ", weixiuqingkuangThree=" + weixiuqingkuangThree +
                ", jianshizhizhi=" + jianshizhizhi +
                ", jianshiPeiXunYeWuOne=" + jianshiPeiXunYeWuOne +
                ", jianshiPeiXunYeWuTwo=" + jianshiPeiXunYeWuTwo +
                ", jianshiPeiXunYeWuThree=" + jianshiPeiXunYeWuThree +
                '}';
    }
}
