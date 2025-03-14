package com.agridata.cdzhdj.data.entrycheck;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-10 17:04.
 * @Description :描述
 */
public class UpdataAnimalABean {
   public String CategoryId = "7f346e9b82414eb2bf1f3126906abe29";
    public String CategoryName = "80658B466CEF4463-T_CC_OutAA";
    public String Mid;
    public String RecoveryTime;//回收时间
    public String CheckNormalCount;//实际数量
    public String CheckAbnormalCount;//异常数量
    public String CheckDeadCount;//死亡数量
    public int Reception=1;
    //{"key":1702,"Name":"已回收"}
   public StatusBean Status;

    public static class  StatusBean{
        public String key;
        public String Name;

    }
}
