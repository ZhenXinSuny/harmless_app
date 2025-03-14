package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.goodsout;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusMapBean;
import com.agridata.cdzhdj.data.goodout.GoodOutBean;
import com.agridata.cdzhdj.data.pigbreed.XdrFeedData;
import com.agridata.cdzhdj.data.pigbreed.feed.FeedStorageListData;
import com.agridata.cdzhdj.data.pigbreed.goods.AddGoodsBean;
import com.agridata.cdzhdj.databinding.ActivityAddGoodsOutBinding;
import com.agridata.cdzhdj.utils.GsonUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.lzx.utils.RxToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 16:15.
 * @Description :描述
 */
public class AddGoodsOutActivity extends BaseActivity<ActivityAddGoodsOutBinding> implements View.OnClickListener{

    private AddGoodsBean  addGoodsBean;

    private OptionsPickerView xdrOptions;
    private int xdrPos=0;
    private String xdrMid;
    private List<XdrFeedData.Result> xdrBeanList = new ArrayList<>();
    private String userId;
    private CustomLoadingDialog mLoadingDialog;


    private List<FeedStorageListData.Result>  resultList = new ArrayList<>();
    private OptionsPickerView VeterinaryDrugOptions;

    private GoodOutBean  goodOutBean = new GoodOutBean();

    private String mid;


