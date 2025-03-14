package com.agridata.cdzhdj.utils;


import java.util.UUID;

/**
 * @auther 56454
 * @date 2022/6/30
 * @time 13:52.
 */
public class UrlUtils {

   private static String ApiHtml = "https://www.cdzhdj.cn/pages/harmless/";
    public static String pic_url = "https://www.cdzhdj.cn/vbfs/storage/api/file/download?type=file&mid=";
    private static  String ApiHtmlQuarantineApp = "https://www.cdzhdj.cn/pages/quarantineApp/";
    private static  String AoiEarTag = "https://www.cdzhdj.cn/pages/plugins/";
    private static  String  CDApiUrl = "https://www.cdzhdj.cn";
    private static  String FARM_MAP ="https://www.cdzhdj.cn/pages/farm-map/Vaccine/";
    private static  String FARMMAP ="https://www.cdzhdj.cn/pages/farmmap/";

  // private static String ApiHtml = "http://cdxc.8918.biz/pages/harmless/";
// public static String pic_url = "http://cdxc.8918.biz/vbfs/storage/api/file/download?type=file&mid=";
// private static  String ApiHtmlQuarantineApp = "http://cdxc.8918.biz/pages/quarantineApp/";
// private static  String AoiEarTag = "http://cdxc.8918.biz/pages/plugins/";
// private static  String  CDApiUrl = "http://cdxc.8918.biz/";
// private static  String FARM_MAP ="http://cdxc.8918.biz/pages/farm-map/Vaccine/";
// private static  String FARMMAP ="http://cdxc.8918.biz/pages/farmmap/";
//





    //现场审核
    public static String XCSH_URL = ApiHtml + "xianchang/jianduList.html?v=" + UUID.randomUUID().toString();

    //驻场审核
    public static String ZCSH_URL = ApiHtml + "zhuchang/zhuChangIndex.html?v=" + UUID.randomUUID().toString();

    //相对人代理
    public static String XDR_URL = ApiHtml + "farm/indexMe_new.html?v=" + UUID.randomUUID().toString();
    //相对人自己
    public static String BIND_XDR_URL = ApiHtml + "farm/index.html?v=" + UUID.randomUUID().toString();

    //收运员  //farm/searchXdrApp.html?self=xz&v=
    public static String SHOUYUN_URL = ApiHtml + "shouyun/shouyunList_new.html?v=" + UUID.randomUUID().toString();


    public static String SHOUYUN_URL_NEW = ApiHtml + "farm/searchXdrApp.html?self=xz&v=" + UUID.randomUUID().toString();

    //管理员
    public static String ADMIN_URL = ApiHtml + "SystemAdmin/index.html?v=" + UUID.randomUUID().toString();

    //处理人
    public static String CHULI_URL = ApiHtml + "chuliyuan/chuliyuanindex.html?v=" + UUID.randomUUID().toString();

    //残余物处理
    public static String CANYUWU_URL = ApiHtml + "canyuwu/index.html?v=" + UUID.randomUUID().toString();


    //电话申报员
    public static String DIANHUA_SHENBAOYUAN = ApiHtml + "farm/index.html?self=dh&v=" + UUID.randomUUID().toString();

    //无害化辖区管理员
    public static String XIAQU_ADMIN = ApiHtml + "shixian/index.html?v=" + UUID.randomUUID().toString();

    //无害化辖区管理员
    public static String NEW_ADMIN_PAGE = ApiHtml + "shouyun/collectHistory.html?v=" + UUID.randomUUID().toString();

    //病死畜禽 饼状图
    public static String BING_SI_BZT = ApiHtml + "apptj/bsxqpie.html?v=" + UUID.randomUUID().toString();

    //病死畜禽 饼状图
    public static String QHBSDWZS = ApiHtml + "shixian/quxianTjZNY.html?v=" + UUID.randomUUID().toString();

    public static String XDRST = ApiHtml + "apptj/xdr12MontnAndScale.html?v=" + UUID.randomUUID().toString();


    //检疫受理
    public static String SBSL = ApiHtmlQuarantineApp + "DeclareList.html?v=" + UUID.randomUUID().toString();


    //申报检疫
    public static String SBJY = ApiHtmlQuarantineApp + "toQuarantineList.html?v=" + UUID.randomUUID().toString();

    //public static  String SBJY =ApiHtmlQuarantineApp + "originalRecords/originalRecord_test.html?Mid=075f9f19c0044eb7b80d7793c1e8dee4&pc=0";
    //检疫历史
    public static String JYLS = ApiHtmlQuarantineApp + "historyList.html?v=" + UUID.randomUUID().toString();

