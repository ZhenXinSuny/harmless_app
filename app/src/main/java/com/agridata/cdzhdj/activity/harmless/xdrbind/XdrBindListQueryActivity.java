package com.agridata.cdzhdj.activity.harmless.xdrbind;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.harmless.xdrbind.adapter.XdrBindListQueryAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.StatusMeBean;
import com.agridata.cdzhdj.data.whh.FarmXdrInfoListByUserIdAndMobileBean;
import com.agridata.cdzhdj.data.xdrbind.BindXdrUserIDForMobileBean;
import com.agridata.cdzhdj.data.xdrbind.LegalPersonSuccessBean;
import com.agridata.cdzhdj.databinding.ActivityXdrBindListQueryBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.StrUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-02-28 13:57.
 * @Description :描述
 */
public class XdrBindListQueryActivity extends BaseActivity<ActivityXdrBindListQueryBinding> {
    private XdrBindListQueryAdapter xdrBindListQueryAdapter;
    private CustomLoadingDialog mLoadingDialog;


    private List<String> mXdrMidList;

    private List<FarmXdrInfoListByUserIdAndMobileBean.Data.BindFarm> mBindResult;

    /**
     * 用户类型（1：自然人、2：法人
     */
    private int UserType = 1;
    private MyCountDownTimer mCountDownTimer;


    private String XdrMid;

