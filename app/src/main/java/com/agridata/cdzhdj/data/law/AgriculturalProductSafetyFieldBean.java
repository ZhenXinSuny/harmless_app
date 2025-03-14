package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-12 10:06.
 * @Description :描述
 */
public class AgriculturalProductSafetyFieldBean {
    /********* 成都市农产品（种植业）检查表 ****/
    public String unifiedCode;//统一社会信用代码
    public String otherQualifications;//其他资质
    public String dominantVariety;//主栽品种

    public int shengchanjiluOne =-1;//是否建立生产记录
    public int shengchanjiluTwo =-1;//生产记录是否完善
    public int trpNyOne =-1;//是否使用禁限用及剧毒、高毒农药
    public int trpNyTwo =-1;//是否严格遵守农药使用安全间隔期
    public int trpNyThree =-1;//包装、标签、标识是否符合规定
    public int trpNyFour =-1;//是否按标签标注使用农药
    public int trpNyFive =-1;//是否建立农药使用记录
    public int trpFlOne =-1;//包装、标签、标识是否符合规定
    public int trpFlTwo =-1;//是否建立化肥使用记录
    public int bzfqw =-1;//是否未按照规定及时回收肥料等农业投入品的包装废弃物或者农用薄膜
    public int dlbz =-1;//是否违规使用地理标志及地域范围
    public int lsyj =-1;//是否违规使用有机、绿色食品标志标识
    public int anquan =-1;//是否存在违反农业转基因生物安全的行为
    public int zhiliang =-1;//是否抽样送检

    @Override
    public String
    toString() {
        return "AgriculturalProductSafetyFieldBean{" +
                "unifiedCode='" + unifiedCode + '\'' +
                ", otherQualifications='" + otherQualifications + '\'' +
                ", dominantVariety='" + dominantVariety + '\'' +
                ", shengchanjiluOne=" + shengchanjiluOne +
                ", shengchanjiluTwo=" + shengchanjiluTwo +
                ", trpNyOne=" + trpNyOne +
                ", trpNyTwo=" + trpNyTwo +
                ", trpNyThree=" + trpNyThree +
                ", trpNyFour=" + trpNyFour +
                ", trpNyFive=" + trpNyFive +
                ", trpFlOne=" + trpFlOne +
                ", trpFlTwo=" + trpFlTwo +
                ", bzfqw=" + bzfqw +
                ", dlbz=" + dlbz +
                ", lsyj=" + lsyj +
                ", anquan=" + anquan +
                ", zhiliang=" + zhiliang +
                '}';
    }
}
