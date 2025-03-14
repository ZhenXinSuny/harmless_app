package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.AppFunBean;
import com.agridata.cdzhdj.data.CustomerBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-01-13 14:01.
 * @Description :描述
 */
public class AppFunSP {

    private SharedPreferences mSharedPreferences;

    private AppFunSP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("role_data", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static AppFunSP getInstance() {
        return AppFunSPHolder.INSTANCE;
    }

    private static class AppFunSPHolder {
        private static final AppFunSP INSTANCE = new AppFunSP();
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
     * @param
     */
    public void setAppFunInfo(AppFunBean.Result result) {
        mSharedPreferences.edit().putString("appfun_info", GsonUtil.toJson(result)).apply();
    }

    /**
     *
     * @return
     */
    public AppFunBean.Result getAppFunInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("appfun_info", null),AppFunBean.Result.class);
        } catch (Exception e) {
            return null;
        }
    }


    public void setCustomerInfo(CustomerBean.Result result) {
        mSharedPreferences.edit().putString("customer_info", GsonUtil.toJson(result)).apply();
    }

    /**
     *
     * @return
     */
    public CustomerBean.Result getCustomerInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("customer_info", null),CustomerBean.Result.class);
        } catch (Exception e) {
            return null;
        }
    }
}
