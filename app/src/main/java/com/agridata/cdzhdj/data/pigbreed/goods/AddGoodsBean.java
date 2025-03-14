package com.agridata.cdzhdj.data.pigbreed.goods;

import com.agridata.cdzhdj.data.pigbreed.feed.AddFeedStorageBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 16:56.
 * @Description :描述
 */
public class AddGoodsBean {

    public String farm;
    public String region;//区划
    public String goodsName;//物品名称
    public String batch;//批次
    public int singleUseNumber;//
    public int amount;
    public int totalUsage;
    public String useUser;
    public String useDate;//
    public String mid;



    public static class UseUserBean {
        public String mid;
        public String Name;
    }

    public static class FarmBean {
        public String mid;
        public String Name;
    }

    public static class RegionBean implements Serializable {
        @SerializedName("ID")
        public int id;
        public int RI1;
        public int RI2;
        public int RI3;
        public int RI4;
        public int RI5;
        public String RegionCode;
        public String RegionName;
        public int RegionLevel;
        public String RegionFullName;
        public int RegionParentID;
    }
}
