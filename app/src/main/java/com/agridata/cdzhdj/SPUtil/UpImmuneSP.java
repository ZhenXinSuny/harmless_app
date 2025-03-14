package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.UpImmuneBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @auther 56454
 * @date 2022/6/29
 * @time 19:50.
 */
public class UpImmuneSP {
    private SharedPreferences mSharedPreferences;

    private UpImmuneSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("up_immune", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static UpImmuneSP getInstance() {
        return ImmuneSPHolder.INSTANCE;
    }

    private static class ImmuneSPHolder {
        private static final UpImmuneSP INSTANCE = new UpImmuneSP();
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
     * @param upImmuneBean
     */
    public void setUpImmune(UpImmuneBean upImmuneBean) {
        mSharedPreferences.edit().putString("up_immune_info", GsonUtil.toJson(upImmuneBean)).apply();
    }

    /**
     * 获取 当前登录的用户信息
     *
     * @return
     */
    public UpImmuneBean  getUpImmuneInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("up_immune_info", null), UpImmuneBean.class);
        } catch (Exception e) {
            return null;
        }
    }
}