    public static void start(Context context) {
        Intent intent = new Intent(context, XdrBindListQueryActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityXdrBindListQueryBinding getBinding() {
        return ActivityXdrBindListQueryBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
        initLoadingView();
        mXdrMidList = new ArrayList<>();
        mBindResult = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        xdrBindListQueryAdapter = new XdrBindListQueryAdapter(R.layout.item_immune_xdr, this);
        binding.recyclerView.setAdapter(xdrBindListQueryAdapter);

        binding.inquireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(binding.searchQuarantineEt.getText().toString())) {
                    getXdrBindInfo();
                } else {
                    Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入备案手机号进行查询~"));
                }
            }
        });


        xdrBindListQueryAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                FarmXdrInfoListByUserIdAndMobileBean.Data.FarmList data = xdrBindListQueryAdapter.getData(position);

                XdrMid = data.mid;
                if (data.isBind) {
                    Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "该条备案数据已绑定~"));
                } else {
//                    if (data.isNaturalLegal) {
//                        showBindDialog(data.mobile);
//                    } else {
//                        showPerfectInformationDialog(data.mid);
//                    }
                    showBindDialog(data.mobile);
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

    }

    @Override
    protected void initDatas() {

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


    private void getXdrBindInfo() {
        showLoading("正在查询中...");
        mBindResult.clear();
        HttpRequest.getXdrBindInfoNew(this, UserDataSP.getInstance().getUserInfo().Result.userId, binding.searchQuarantineEt.getText().toString(), new CallBackLis<>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, FarmXdrInfoListByUserIdAndMobileBean content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (content.code == 200) {
                    if (content.data.farmList.size() > 0) {
                        mBindResult = content.data.bindFarm;
                        binding.notDataRl.setVisibility(View.GONE);
                        xdrBindListQueryAdapter.refreshDataList(content.data.farmList);
                    } else {
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        Objects.requireNonNull(RxToast.error(XdrBindListQueryActivity.this, "未查询到当前手机号的备案信息，请先去完成备案操作~"));
                    }
                } else {
                    Objects.requireNonNull(RxToast.error(XdrBindListQueryActivity.this, content.msg));
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }



    private void showBindDialog(String phone) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        title_tv.setText("绑定操作");
        contentTv.setText("您确定要绑定该条备案信息吗？");

        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
                    exitDialogLoginOut.dismiss();
                    setSmsBind(phone);
                }
        );
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    private void showPerfectInformationDialog(String XdrMid) {
        View view = getLayoutInflater().inflate(R.layout.dialog_per_info, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        RadioGroup radioGroup_weight = view.findViewById(R.id.radioGroup_weight);
        RadioButton natural_person_rb = view.findViewById(R.id.natural_person_rb);
        RadioButton enterprise_rb = view.findViewById(R.id.enterprise_rb);
        LinearLayout legal_person_Ll = view.findViewById(R.id.legal_person_Ll);
        LinearLayout credit_code_Ll = view.findViewById(R.id.credit_code_Ll);
        EditText name_et = view.findViewById(R.id.name_et);
        EditText phone_et = view.findViewById(R.id.phone_et);
        EditText idcard_et = view.findViewById(R.id.idcard_et);
        EditText legal_person_et = view.findViewById(R.id.legal_person_et);
        EditText credit_code_et = view.findViewById(R.id.credit_code_et);
        Button perfect_information_btn = view.findViewById(R.id.perfect_information_btn);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialogLoginOut.dismiss();
            }
        });

        radioGroup_weight.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.natural_person_rb) {
                legal_person_Ll.setVisibility(View.GONE);
                credit_code_Ll.setVisibility(View.GONE);
                UserType = 1;
            } else {
                legal_person_Ll.setVisibility(View.VISIBLE);
                credit_code_Ll.setVisibility(View.VISIBLE);
                UserType = 2;
            }
        });

        perfect_information_btn.setOnClickListener(v -> {
            if (checkInfo(name_et, phone_et, idcard_et, legal_person_et, credit_code_et)) {
                HttpRequest.mCreateNaturalLegal(XdrBindListQueryActivity.this, UserDataSP.getInstance().getUserInfo().Result.userId, XdrMid
                        , phone_et.getText().toString(), name_et.getText().toString(), idcard_et.getText().toString(), UserType, legal_person_et.getText().toString(), credit_code_et.getText().toString(), new CallBackLis<LegalPersonSuccessBean>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void onSuccess(String method, LegalPersonSuccessBean content) {
                                LogUtil.d("lzx---------》", content.toString());
                                if (content.code == 200) {
                                    exitDialogLoginOut.dismiss();
                                    RxToast.success(XdrBindListQueryActivity.this, "信息完善成功~");

                                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                                        setSmsBind(content.data.mobile);
                                    }, 1000);

                                } else {
                                    RxToast.error(XdrBindListQueryActivity.this, content.msg);
                                }
                            }

                            @SuppressLint("CheckResult")
                            @Override
                            public void onFailure(String method, String error) {
                                RxToast.error(XdrBindListQueryActivity.this, error);
                            }
                        });
            }
        });


        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }


    private boolean checkInfo(EditText name, EditText phone, EditText IDCard, EditText legal_person, EditText credit_code) {
        if (TextUtils.isEmpty(name.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入姓名"));
            return false;
        }
        if (TextUtils.isEmpty(phone.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入手机号"));
            return false;
        }
        if (TextUtils.isEmpty(IDCard.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入身份证号"));
            return false;
        }
        if (UserType == 2) {
            if (TextUtils.isEmpty(legal_person.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入法人"));
                return false;
            }
            if (TextUtils.isEmpty(credit_code.getText().toString())) {
                Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入信用代码"));
                return false;
            }
        }
        return true;
    }

    private void setSmsBind(String mobile) {
        View view = getLayoutInflater().inflate(R.layout.dialog_sms_bind, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(this).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        EditText account_et = view.findViewById(R.id.account_et);
        EditText verification_et = view.findViewById(R.id.verification_et);

        account_et.setText(mobile);
        Button verify_btn = view.findViewById(R.id.ver_btn);
        Button send_sms_btn = view.findViewById(R.id.send_sms_btn);
        this.mCountDownTimer = new MyCountDownTimer(60 * 1000, 1000, send_sms_btn);
        //发送验证码
        send_sms_btn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(account_et.getText().toString())) {
                if (this.mCountDownTimer != null) {
                    this.mCountDownTimer.start();
                    getSendSms(account_et.getText().toString());
                }
            } else {
                Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入手机号"));
            }
        });

        verify_btn.setOnClickListener(v -> {
            if (bindCheckInfo(account_et,verification_et)) {
                exitDialogLoginOut.dismiss();
                addBindUserXdr(XdrMid,account_et.getText().toString());
            }
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    private boolean bindCheckInfo(EditText accountEt,EditText verification_et) {
        String account = accountEt.getText().toString().trim();
        String verificationCode = verification_et.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入手机号"));
            return false;
        }
        if (!TextUtils.isEmpty(account) && !StrUtil.isMobileNumber(account)) {
            Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入正确手机号"));
            return false;
        }
        if (TextUtils.isEmpty(verificationCode) ) {
            Objects.requireNonNull(RxToast.warning(XdrBindListQueryActivity.this, "请输入短信验证码"));
            return false;
        }
        return true;
    }

    private void getSendSms(String account) {
        showLoading("获取短信验证...");
        HttpRequest.getSendSms(XdrBindListQueryActivity.this, account, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.Status == 0) {
                    hideLoading();
                    Objects.requireNonNull(RxToast.success(XdrBindListQueryActivity.this, "短信验证码发送成功,注意查收"));
                } else {
                    hideLoading();
                    Objects.requireNonNull(RxToast.error(XdrBindListQueryActivity.this, content.Message));
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(XdrBindListQueryActivity.this, error));
            }
        });
    }

    private void addBindUserXdr(String XdrMid,String phone) {
        showLoading("绑定中...");
        BindXdrUserIDForMobileBean bindXdrUserIDForMobileBean = new  BindXdrUserIDForMobileBean();
        bindXdrUserIDForMobileBean.command="BindXdrUserIDForMobile";

        bindXdrUserIDForMobileBean.params = new BindXdrUserIDForMobileBean.ParamsBean();
        bindXdrUserIDForMobileBean.params.Mobile=phone;
        bindXdrUserIDForMobileBean.params.UserId=UserDataSP.getInstance().getUserInfo().Result.userId;
        bindXdrUserIDForMobileBean.params.XDRMid=XdrMid;

        HttpRequest.BindXdrUserIDForMobile(XdrBindListQueryActivity.this, bindXdrUserIDForMobileBean, new CallBackLis<StatusMeBean>() {
            @Override
            public void onSuccess(String method, StatusMeBean content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (content.code == 200) {
                    Objects.requireNonNull(RxToast.success(XdrBindListQueryActivity.this, "绑定成功~"));
                    finish();
                } else {
                    Objects.requireNonNull(RxToast.error(XdrBindListQueryActivity.this, content.msg));
                }
            }
            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(XdrBindListQueryActivity.this, error));
            }
        });
    }

    /**
     * 倒计时器
     */
    private class MyCountDownTimer extends CountDownTimer {

        private TextView mTextView;

        /**
         * 构造方法
         *
         * @param millisInFuture    倒计时的总时间, 单位ms
         * @param countDownInterval 倒计时的间隔时间, 单位ms
         * @param textView          倒计时的view
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView textView) {
            super(millisInFuture, countDownInterval);
            this.mTextView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            @SuppressLint("StringFormatMatches")
            String content = String.format(getString(R.string.sms_time), (millisUntilFinished / 1000));
            this.mTextView.setEnabled(false);
            this.mTextView.setTextColor(ContextCompat.getColor(XdrBindListQueryActivity.this, R.color.white));
            this.mTextView.setText(content);
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void onFinish() {
            this.mTextView.setEnabled(true);
            this.mTextView.setTextColor(ContextCompat.getColor(XdrBindListQueryActivity.this, R.color.white));
            this.mTextView.setText("重新发送");
        }
    }

}