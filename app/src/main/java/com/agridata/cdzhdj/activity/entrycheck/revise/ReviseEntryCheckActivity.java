package com.agridata.cdzhdj.activity.entrycheck.revise;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckHistoryDetailBean;
import com.agridata.cdzhdj.data.entrycheck.EntryCheckLogBean;
import com.agridata.cdzhdj.databinding.ActivityReviseEntryCheckBinding;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.util.DateTimeUtils;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-08-08 09:06.
 * @Description :描述
 */
public class ReviseEntryCheckActivity extends BaseActivity<ActivityReviseEntryCheckBinding> {
    private EntryCheckHistoryDetailBean.Result result;
    private CustomLoadingDialog mLoadingDialog;
    private String mMid;
    private int mAnimalCount = -1;


    private int endTimesType = -1;
    private int endAddressType = -1;
    private int carType = -1;
    private int designatedChannelType = -1;
    private int earTagsType = -1;
    private int checkResult;


    public static void start(Context context, String Mid) {
        Intent intent = new Intent(context, ReviseEntryCheckActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }


    @Override
    protected ActivityReviseEntryCheckBinding getBinding() {
        return ActivityReviseEntryCheckBinding.inflate(getLayoutInflater());
    }

    private void getArguments() {
        mMid = this.getIntent().getStringExtra("Mid");

    }


    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(view -> finish());
        this.getArguments();
        this.initLoadingView();
    }

    @Override
    protected void initDatas() {
        getHistoryEntryCheckDetails();


        binding.summitBtn.setOnClickListener(view -> {
            if (checkInfoSummit()) {
                summitCheckInfo();
            }

        });
    }

    private boolean checkInfoSummit() {
        if (mAnimalCount == -1) {
            Objects.requireNonNull(RxToast.warning(ReviseEntryCheckActivity.this, "请选择动物数量是否合格"));
            return false;
        }
        if (TextUtils.isEmpty(binding.normalEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(ReviseEntryCheckActivity.this, "请输入实际入场数量"));
            return false;
        }
        if (TextUtils.isEmpty(binding.abnormalEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(ReviseEntryCheckActivity.this, "请输入异常数量"));
            return false;
        }
        if (TextUtils.isEmpty(binding.deadEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(ReviseEntryCheckActivity.this, "请输入死亡数量"));
            return false;
        }
        return true;
    }

