package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-10 16:44.
 * @Description :描述 兽药
 */
public class VeterinaryDrugFieldBean {

    //成都市兽药生产企业执法检查记录表 201
    public String mBusinessLicenseNumber;//营业执照号码或社会信用统一代码
    public int  GMPZhengShu  =-1;//具备兽药GMP证书、《兽药生产许可证》和产品批准文号等资质
    public int youXiaoqi =-1;//在有效期内

    public int  yuanLiaoFuLiaoFuHe =-1;//购进原料、辅料符合国家标准或者生产兽药的质量要求
    public int  StorageAndSafekeeping =-1;//对贮存有特殊要求的，严格按要求贮存和保管
    public int  wenJianJiZai =-1;//建立了完整、准确、规范的管理、生产、质量、销售等制度和记录记载
    public  int caoZuoRenQianMing =-1;//批生产记录应及时填写，做到字迹清晰、内容真实、数据完整，并由操作人及复核人签名
    public int  piHaoGuiDang =-1;//批生产记录应按批号规范归档，保存至兽药有效期后一年
    public int  zhiLiangJianYan =-1;//兽药出厂前经过质量检验
    public int biaoQianshuoming =-1;//兽用处方药标签和说明书在右上角以宋体红色标注，背景应当为白色的“兽用处方药”字样
    public  int biaozhuerweima =-1;//生产的兽药是否按照要求标注二维码
    public int guifanyinzhi =-1;//按《兽药产品说明书范本》或农业部批准样稿规范印制
    public int piqianfa =-1;//兽用生物制品实行批签发
    public int zhaohuizhidu =-1;//不合格兽药实施召回制度，并在当地主管部门的监督下进行监督销毁
    public int anquanshengchananquanshengchan =-1;//安全生产制度、安全防护设施、警示标识、粉尘控制、安全培训
    public int GMPhouxujianguan =-1;//严格按照《兽药生产质量管理规范》规定从事兽药生产活动



    //成都市兽药经营企业执法检查记录表 202
    public int shouyaoxukezhengzizhi =-1;//具备《兽药经营许可证》等资质
    public int shifouyouxiaoqi =-1;//在有效期内
    public int jingyingchangsuoxiangshiying =-1;//经营场所的面积、设施和设备应当与经营的兽药品种、经营规模相适应
    public int shebeiqiquan =-1;//经营场所和仓库的设施、设备是否齐备、整洁和完好
    public int guizhangzhidu =-1;//建立健全兽药保管制度、出入库查验制度、员工培训制度等，并严格执行
    public int caigouruku =-1;//购进的兽药必须具备产品标签、说明书、产品质量合格证，并与兽药产品核对无误
    public  int chenliechuxuOne =-1;//按照《兽药经营质量管理规范》要求陈列和储存兽药
    public  int chenliechuxuTwo =-1;//应当保存采购兽药的有效凭证，建立真实、完整的采购记录，做到有效凭证、账、货相符
    public  int chenliechuxuThree =-1;//处方药和非处方药分区或分柜摆放，处方药不得采用开架自选方式销售
    public  int chenliechuxuFour =-1;//在经营场所显著位置悬挂或张贴“兽用处方药必须凭兽医处方购买”的提示语
    public int shouyaoxiaoshouOne =-1;//建立购销记录和单独的处方药购销记录，并真实完整规范地保存购销记
    public int shouyaoxiaoshouTwo =-1;//经营违禁药品、人用药品、原料药和假劣兽药
    public int shouyaoshengwuzhipinOne =-1;//取得《兽用生物制品经营许可证》，并在核定范围内经营
    public int shouyaoshengwuzhipinTwo =-1;//与委托生产企业签定销售代理合同
    public int shouyaoshengwuzhipinThree =-1;//经营未经委托的企业生产的兽用生物制品
    public int GMPhouxujianguanTwo =-1;//严格按照《兽药经营质量管理规范》规定从事兽药经营活动


    @Override
    public String toString() {
        return "VeterinaryDrugFieldBean{" +
                "mBusinessLicenseNumber='" + mBusinessLicenseNumber + '\'' +
                ", GMPZhengShu=" + GMPZhengShu +
                ", youXiaoqi=" + youXiaoqi +
                ", yuanLiaoFuLiaoFuHe=" + yuanLiaoFuLiaoFuHe +
                ", StorageAndSafekeeping=" + StorageAndSafekeeping +
                ", wenJianJiZai=" + wenJianJiZai +
                ", caoZuoRenQianMing=" + caoZuoRenQianMing +
                ", piHaoGuiDang=" + piHaoGuiDang +
                ", zhiLiangJianYan=" + zhiLiangJianYan +
                ", biaoQianshuoming=" + biaoQianshuoming +
                ", biaozhuerweima=" + biaozhuerweima +
                ", guifanyinzhi=" + guifanyinzhi +
                ", piqianfa=" + piqianfa +
                ", zhaohuizhidu=" + zhaohuizhidu +
                ", anquanshengchananquanshengchan=" + anquanshengchananquanshengchan +
                ", GMPhouxujianguan=" + GMPhouxujianguan +
                ", shouyaoxukezhengzizhi=" + shouyaoxukezhengzizhi +
                ", shifouyouxiaoqi=" + shifouyouxiaoqi +
                ", jingyingchangsuoxiangshiying=" + jingyingchangsuoxiangshiying +
                ", shebeiqiquan=" + shebeiqiquan +
                ", guizhangzhidu=" + guizhangzhidu +
                ", caigouruku=" + caigouruku +
                ", chenliechuxuOne=" + chenliechuxuOne +
                ", chenliechuxuTwo=" + chenliechuxuTwo +
                ", chenliechuxuThree=" + chenliechuxuThree +
                ", chenliechuxuFour=" + chenliechuxuFour +
                ", shouyaoxiaoshouOne=" + shouyaoxiaoshouOne +
                ", shouyaoxiaoshouTwo=" + shouyaoxiaoshouTwo +
                ", shouyaoshengwuzhipinOne=" + shouyaoshengwuzhipinOne +
                ", shouyaoshengwuzhipinTwo=" + shouyaoshengwuzhipinTwo +
                ", shouyaoshengwuzhipinThree=" + shouyaoshengwuzhipinThree +
                ", GMPhouxujianguanTwo=" + GMPhouxujianguanTwo +
                '}';
    }
}
