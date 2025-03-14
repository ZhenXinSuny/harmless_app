package com.agridata.cdzhdj.data.db.rolebean;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-22 13:44.
 * @Description :描述
 */
public class RoleUserInfoDB {


    @ColumnInfo(name = "RoleUserInfoDBUserID")
    public String userId;
    public String username;

    @ColumnInfo(name = "RoleUserInfoDBMobile")
    public String mobile;
    @Ignore
    public String email;
    @Ignore
    public String password;
    public String nickname;
    @Ignore
    public boolean disabled;
    @Ignore
    public boolean deleted;
    public String appId;
    @Ignore
    public int ordinal;
    public String name;
    public String avatar;
    @Ignore
    public Object department;
    @Ignore
    public Object job;
    @Ignore
    public Object extension;
    @Ignore
    public boolean readOnly;
    @Ignore
    public Object tags;
    @Ignore
    public Object bindings;
    @Ignore
    public String lastLogin;
    @Ignore
    public String createAt;
    @Ignore
    public String lastUpdate;
    @Ignore
    public Object settings;
    @Ignore
    public String passLastUpdate;
    @Ignore
    public int passErrorCount;
    @Ignore
    public Object passErrorTime;
    @Ignore
    public Object isInitialPass;
    @Ignore
    public Object isActiveEmail;

}