    private void getHistoryEntryCheckDetails() {
        showLoading("详细数据加载中...");
        HttpRequest.getHistoryEntryCheckDetails(ReviseEntryCheckActivity.this, mMid, new CallBackLis<>() {


            @Override
            public void onSuccess(String method, EntryCheckHistoryDetailBean content) {
                hideLoading();
                if (content.status == 0) {

                    result = content.result;

                    binding.certNoTv.setText(String.format("[%s]", content.result.certNo));
                    if ("2".equals(content.result.certType)) {
                        binding.certTypeTv.setText("动物B证");
                    } else {
                        binding.certTypeTv.setText("动物A证");
                    }
                    binding.testResultTv.setText(content.result.checkResult == 1 ? "合格" : "不合格");
                    binding.timeQualifiedTv.setText(content.result.timeIsPass == 1 ? "合格" : "不合格");
                    binding.addressQualifiedTv.setText(content.result.addressIsPass == 1 ? "合格" : "不合格");
                    binding.carQualifiedTv.setText(content.result.carIsPass == 1 ? "合格" : "不合格");
                    binding.channelQualifiedTv.setText(content.result.roadIsPass == 1 ? "合格" : "不合格");
                    binding.animalQualifiedTv.setText(content.result.numberIsPass == 1 ? "合格" : "不合格");
                    binding.eartagNumTv.setText(content.result.earTagIsPass == 1 ? "合格" : content.result.earTagIsPass == 2 ? "不抽查" : "不合格");
                    binding.recyclerTv.setText(content.result.checkUser.name);
                    binding.recyclerTimeTv.setText(content.result.slaughterHouseName);
                    binding.actualQuantityTv.setText(String.valueOf(content.result.actualNumber));
                    binding.abnormalQuantityTv.setText(String.valueOf(content.result.anomalyNumber));
                    binding.deathsNumTv.setText(String.valueOf(content.result.deathNumber));


                    endTimesType = content.result.timeIsPass;
                    endAddressType = content.result.addressIsPass;
                    carType = content.result.carIsPass;
                    designatedChannelType = content.result.roadIsPass;
                    earTagsType = content.result.earTagIsPass;


                    binding.designatedChannelB.setClickable(false);
                    binding.designatedChannelYesRb.setClickable(false);
                    binding.designatedChannelNoRb.setClickable(false);
                    if ("2".equals(result.certType)) {
                        binding.designatedChannelB.setVisibility(View.VISIBLE);
                        binding.designatedChannelB.setChecked(true);
                    } else {
                        binding.designatedChannelB.setVisibility(View.GONE);
                        binding.designatedChannelYesRb.setChecked(true);
                    }

                    binding.endTimesYesRb.setClickable(false);
                    binding.endTimesNoRb.setClickable(false);
                    if (content.result.timeIsPass == 1) {
                        binding.endTimesYesRb.setChecked(true);
                    } else {
                        binding.endTimesNoRb.setChecked(true);
                    }


                    binding.endAddressYesRb.setClickable(false);
                    binding.endAddressNoRb.setClickable(false);
                    if (content.result.addressIsPass == 1) {
                        binding.endAddressYesRb.setChecked(true);
                    } else {
                        binding.endAddressNoRb.setChecked(true);
                    }

                    binding.carYesRb.setClickable(false);
                    binding.carNoRb.setClickable(false);
                    if (content.result.carIsPass == 1) {
                        binding.carYesRb.setChecked(true);
                    } else {
                        binding.carNoRb.setChecked(true);
                    }

                    binding.eartagsNoCheckRb.setClickable(false);
                    binding.eartagsYesRb.setClickable(false);
                    binding.eartagsNoRb.setClickable(false);
                    if (content.result.earTagIsPass == 1) {
                        binding.eartagsYesRb.setChecked(true);
                    } else {
                        binding.eartagsNoRb.setChecked(true);
                    }

                } else {
                    Objects.requireNonNull(RxToast.error(ReviseEntryCheckActivity.this, "获取数据失败，请重试~"));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
            }
        });
    }


    public void onRadioButtonClicked(View view) {
        RadioButton button = (RadioButton) view;
        boolean isChecked = button.isChecked();
        if (view == binding.animalCountYesRb) {
            if (isChecked) {
                mAnimalCount = 1;
            }
        } else if (view == binding.animalCountNoRb) {
            if (isChecked) {
                mAnimalCount = 0;
            }
        }
    }


    private void showCheckDialog(int checkResult) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        final AlertDialog exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(exitDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = (TextView) view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = (TextView) view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = (TextView) view.findViewById(R.id.content_tv);
        TextView titleTv = (TextView) view.findViewById(R.id.title_tv);
        titleTv.setVisibility(View.VISIBLE);
        titleTv.setText("修改检查结果");
        contentTv.setTextSize(14);
        if (checkResult == 1) {
            contentTv.setText("当前入场数据合格，是否确定修改？");
            setTextColor(ContextCompat.getColor(this, R.color.J5), 8, contentTv);
        } else {
            contentTv.setText("当前入场数据不合格，是否确定修改？");
            setTextColor(ContextCompat.getColor(this, R.color.Red), 9, contentTv);
        }
        cancelTv.setOnClickListener(view1 -> exitDialog.dismiss());
        confirmTv.setOnClickListener(view12 -> {
            exitDialog.dismiss();
            summitInfo();
        });
        if (ActivityManager.getInstance().isLiving(ReviseEntryCheckActivity.this)) {
            exitDialog.show();
        }
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void summitCheckInfo() {
        if (endTimesType == 0 || endAddressType == 0 || carType == 0 || mAnimalCount == 0 || earTagsType == 0 || designatedChannelType==0) {
            checkResult = 2;
            showCheckDialog(checkResult);
        } else {
            checkResult = 1;
            showCheckDialog(checkResult);
        }

    }



    private void  summitInfo(){
        showLoading("入场查验数据修改中...");
        HttpRequest.mModifyAdmissionCheck(ReviseEntryCheckActivity.this,result.mid,checkResult,mAnimalCount,binding.normalEt.getText().toString(),
                binding.abnormalEt.getText().toString(),binding.deadEt.getText().toString(),new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData statue) {

                if (statue.ErrorCode==0) {
                    summitEntryCheckLog();
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(ReviseEntryCheckActivity.this, error));
            }
        });
    }

    private void  summitEntryCheckLog(){
        EntryCheckLogBean entryCheckLogBean = new EntryCheckLogBean();
        entryCheckLogBean.CertNo = result.certNo;
        entryCheckLogBean.EntryCheckMid = result.mid;
        entryCheckLogBean.ChangeTime = DateTimeUtils.getNowTimes();
        entryCheckLogBean.Modifier = new EntryCheckLogBean.ModifierBean();
        entryCheckLogBean.Modifier.Name = UserDataSP.getInstance().getUserInfo().Result.name;
        entryCheckLogBean.Modifier.UserID = UserDataSP.getInstance().getUserInfo().Result.userId;
        entryCheckLogBean.ChangeInfo = new EntryCheckLogBean.ChangeInfoBean();
        entryCheckLogBean.ChangeInfo.NumberIsPass = mAnimalCount;
        entryCheckLogBean.ChangeInfo.ActualNumber = binding.normalEt.getText().toString();
        entryCheckLogBean.ChangeInfo.AnomalyNumber = binding.abnormalEt.getText().toString();
        entryCheckLogBean.ChangeInfo.DeathNumber = binding.deadEt.getText().toString();
        HttpRequest.upDataEntryCheckLog(ReviseEntryCheckActivity.this,entryCheckLogBean,new CallBackLis<>() {
                    @Override
                    public void onSuccess(String method, StatusData statue) {
                        hideLoading();
                        if (statue.ErrorCode==0) {
                            Objects.requireNonNull(RxToast.success(ReviseEntryCheckActivity.this, "修改成功"));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(String method, String error) {
                        hideLoading();
                        Objects.requireNonNull(RxToast.error(ReviseEntryCheckActivity.this, error));
                    }
                });
    }

    private void setTextColor(int color, int end, TextView contentTv) {
        SpannableString spannableString = new SpannableString(contentTv.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(color), 6, end, 0);
        contentTv.setText(spannableString);
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