    //耳标发放
    public static String ERBIAOFAFANG = AoiEarTag + "form/form.html?fid=40c0ed09ba8345e19943a8a01a253c27&cid=9b3e8446f8f44f8a926b690dbac5cad1&v=" + UUID.randomUUID().toString();

    //耳标签收
    public static String ERBIAOQIANSHOU = AoiEarTag + "form/form.html?fid=4801a2b4b2ef4860a433ddcf2410fc72&v=" + UUID.randomUUID().toString();


    //ApiHtmlQuarantineApp + "originalRecords/FarmFile.html?v=" +  UUID.randomUUID().toString();
    public static String ADD_XDR_URL = ApiHtmlQuarantineApp + "originalRecords/FarmFile.html?v=" + UUID.randomUUID().toString();

    public static String BindSlaughterhouse = AoiEarTag + "form/form.html?fid=902239ee731a4835a3a3cbc652b5eb5b&v=" + UUID.randomUUID().toString();


    //防疫员签收发放
    public static String FYY_XDR = FARM_MAP + "EpidemicPhonePost.html?v=" + UUID.randomUUID().toString();

    public static String farmGet = FARM_MAP + "farmGet.html?v=" + UUID.randomUUID().toString();


    public static String XQMU = "https://zypc.nahs.org.cn/pzml/classify.html?v=" + UUID.randomUUID().toString();

    public static String TRPLY = CDApiUrl + "/pages/plugins/table/cardTable.html?id=ee16ae182b034a9b96473f90eda51c3a&v=" + UUID.randomUUID().toString();

    public static String YZTRPSH = CDApiUrl + "/pages/plugins/table/cardTable.html?id=0df31b74623848af9b2e6159bc84ce3f&v=" + UUID.randomUUID().toString();


    public static String WPTJ = FARMMAP + "GoodsStatistics.html?v=" + UUID.randomUUID().toString();


    public static String WPPD = AoiEarTag + "table/cardTable.html?id=06ba78b502e54c5d803305c5281ed535&v=" + UUID.randomUUID().toString();

    public static String SSYSML = FARMMAP + "aquaticwldlifequery.html?v=" + UUID.randomUUID().toString();

    public static String JYQY = FARMMAP + "nofishingarea.html?v=" + UUID.randomUUID().toString();


    public static String SZYF_ZZ = AoiEarTag + "table/cardTable.html?id=7bea8dcbc163461397ac67bbc4dd1122&v=" + UUID.randomUUID().toString();

    public static String SZYF_SPZ = AoiEarTag + "table/cardTable.html?id=e6f8eb6b16ec4f9ab3a7efaeec86f3f8&v=" + UUID.randomUUID().toString();

    public static String SZYF_SJTJ = AoiEarTag + "table/cardTable.html?id=d9a3c69267d7401390faa7b097b0d43d&v=" + UUID.randomUUID().toString();

    public static String SZYF_ZLZB = AoiEarTag + "table/cardTable.html?id=0cacd729a4fb4f9d8a6aabdf3e6d7ae8&v=" + UUID.randomUUID().toString();


    public static String daianchakan = AoiEarTag + "table/cardTable.html?id=0e961d74097c4b299dd9a31cd5d440f6&v=" + UUID.randomUUID().toString();

    public static String SZFY_YZGL = CDApiUrl + "/pages/plugins/table/cardTable.html?id=aedd7a44022c4be3baa3a6dfcdb98ed3&v=" + UUID.randomUUID().toString();

    public static String SZFY_YFGL = CDApiUrl + "/pages/plugins/table/cardTable.html?id=f2550756c1a541868589090493caec30&v=" + UUID.randomUUID().toString();

    public static String SZFY_DAGL = CDApiUrl + "/pages/plugins/table/cardTable.html?id=12303b2e62694704a2b99b9e119d1d42&v=" + UUID.randomUUID().toString();

    //先打后补申请
    public static String XDHBSB = CDApiUrl + "/pages/plugins/form/form.html?fid=70b0c497969a4b8f833f280c335f9f0f&cid=d0559e216422549148de9911439202147&v=" + UUID.randomUUID().toString();

    //疫苗采购
    public static String YMCG = CDApiUrl + "/pages/plugins/form/form.html?fid=2b2299ba127e4478a19a6a385db8d564&v=" + UUID.randomUUID().toString();

    //疫苗销售
    public static String YMXS = CDApiUrl + "/pages/plugins/form/form.html?fid=647d616d5f814c4ab9279a6dce93dd39&v=" + UUID.randomUUID().toString();

