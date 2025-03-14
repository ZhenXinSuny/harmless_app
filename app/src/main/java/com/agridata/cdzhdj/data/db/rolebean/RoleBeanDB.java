package com.agridata.cdzhdj.data.db.rolebean;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.db.converter.ShouYunRegionDTOConverter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-17 16:49.
 * @Description :描述
 */
@Entity
public class RoleBeanDB {

    @PrimaryKey(autoGenerate = false)
    public int id;
    @Embedded
    public RoleUserInfoDB roleUserInfoDb;

    @Embedded
    public RoleHarmlessUserDB roleHarmlessUserDb;


    @TypeConverters(ShouYunRegionDTOConverter.class)
    public List<HarmlessRegionBean> shouYunRegion;

    public static class HarmlessRegionBean {
        public int RegionParentID;
        public String RegionCode;
        public String RegionName;
        public int RegionLevel;
        public int RI2;
        @SerializedName("ID")
        public int id;
        public int RI1;
        public int RI4;
        public int RI3;
        public String RegionFullName;
        public int RI5;
    }
}


