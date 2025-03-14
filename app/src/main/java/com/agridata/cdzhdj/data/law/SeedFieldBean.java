package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-07 16:46.
 * @Description :描述
 */
public class SeedFieldBean {
    public int infringePlant =-1;//是否侵犯植物新品种权
    public int fakePlant =-1;//是否假冒植物新品种权
    public int dengJi =-1;//是否通过国家、四川省审定或登记
    public int baozhaung =-1;//是否按规定包装
    public int biaoqian =-1;//是否附有标签
    public  int biaoqianneirong =-1;//标签标注的内容是否完整，符合要求
    public int shengchandangan =-1;//生产档案
    public int jingyingdangan =-1;//经营档案
    public int beian =-1;//是否按规定备案
    public int songjian =-1;//是否抽样送检
    public String creditCode;//营业执照统一社会信用代码
    public String businessCode;//农作物种子生产经营许可证

    @Override
    public String toString() {
        return "SeedFieldBean{" +
                "infringePlant=" + infringePlant +
                ", fakePlant=" + fakePlant +
                ", dengJi=" + dengJi +
                ", baozhaung=" + baozhaung +
                ", biaoqian=" + biaoqian +
                ", biaoqianneirong=" + biaoqianneirong +
                ", shengchandangan=" + shengchandangan +
                ", jingyingdangan=" + jingyingdangan +
                ", beian=" + beian +
                ", songjian=" + songjian +
                ", creditCode='" + creditCode + '\'' +
                ", businessCode='" + businessCode + '\'' +
                '}';
    }
}
