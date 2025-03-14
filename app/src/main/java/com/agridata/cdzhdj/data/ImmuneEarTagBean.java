package com.agridata.cdzhdj.data;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-11-30 09:37.
 * @Description :描述
 */
public class ImmuneEarTagBean {

    public String EarTag;
    public boolean isSelected = false;

    @Override
    public String toString() {
        return "ImmuneEarTagBean{" +
                "EarTag='" + EarTag + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
