package com.agridata.cdzhdj.data.entrycheck;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-10 17:04.
 * @Description :描述  回收时间
 */
public class UpdataAnimalBBean {
    public String CategoryId = "44be5d010ab14aaaa99383eb72875145";
    public String CategoryName = "0AAFC65677282AC8-T_CC_AB";
    public String Mid;
    public RecyclingPeopleBean Recycling_People;
    public String RecoveryTime;//回收时间
    public String ActualNumber;//实际数量
    public String AnomalyNumber;//异常数量
    public String DeathNumber;//死亡数量
    public int Reception=1;
    //回收人
    public static class RecyclingPeopleBean {
        public String key;
        public String Name;

    }

    //{"key":1702,"Name":"已回收"}
    public  StatusBean Status;

    public static class StatusBean {
        public String key;
        public String Name;

    }
}
