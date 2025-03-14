package com.agridata.cdzhdj.activity.mine.setting.changepwd;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.SPUtil.AccountPwdSp;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.LoginActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.NewPwdData;
import com.agridata.cdzhdj.data.StatusChangePwd;
import com.agridata.cdzhdj.databinding.ActivityChangePwdBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.agridata.util.Base64Util;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-08-30 14:02.
 * @Description :描述  修改密码
 */
public class ChangePwdActivity extends BaseActivity<ActivityChangePwdBinding> {
    private CustomLoadingDialog mLoadingDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, ChangePwdActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityChangePwdBinding getBinding() {
        return ActivityChangePwdBinding.inflate(getLayoutInflater());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initView() {
            initLoadingView();
        binding.titlebarLeft.setOnClickListener(view ->finish());
        binding.confirmBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(Objects.requireNonNull(binding.inputNewPwdCet.getText()).toString())  ){
                Objects.requireNonNull(RxToast.warning(ChangePwdActivity.this, "请输入新密码"));
            }else if (binding.inputNewPwdCet.getText().length()<8){
                Objects.requireNonNull(RxToast.warning(ChangePwdActivity.this, "密码不能少于8位字符"));
            }else if (TextUtils.isEmpty(Objects.requireNonNull(binding.inputNewPwdAgainCet.getText()).toString()) ){
                Objects.requireNonNull(RxToast.warning(ChangePwdActivity.this, "请再次确认新密码"));
            }else if (binding.inputNewPwdAgainCet.getText().length()<8){
                Objects.requireNonNull(RxToast.warning(ChangePwdActivity.this, "密码不能少于8位字符"));
            }else if(!Objects.requireNonNull(binding.inputNewPwdCet.getText()).toString().equals(Objects.requireNonNull(binding.inputNewPwdAgainCet.getText()).toString())){
                Objects.requireNonNull(RxToast.warning(ChangePwdActivity.this, "两次密码输入不一致"));
            }else {
                setNewPwd();
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void  setNewPwd()  {
        NewPwdData newPwdData = new NewPwdData();
        newPwdData.oldTel = UserDataSP.getInstance().getUserInfo().Result.mobile;
        newPwdData.password = Objects.requireNonNull(binding.inputNewPwdCet.getText()).toString();

        String jsonString = GsonUtil.toJson(newPwdData);
        try {
            String signString = Base64Util.encodeData(jsonString);
                HttpRequest.updatePassword(ChangePwdActivity.this,signString, new CallBackLis<StatusChangePwd>() {
                    @Override
                    public void onSuccess(String method, StatusChangePwd content) {
                        LogUtil.d("lzx---------》", content.toString());
                        if (content.code == 200) {
                            Objects.requireNonNull(RxToast.success(ChangePwdActivity.this, "密码修改成功，请重新登录"));
                            UserDataSP.getInstance().clear();
                            AccountPwdSp.getInstance().clear();
                            ActivityManager.getInstance().finishAllActivity();
                            LoginActivity.start(ChangePwdActivity.this);
                        } else {
                            hideLoading();
                            Objects.requireNonNull(RxToast.error(ChangePwdActivity.this, content.data));
                        }
                    }
                    @Override
                    public void onFailure(String method, String error) {
                        Objects.requireNonNull(RxToast.error(ChangePwdActivity.this, error));
                    }
                });
        }catch (Exception ignored){
            Objects.requireNonNull(RxToast.success(ChangePwdActivity.this, "两次密码输入不一致"));
        }

    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(this);
        this.mLoadingDialog.setCanceledOnTouchOutside(false);
    }


    /**
     * 显示加载框
     */
    public void showLoading(String tips) {
        if (this.mLoadingDialog != null && !this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
            this.mLoadingDialog.setTitleVisibility(0);
            this.mLoadingDialog.setTitle(tips);
        }
    }

    /**
     * 隐藏 加载框
     */
    public void hideLoading() {
        if (this.mLoadingDialog != null && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.hide();
        }
    }

}
