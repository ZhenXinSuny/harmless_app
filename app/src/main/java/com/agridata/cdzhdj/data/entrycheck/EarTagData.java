package com.agridata.cdzhdj.data.entrycheck;

/**
 * @ProjectName : AdmissionCheck
 * @Author :
 * @Time : 2021/10/13 15:23
 * @Description :
 */
public class EarTagData {//不合格
    public String qualifiedEarTags;
    public String unqualifiedTags;

    @Override
    public String toString() {
        return "EarTagData{" +
                "qualifiedEarTags='" + qualifiedEarTags + '\'' +
                ", unqualifiedTags='" + unqualifiedTags + '\'' +
                '}';
    }
}
