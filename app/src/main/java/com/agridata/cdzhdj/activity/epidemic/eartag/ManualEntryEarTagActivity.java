package com.agridata.cdzhdj.activity.epidemic.eartag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.SPUtil.AnimalSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.EarTagStartEndNumBean;
import com.agridata.cdzhdj.data.ImmuneXdrBean;
import com.agridata.cdzhdj.data.immune.EarTagIsInHistoryData;
import com.agridata.cdzhdj.databinding.ActivityManualentryEartagBinding;
import com.agridata.cdzhdj.utils.RxBus;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-01 09:33.
 * @Description :描述 《耳标手工批量录入》
 */
public class ManualEntryEarTagActivity extends BaseActivity<ActivityManualentryEartagBinding> {
    private CustomLoadingDialog mLoadingDialog;
    int max = 2000;
    private ImmuneXdrBean.Result.AnimalVariety chooseAnimal;
    private int earTagLength;

    /**
     * 开启activity
     *
     * @param context
     */
    public static void start(Context context, int earTagLength) {
        Intent intent = new Intent(context, ManualEntryEarTagActivity.class);
        Bundle data = new Bundle();
        data.putInt("earTagLength", earTagLength);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    /**
     * 获取上一级所传过来的值  去判断加载哪一个Url
     */
    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        earTagLength = bundle.getInt("earTagLength");
    }


    @Override
    protected ActivityManualentryEartagBinding getBinding() {
        return ActivityManualentryEartagBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();
        chooseAnimal = AnimalSP.getInstance().getChooseAnimal();
        binding.addEartagType.setText(Html.fromHtml(String.format("请录入<font color=red>%s</font>耳标起始和终止号段", chooseAnimal.name)));
        int a = max - earTagLength;
        binding.eartagLength.setText(Html.fromHtml(String.format("当前最多还可输入<font color=red>%d</font>个耳标记录", max)));
        binding.titlebarLeft.setOnClickListener(view -> finish());
        binding.sureBtn.setOnClickListener(v -> setEarTagNum());
    }

    @Override
    protected void initDatas() {

    }

    private void setEarTagNum() {

        String num = binding.addEartagNum.getText().toString().trim();
        String start = binding.addEartagStart.getText().toString().trim();
        String end = binding.addEartagEnd.getText().toString().trim();


        String substring = num.substring(0, 1);
        if (num.length() == 7 && start.length() == 8 && end.length() == 8) {

            int n = Integer.parseInt(num);
            int s = Integer.parseInt(start);
            int e = Integer.parseInt(end);
            if (e < s) {
                Objects.requireNonNull(RxToast.error(ManualEntryEarTagActivity.this, "起始号段不能大于结束号段"));
            } else if (e - s + 1 > max ) {
                Objects.requireNonNull(RxToast.info(ManualEntryEarTagActivity.this, "当前最多可发放" + max + "耳标记录"));
            } else {
                if (substring.equals(String.valueOf(chooseAnimal.eartagCode))) {
                    if (chooseAnimal.eartagCode == 1) {
                        String stringStart = num + start;
                        String stringEnd = num + end;
                        showLoading("耳标库存查询中...");
                        HttpRequest.queryEarTagIsInHistory(ManualEntryEarTagActivity.this, stringStart, stringEnd, new CallBackLis<EarTagIsInHistoryData>() {
                            @Override
                            public void onSuccess(String method, EarTagIsInHistoryData content) {
                                hideLoading();
                                if (content.code == 200) {
                                    if (content.data.isEmpty()) {
                                        EarTagStartEndNumBean earTagStartEndNumBean = new EarTagStartEndNumBean();
                                        earTagStartEndNumBean.Num = n;
                                        earTagStartEndNumBean.StartNum = s;
                                        earTagStartEndNumBean.EndNum = e;
                                        RxBus.getInstance().post("eartag_num_edit", earTagStartEndNumBean);
                                        finish();
                                    } else {
                                        Objects.requireNonNull(RxToast.warning(ManualEntryEarTagActivity.this, "[" + content.data.get(0).lower + "-" + content.data.get(0).upper + "]号段不在库存中，请重新填写"));
                                    }
                                }
                            }
                            @Override
                            public void onFailure(String method, String error) {
                                hideLoading();
                            }
                        });
                    } else {
                        EarTagStartEndNumBean earTagStartEndNumBean = new EarTagStartEndNumBean();
                        earTagStartEndNumBean.Num = n;
                        earTagStartEndNumBean.StartNum = s;
                        earTagStartEndNumBean.EndNum = e;
                        RxBus.getInstance().post("eartag_num_edit", earTagStartEndNumBean);
                        finish();
                    }


                } else {
                    Objects.requireNonNull(RxToast.error(ManualEntryEarTagActivity.this, "耳标号段与动物种类不匹配，请重新输入"));

                }

            }
        } else {
            Objects.requireNonNull(RxToast.error(ManualEntryEarTagActivity.this, "号码长度输入有误,请输入完整的号段"));
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
