package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-11 14:42.
 * @Description :描述 	肥料领域
 */
public class FertilizerFieldBean {
    /********  成都市肥料生产经营企业执法检查记录表 *******/
    public String businesslicensenumber;//营业执照号码
    public int feiliaodengjiOne = -1;//生产、销售的产品是否登记、备案
    public int feiliaodengjiTwo = -1;//是否假冒伪造登记证
    public int feiliaodengjiThree = -1;//登记证是否过期限
    public int chanpinbiaoqianOne = -1;//是否附有标签
    public int chanpinbiaoqianTwo = -1;//标签标注的内容是否完整，符合要求
    public int chanpinbiaoqianThree = -1;//是否超过保质期
    public int chanpinbiaoqianFour = -1;//包装内是否附具产品质量检验合格证（开袋检查）
    public int zhiliang = -1;//是否抽样送检
}