    public static void start(Context context) {
        Intent intent = new Intent(context, AddGoodsOutActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityAddGoodsOutBinding getBinding() {
        return ActivityAddGoodsOutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        userId = UserDataSP.getInstance().getUserInfo().Result.userId;
        //getXdrForTel();
        binding.xdrNameEt.setOnClickListener(this);
        binding.submitBtn.setOnClickListener(this);
        binding.brandEt.setOnClickListener(this);


        addGoodsBean = new AddGoodsBean();



        goodOutBean.UseUser = new GoodOutBean.UseUserBean();
        goodOutBean.UseUser.mid = UserDataSP.getInstance().getUserInfo().Result.userId;//TODO:此处使用mid 还是 userid 相对人没有mid
        goodOutBean.UseUser.Name = UserDataSP.getInstance().getUserInfo().Result.name;

        addGoodsBean.useUser = GsonUtil.toJson(goodOutBean.UseUser);

        binding.singleHeadUsageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.sendEmptyMessage(1);
            }
        });
        binding.numberAnimalsUsedEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                handler.sendEmptyMessage(1);
            }
        });
    }

    private final Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {      //判断标志位
                case 1:
                    if (!TextUtils.isEmpty(binding.numberAnimalsUsedEt.getText().toString()) &&  !TextUtils.isEmpty(binding.singleHeadUsageEt.getText().toString())){
                        int  s = Integer.parseInt(binding.numberAnimalsUsedEt.getText().toString());
                        int  s1 = Integer.parseInt(binding.singleHeadUsageEt.getText().toString());
                        int s2 = s1*s;
                        binding.totalUsageEt.setText(s2+"");

                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initDatas() {

    }
    @Override
    public void onClick(View v) {
        if (v==binding.xdrNameEt){
            getXdrForTel();
        }else  if (v==binding.submitBtn){
            if (checkInfo()){
                submitData();
            }
        }else if (v==binding.brandEt){
                getStorageListData();

        }
    }

    private void  submitData(){
        //指定日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        //日历对象
        Calendar calendar = Calendar.getInstance();
        //获取当前日期
        calendar.setTime(new Date());
        System.out.println("当前时间：" + simpleDateFormat.format(calendar.getTime()));
        addGoodsBean.useDate = simpleDateFormat.format(calendar.getTime());
        addGoodsBean.goodsName = binding.brandEt.getText().toString();
        addGoodsBean.batch = binding.batchEt.getText().toString();
        addGoodsBean.singleUseNumber = Integer.parseInt(binding.singleHeadUsageEt.getText().toString());
        addGoodsBean.amount = Integer.parseInt(binding.numberAnimalsUsedEt.getText().toString());
        addGoodsBean.totalUsage = Integer.parseInt(binding.totalUsageEt.getText().toString());
        addGoodsBean.mid = mid;
        showLoading("正在提交中...");
        HttpRequest.addGoodsOut(AddGoodsOutActivity.this,addGoodsBean, new CallBackLis<StatusMapBean>() {
            @Override
            public void onSuccess(String method, StatusMapBean content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (content.code == 200) {
                    Objects.requireNonNull(RxToast.success(AddGoodsOutActivity.this,"兽药出库提交成功"));
                    finish();
                }else {
                    Objects.requireNonNull(RxToast.error(AddGoodsOutActivity.this,content.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(AddGoodsOutActivity.this,error));
            }
        });

    }

    private void  getStorageListData(){
        showLoading("数据加载中...");
        HttpRequest.getFeedStorageFarmList(AddGoodsOutActivity.this, userId,xdrMid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, FeedStorageListData content) {
                hideLoading();
                if (content.errorCode==0){
                    if (content.result.size()>0){
                        resultList = content.result;
                        setVeterinaryDrugManufacturerUi();
                    }else {
                        Objects.requireNonNull( RxToast.error(AddGoodsOutActivity.this,"暂无物品出库厂商"));
                    }
                }else {
                    Objects.requireNonNull( RxToast.error(AddGoodsOutActivity.this,content.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull( RxToast.error(AddGoodsOutActivity.this,error));
            }
        });

    }
    //兽药厂商
    private void setVeterinaryDrugManufacturerUi(){
        List<String> listString = new ArrayList<>();

        for (FeedStorageListData.Result result : resultList) {
            listString.add(result.goodsName);
        }
        LogUtil.d("lzx----------》",listString.toString());
        VeterinaryDrugOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.brandEt.setText(resultList.get(options1).goodsName);
            binding.drugBrandEt.setText(resultList.get(option2).brand);

            mid = resultList.get(option2).mid;
            FeedStorageListData.Result result = resultList.get(options1);
            setInfo(result);
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("兽药名称选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        VeterinaryDrugOptions.returnData();
                        VeterinaryDrugOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> VeterinaryDrugOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(0)
                .build();
        VeterinaryDrugOptions.setPicker(listString);//添加数据
        VeterinaryDrugOptions.show();
    }

    private void  setInfo( FeedStorageListData.Result result){
        binding.batchEt.setText(result.batch);
       binding.storageQuantityEt.setText(result.goodsNumber +"");
    }
    //相对人选择
    private void setXdrUi(){
        List<String> listString = new ArrayList<>();

        for (XdrFeedData.Result data : xdrBeanList) {
            listString.add(data.displayName);
        }

        xdrOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.xdrNameEt.setText(xdrBeanList.get(options1).displayName);
            xdrMid = xdrBeanList.get(options1).mid;
            xdrPos = options1;

            goodOutBean.Farm = new GoodOutBean.FarmBean();
            goodOutBean.Farm.Name = xdrBeanList.get(options1).displayName;
            goodOutBean.Farm.mid = String.valueOf(xdrBeanList.get(options1).mid);

            addGoodsBean.farm = GsonUtil.toJson(goodOutBean.Farm);


            binding.regionNameEt.setText("");
            binding.regionNameEt.setText(xdrBeanList.get(options1).region.regionFullName);

            goodOutBean.Region = new GoodOutBean.RegionBean();
            goodOutBean.Region.id = xdrBeanList.get(options1).region.iD;
            goodOutBean.Region.RI1 = xdrBeanList.get(options1).region.rI1;
            goodOutBean.Region.RI2 = xdrBeanList.get(options1).region.rI2;
            goodOutBean.Region.RI3 = xdrBeanList.get(options1).region.rI3;
            goodOutBean.Region.RI4 = xdrBeanList.get(options1).region.rI4;
            goodOutBean.Region.RI5 = xdrBeanList.get(options1).region.rI5;
            goodOutBean.Region.RegionCode = xdrBeanList.get(options1).region.regionCode;
            goodOutBean.Region.RegionName = xdrBeanList.get(options1).region.regionName;
            goodOutBean.Region.RegionLevel = xdrBeanList.get(options1).region.regionLevel;
            goodOutBean.Region.RegionFullName = xdrBeanList.get(options1).region.regionFullName;
            goodOutBean.Region.RegionParentID = xdrBeanList.get(options1).region.regionParentID;

            addGoodsBean.region = GsonUtil.toJson(goodOutBean.Region);


        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit =  v.findViewById(R.id.tv_finish);
                    final TextView title_tv =  v.findViewById(R.id.title_tv);
                    title_tv.setText("养殖场户选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        xdrOptions.returnData();
                        xdrOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> xdrOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setSelectOptions(xdrPos)
                .build();
        xdrOptions.setPicker(listString);//添加数据
        xdrOptions.show();
    }
    private  void  getXdrForTel(){
        showLoading("数据加载中...");
        HttpRequest.getMobileXdr(AddGoodsOutActivity.this, UserDataSP.getInstance().getUserInfo().Result.mobile, new CallBackLis<XdrFeedData>() {
            @Override
            public void onSuccess(String method, XdrFeedData content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (content.status == 0) {
                    if (content.result.size()>0){
                        xdrBeanList = content.result;
                        setXdrUi();
                    }else {
                        Objects.requireNonNull( RxToast.warning(AddGoodsOutActivity.this,"当前暂无养殖场户"));
                    }

                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull( RxToast.error(AddGoodsOutActivity.this,error));
            }
        });

    }


    private boolean checkInfo() {
        if (TextUtils.isEmpty(binding.brandEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddGoodsOutActivity.this, "请选择兽药名称"));
            return false;
        }

        if (TextUtils.isEmpty(binding.singleHeadUsageEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddGoodsOutActivity.this, "请输入单头使用量(克/毫升)"));
            return false;
        }
        if (TextUtils.isEmpty(binding.numberAnimalsUsedEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddGoodsOutActivity.this, "请输入使用动物数量（头）"));
            return false;
        }

        if (TextUtils.isEmpty(binding.totalUsageEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddGoodsOutActivity.this, "请输入总使用量（克/毫升）"));
            return false;
        }
        return  true;
    }




    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(AddGoodsOutActivity.this);
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
