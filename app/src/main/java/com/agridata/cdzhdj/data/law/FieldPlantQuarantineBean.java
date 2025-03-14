package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-13 18:40.
 * @Description :描述 	植物检疫领域
 */
public class FieldPlantQuarantineBean {

   /********** 成都市植物检疫检查记录表 ************/
    public int plant1 =-1;//是否办理《植物检疫证书》
    public int plant2 =-1;//货单位、货物品种、数量、运往地点是否与《植物检疫证书》相符
    public int plant3 =-1;//是否伪造、涂改、买卖、转让植物检疫单证、印章、标志、封识
    public int plant4 =-1;//是否擅自开拆植物、植物产品包装，调换植物、植物产品，或者擅自改变植物、植物产品的规定用途
    public int plant5 =-1;//销售的种子标签是否标注植物检疫证明编号
    public int plant6 =-1;//是否带有国家规定的植物检疫性有害生物
    public int plant7 =-1;//是否申报产地检疫
    public int plant8 =-1;//报检过程中是否弄虚作假
    public int plant9 =-1;//繁殖材料来源是否自繁
    public int plant10 =-1;//来源于县级行政区域外的繁殖材料是否取得《植物检疫证书》
    public int plant11 =-1;//来源于县级行政区域外的繁殖材料的《植物检疫证书》 是否伪造、涂改、买卖、转让
    public int plant12 =-1;//是否违法试验、生产、推广带有植物检疫性有害生物的种子、苗木及其他繁殖材料
    public int plant13 =-1;//是否抽样送检

    @Override
    public String toString() {
        return "FieldPlantQuarantineBean{" +
                "plant1=" + plant1 +
                ", plant2=" + plant2 +
                ", plant3=" + plant3 +
                ", plant4=" + plant4 +
                ", plant5=" + plant5 +
                ", plant6=" + plant6 +
                ", plant7=" + plant7 +
                ", plant8=" + plant8 +
                ", plant9=" + plant9 +
                ", plant10=" + plant10 +
                ", plant11=" + plant11 +
                ", plant12=" + plant12 +
                ", plant13=" + plant13 +
                '}';
    }
}
