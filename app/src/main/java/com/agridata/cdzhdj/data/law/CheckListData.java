package com.agridata.cdzhdj.data.law;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-03 14:19.
 * @Description :描述
 */
public class CheckListData {
    public int parentid;
    public int  code;
    public String name;

    @Override
    public String toString() {
        return "CheckListData{" +
                "parentid=" + parentid +
                ", code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}

