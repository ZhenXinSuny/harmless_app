package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.immune.ImmuneSaveBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-01-11 17:14.
 * @Description :描述
 */
public class ImmuneSp {

    private SharedPreferences mSharedPreferences;

    private ImmuneSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("immune_sp", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ImmuneSp getInstance() {
        return ImmuneSp.ImmuneHolder.INSTANCE;
    }

    private static class ImmuneHolder {
        private static final ImmuneSp INSTANCE = new ImmuneSp();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }



    public void setImmuneSaveInfo(ImmuneSaveBean immuneSaveInfo) {
        mSharedPreferences.edit().putString("immune_save", GsonUtil.toJson(immuneSaveInfo)).apply();
    }

    public ImmuneSaveBean getImmuneSaveInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("immune_save", null), ImmuneSaveBean.class);
        } catch (Exception e) {
            return null;
        }
    }



}
