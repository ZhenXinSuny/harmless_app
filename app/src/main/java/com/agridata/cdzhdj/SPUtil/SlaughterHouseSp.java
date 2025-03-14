package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.entrycheck.SlaughterHouseBean;
import com.agridata.cdzhdj.utils.GsonUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-01-03 19:22.
 * @Description :描述
 */
public class SlaughterHouseSp {

    private SharedPreferences mSharedPreferences;

    private SlaughterHouseSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("slaughter_house_data", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static SlaughterHouseSp getInstance() {
        return SlaughterHouseSpHolder.INSTANCE;
    }

    private static class SlaughterHouseSpHolder {
        private static final SlaughterHouseSp INSTANCE = new SlaughterHouseSp();
    }
    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().commit();
    }

    /**
     *
     * @param regionBean
     */
    public void setSlaughterHouseInfo(SlaughterHouseBean.Result.Region regionBean) {
        mSharedPreferences.edit().putString("slaughter_house_info", GsonUtil.toJson(regionBean)).apply();
    }

    /**
     *
     * @return
     */
    public SlaughterHouseBean.Result.Region getSlaughterHouseInfo() {
        try {
            return GsonUtil.fromJson(mSharedPreferences.getString("slaughter_house_info", null), SlaughterHouseBean.Result.Region.class);
        } catch (Exception e) {
            return null;
        }
    }
}
