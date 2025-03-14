package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-11 09:51.
 * @Description :描述 农药领域
 */
public class PesticideFieldBean {
    public String businessLicenseNumber;//营业执照号码
    public String pesticideOperationLicenseNumber;//农药经营许可证编号
    public int   productionAndManagementOne=-1;//生产者是否取得农药生产许可证
    public int   productionAndManagementTwo =-1;//生产的农药是否取得农药登记证
    public int   productionAndManagementThree =-1;//经营者是否取得农药经营许可证或者按规定备案
    public int   productionAndManagementFour =-1;//相关许可证明否超过有效期限

    public int   chanPinBiaoQianOne =-1;//是否附有标签
    public int   chanPinBiaoQianTwo =-1 ;//标签标注的内容是否符合规定
    public int   chanPinBiaoQianThree =-1;//是否超过保质期

    public int jinXianNongYaoOne =-1;//是否生产、经营国家禁用农药
    public int  jinXianNongYaoTwo =-1 ;//是否超范围经营限制使用农药


    public int taiZhang =-1 ;//是否执行相关台账制度
    public  int wangShangXiaoShou =-1 ;//是否有网络经营店铺

    public int chanPinZhiLiangOne =-1;//是否附具产品质量检验合格证
    public int chanPinZhiLiangTwo= -1;//是否抽样送检
}
