package com.agridata.cdzhdj.data;

import java.io.Serializable;
import java.util.List;

/**
 * @ProjectName : AdmissionCheck
 * @Author :
 * @Time : 2021/10/12 10:15
 * @Description :
 */
public class CheckInfoData implements Serializable {

    public String errorEarTag;
    public List<String> EarTag;
    public int Type;
    public int Count;
    public String imgInfo;

    @Override
    public String toString() {
        return "CheckInfoData{" +
                "errorEarTag='" + errorEarTag + '\'' +
                ", EarTag=" + EarTag +
                ", Type=" + Type +
                ", Count=" + Count +
                ", imgInfo='" + imgInfo + '\'' +
                '}';
    }
}
