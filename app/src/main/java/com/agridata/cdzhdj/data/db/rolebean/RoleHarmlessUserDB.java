package com.agridata.cdzhdj.data.db.rolebean;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-23 11:41.
 * @Description :描述
 */
public class RoleHarmlessUserDB {


    @ColumnInfo(name = "RoleHarmlessUserMid")
    public String mid;
    @Ignore
    public Object sourceId;

    @ColumnInfo(name = "RoleHarmlessUserName")
    public String name;
    public String partid;

    @ColumnInfo(name = "RoleHarmlessUserID")
    public String userId;
    @ColumnInfo(name = "RoleHarmlessUserMobile")
    public String mobile;
    @Embedded
    public RoleDTO role;
    public String idcard;
    @Embedded
    public FactoryDTO factory;



    public static class RoleDTO {
        public String key;

        @ColumnInfo(name = "RoleDTOName")
        @SerializedName("Name")
        public String name;
    }

    public static class FactoryDTO {
        @SerializedName("Mid")
        public String mid;

        @ColumnInfo(name = "FactoryDTOName")
        @SerializedName("Name")
        public String name;
    }
}
