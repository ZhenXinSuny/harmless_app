package com.agridata.cdzhdj.data.entrycheck;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-08-08 14:24.
 * @Description :描述
 */
public class EntryCheckLogBean {
    public String EntryCheckMid;
    public String CategoryId="af236cb2dd104532bb9d02f54de5cccf";
    public  String _PartId="d5896b31964e425382df52f655dedfc2";
    public String ChangeTime;
    public String CertNo;
    public ModifierBean Modifier;
    public ChangeInfoBean ChangeInfo;

    public static class ModifierBean {
        public String Name;
        public String UserID;
    }
    public static class ChangeInfoBean {
        public int  NumberIsPass;
        public String ActualNumber;
        public String AnomalyNumber;
        public String DeathNumber;
    }
}
