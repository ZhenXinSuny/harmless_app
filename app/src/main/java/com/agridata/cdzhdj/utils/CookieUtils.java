package com.agridata.cdzhdj.utils;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.network.utils.LogUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther 56454
 * @date 2022/6/22
 * @time 13:50.
 */
public class CookieUtils {
    /**
     * 将cookie同步到WebView
     *
     * @param url WebView要加载的url
     * @return true 同步cookie成功，false同步cookie失败
     * @Author JPH  1073
     */

    public static boolean syncCookie(String url) {
        CookieManager cookieManager = CookieManager.getInstance();
        Map<String, String> cookieMap = getCookieMap();
        for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
            String cookieStr = makeCookie(entry.getKey(), entry.getValue());
            cookieManager.setCookie(url, cookieStr);
        }
        String newCookie = cookieManager.getCookie(url);
        return !TextUtils.isEmpty(newCookie);

    }


    /**
     * 组装 Cookie 里需要的值
     *
     * @return
     */

    public static Map<String, String> getCookieMap() {
        LoginData userInfo = UserDataSP.getInstance().getUserInfo();
        LoginData.ResultBean result = userInfo.Result;
        Map<String, String> headerMap = new HashMap<>();
        if (result.roles.size()>0){
            headerMap.put("vbm_saas_role", GsonUtil.toJson(result.roles.get(0)));
        }
        result.dependency=null;
        headerMap.put("vbm_saas_user",  GsonUtil.toJson(result));
        LogUtil.d("lzx-----》","vbm_saas_user" + GsonUtil.toJson(result));
        headerMap.put("vbm_saas_app","");
        return headerMap;
    }


    /**
     * 拼接 Cookie 字符串
     *
     * @param key
     * @param value
     * @return
     */

    private static String makeCookie(String key, String value) {
        Date date = new Date();
        date.setTime(date.getTime() + 3 * 24 * 60 * 60 * 1000); //3天过期
        return key + "=" + value + ";expires=" + date + ";path=/";
    }

    public  static void clearCookie(){
        CookieSyncManager.createInstance(MyApplication.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookies(null);
            cookieManager.removeAllCookie();
            CookieSyncManager.getInstance().sync();

        }
    }
}
