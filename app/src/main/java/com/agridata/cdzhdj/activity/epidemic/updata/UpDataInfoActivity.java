package com.agridata.cdzhdj.activity.epidemic.updata;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.ImmuneAnimalBean;
import com.agridata.cdzhdj.databinding.ActivityUpdataInfoBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * Created by XXX.
 * Date: 2022/11/24
 * describe 数据同步
 */
public class UpDataInfoActivity extends BaseActivity<ActivityUpdataInfoBinding> {

    private CustomLoadingDialog mLoadingDialog;

    private final static String TAG = "lzx------》";

    public static void start(Activity context) {
        Intent intent = new Intent(context, UpDataInfoActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected ActivityUpdataInfoBinding getBinding() {
        return ActivityUpdataInfoBinding.inflate(getLayoutInflater());
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
    @Override
    protected void initView() {

        initLoadingView();
        binding.titlebarLeft.setOnClickListener(view -> finish());

        binding.animalTypeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading("动物种类同步中...");
                getAnimalBean();
            }
        });
    }

    @Override
    protected void initDatas() {

    }

    private void getAnimalBean(){
        HttpRequest.getImmuneAnimal(UpDataInfoActivity.this, new CallBackLis<ImmuneAnimalBean>() {
            @Override
            public void onSuccess(String method, ImmuneAnimalBean content) {
                LogUtil.d(TAG, "获取动物" + content.toString());
                hideLoading();
                if (content.status == 0) {
                    Objects.requireNonNull(RxToast.success(UpDataInfoActivity.this, "同步动物种类成功"));
                    AnimalSP.getInstance().setAnimalList(content.result);
                }else {
                    Objects.requireNonNull(RxToast.success(UpDataInfoActivity.this, content.message));
                }

            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

}
