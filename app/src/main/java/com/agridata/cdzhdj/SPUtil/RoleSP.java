package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @auther 56454
 * @date 2022/6/29
 * @time 19:50.
 */
public class RoleSP {
    private SharedPreferences mSharedPreferences;

    private RoleSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("role_data", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static RoleSP getInstance() {
        return RoleSPHolder.INSTANCE;
    }

    private static class RoleSPHolder {
        private static final RoleSP INSTANCE = new RoleSP();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     * 保存 当前登录的用户信息
     *
     * @param roleBean
     */
    public void setRoleInfo(RoleBean.DataBean roleBean) {
        mSharedPreferences.edit().putString("role_info", GsonUtil.toJson(roleBean)).apply();
    }

    /**
     * 获取 当前登录的用户信息
     *
     * @return
     */
    public RoleBean.DataBean getRoleInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("role_info", null), RoleBean.DataBean.class);
        } catch (Exception e) {
            return null;
        }
    }
}
