package com.agridata.cdzhdj.data.goodout;

import com.agridata.cdzhdj.data.pigbreed.goods.AddGoodsBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-06-01 16:57.
 * @Description :描述
 */
public class GoodOutBean {
    public FarmBean Farm;
    public RegionBean Region;//区划
    public UseUserBean UseUser;
    public static class FarmBean {
        public String mid;
        public String Name;
    }


    public static class UseUserBean {
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
