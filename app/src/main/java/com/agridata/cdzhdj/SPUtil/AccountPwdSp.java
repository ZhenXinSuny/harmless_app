package com.agridata.cdzhdj.SPUtil;

import android.content.Context;
import android.content.SharedPreferences;

import com.agridata.cdzhdj.base.MyApplication;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 18:31.
 */
public class AccountPwdSp {
    private SharedPreferences mSharedPreferences;

    private AccountPwdSp() {
        mSharedPreferences = MyApplication.getContext().getSharedPreferences("account_pwd", Context.MODE_PRIVATE);
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static AccountPwdSp getInstance() {
        return UserHolder.INSTANCE;
    }

    private static class UserHolder {
        private static final AccountPwdSp INSTANCE = new AccountPwdSp();
    }

    /**
     * 清空 SharedPreferences
     */
    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }
    public void setAccount(String   account) {
        mSharedPreferences.edit().putString("ACCOUNT", account).apply();
    }
    public String getAccount() {
        return mSharedPreferences.getString("ACCOUNT", "");
    }
    public void setPwd(String   pwd) {
        mSharedPreferences.edit().putString("PWD", pwd).apply();
    }
    public String getPwd() {
        return mSharedPreferences.getString("PWD", "");
    }


    public void setAccountCb(boolean   accountCb) {
        mSharedPreferences.edit().putBoolean("ACCOUNT_CB", accountCb).apply();
    }
    public boolean getAccountCb() {
        return mSharedPreferences.getBoolean("ACCOUNT_CB", true);
    }

    public void setPwdCb(boolean   pwdCb) {
        mSharedPreferences.edit().putBoolean("PWD_CB", pwdCb).apply();
    }
    public boolean getPwdCb() {
        return mSharedPreferences.getBoolean("PWD_CB", true);
    }
}
