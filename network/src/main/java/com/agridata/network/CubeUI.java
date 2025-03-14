package com.agridata.network;

import android.content.Context;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-12-27 16:31.
 * @Description :描述
 */
public class CubeUI {

    /**
     * CubeUI 单例模式
     */
    private static volatile CubeUI mInstance = null;

    /**
     * ApplicationContext
     */
    private Context mApplicationContext;

    /**
     * cube数据提供者，主要由上层app提供
     */
    private CubeDataProvider mCubeDataProvider;

    private CubeUI() {

    }
    /**
     * 获取 CubeUI 单例对象
     *
     * @return
     */
    public static CubeUI getInstance() {
        if (mInstance == null) {
            synchronized (CubeUI.class) {
                if (mInstance == null) {
                    mInstance = new CubeUI();
                }
            }
        }
        return mInstance;
    }

    /**
     * CubeUI初始化
     *
     */
    public void init(Context applicationContext, CubeDataProvider cubeDataProvider) {
        this.mApplicationContext = applicationContext;
        this.mCubeDataProvider = cubeDataProvider;

    }

    /**
     * 获取ApplicationContext
     *
     * @return
     */
    public Context getApplicationContext() {
        return this.mApplicationContext;
    }
    public CubeDataProvider getCubeDataProvider() {
        return mCubeDataProvider;
    }
}