    //防疫信息填报
    public static String FYXXTB = CDApiUrl + "/pages/plugins/form/form.html?fid=d660487f7e3442a795fe85d223ebcace&v=" + UUID.randomUUID().toString();

    //补助申请
    public static String BUZHUSHENQING = CDApiUrl + "/pages/plugins/form/form.html?fid=6981e42f150144eca14f4065905f9b66&v=" + UUID.randomUUID().toString();

    //免疫效果评价上传
    public static String PINGJIA = CDApiUrl + "/pages/plugins/form/form.html?fid=574b7b930d3f4770b71d2178179f496a&cid=d0559e21622549148de9911439202147&id=693e9da9b48e4243b9514f98f9e617bf&v=" + UUID.randomUUID().toString();

    //台账
    public static String XIAOSHOUTAIZHANG = CDApiUrl + "/pages/plugins/table/cardTable.html?id=cd419476ce7b407ab4e8920758f4bc4b&v=" + UUID.randomUUID().toString();

    public static String YiMiaoPingHuiShou = CDApiUrl + "/pages/plugins/table/cardTable.html?id=1319838db6ec484189029a7929572024&v=" + UUID.randomUUID().toString();


    public static String mian_yi_shouli_and_bohui = CDApiUrl + "/pages/plugins/table/cardTable.html?id=cf60d3afde29450b994e6f02548f0ec6&v=" + UUID.randomUUID().toString();


    public static String mianyi_shenbao = CDApiUrl + "/pages/plugins/form/form.html?fid=dbf3d28e79614efabfd197371064d801&v=" + UUID.randomUUID().toString();

    //免疫修改
    public static String mian_yi_xiugai = CDApiUrl + "/pages/plugins/table/cardTable.html?id=d75ea1e074484beda963b94839f42c8d&v=" + UUID.randomUUID().toString();


    //无害化保险公司
    public static String WHHBXGS = CDApiUrl + "/pages/plugins/table/cardTable.html?id=74b73cb1605a4280b234b9648380215e&v=" + UUID.randomUUID().toString();

    //保险公司新增
    public static String WHHBXGSXZ = CDApiUrl + "/pages/plugins/form/form.html?fid=8d23228210e7467d80d09dba01ef0afa&v=" + UUID.randomUUID().toString();

    //无害化保险公司人员管理
    public static String WHHBXGSADD = CDApiUrl + "/pages/plugins/table/cardTable.html?id=6d73f7a563cf4c02a6595cb2adb58bcc&v=" + UUID.randomUUID().toString();

    //人员新增
    public static String WHHBXGSRUXZ = CDApiUrl + "/pages/plugins/form/form.html?fid=57922341106f489183b2feadfd436f30&v=" + UUID.randomUUID().toString();


    //无害化已投保收集单管理
    public static String WHHTBSJDGL = CDApiUrl + "/pages/plugins/table/cardTable.html?id=d41f050dfe444bb4a9ce82134cc1402e&v=" + UUID.randomUUID().toString();

    //无害化已投保收集单推送
    public static String WHHTBSJDTS = CDApiUrl + "/pages/plugins/form/form.html?fid=c24f4689d7f84d238be67ac2556861fa&v=" + UUID.randomUUID().toString();

    //屠宰场无害化申报
    public static String TZCWHHSB = CDApiUrl + "/pages/plugins/table/cardTable.html?id=c8d349df631341078a07544c6d8499aa&v=" + UUID.randomUUID().toString();


    //执法办案
    public static String ZFBA = CDApiUrl + "/pages/plugins/table/cardTable.html?id=9a863c50eb0949a8a00f6dd48882cde5&v=" + UUID.randomUUID().toString();

    //免疫体系评价
    public static String mainyi_tixi_pingjia = CDApiUrl + "/pages/farm-map/demo/mianyitixi.html?v=" + UUID.randomUUID().toString();


    ///////////////////////////////////// 无害化 H5页面 ///////////////////////////////

    //收运员动物统计
    public static String CollectorsStatisticsAnimal = ApiHtml + "shouyun/shouyunTjApp.html?title=animal";

    //收运员动物统计
    public static String CollectorsStatisticsApply = ApiHtml + "shouyun/shouyunTjApp.html?title=shenbaoshow";


    public static String WUZHIHUACHUZHENG = AoiEarTag + "form/form.html?fid=abea451619b04854ae319190a7168e7a&v=" + UUID.randomUUID().toString();


    public static  String Xdr_Edit_Url=CDApiUrl + "/pages/plugins/form/form.html?fid=3dbf40b6ba7c4aba9da6f805e562320b&v="+ UUID.randomUUID().toString();;

}
