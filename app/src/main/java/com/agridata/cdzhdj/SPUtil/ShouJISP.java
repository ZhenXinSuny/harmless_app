package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.ShouJiHuanCunBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @auther 56454
 * @date 2022/8/2
 * @time 9:45.
 */
public class ShouJISP {

    private SharedPreferences mSharedPreferences;

    private ShouJISP() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("ShouJISP", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static ShouJISP getInstance() {
        return ShouJISPHolder.INSTANCE;
    }

    private static class ShouJISPHolder {
        private static final ShouJISP INSTANCE = new ShouJISP();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    public void setShouJiHuanCunBeanInfo(ShouJiHuanCunBean shouJiHuanCunBeanInfo) {
        mSharedPreferences.edit().putString("shouji_info", GsonUtil.toJson(shouJiHuanCunBeanInfo)).apply();
    }

    public ShouJiHuanCunBean getShouJiHuanCunInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("shouji_info", null), ShouJiHuanCunBean.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 代养主体名称
     * @param mFosterCareName
     */
    public  void  setFosterCareName(String mFosterCareName){
        mSharedPreferences.edit().putString("FosterCareName", mFosterCareName ).apply();
    }

    public  String getFosterCareName(){
        return mSharedPreferences.getString("FosterCareName", "");
    }

}
