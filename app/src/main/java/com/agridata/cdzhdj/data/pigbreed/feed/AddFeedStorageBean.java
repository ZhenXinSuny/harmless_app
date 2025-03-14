package com.agridata.cdzhdj.data.pigbreed.feed;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.http.PUT;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-27 13:55.
 * @Description :描述
 */
public class AddFeedStorageBean {
    //	饲料	6102   兽药	6101
    public String CategoryId = "5bfa949f6e294b4e80474fd57dec3d24";
    public String CategoryName = "F709AE4E3DC1D650-T_YZT_PigGoods_Inventory";
    public String _PartId = "d5896b31964e425382df52f655dedfc2";


    public FarmBean  Farm;//养殖户

    public RegionBean  Region;//区划


    public String Manufacturer;//廠商
    public String Enterprise;//經營企業
    public String Brand;//品牌
    public String Batch;//批次
    public String BeBornDate;//生产日期
    public String Validity;//有效期
   public DepositorUserBean  DepositorUser;//入库人员
    public String WarehousingTime;//入库时间
    public GoodsTypeBean  GoodsType;//	物品种类
    public int  GoodsNumber;//数量
    public String  GoodsName;//物品名称


    public static  class  GoodsTypeBean{
        public String key;
        public String Name;

    }


    public static class FarmBean{
        public String mid;
        public String Name;
    }

    public static class DepositorUserBean{
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
