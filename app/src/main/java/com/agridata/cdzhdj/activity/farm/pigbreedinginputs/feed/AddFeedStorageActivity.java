package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.feed;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.pigbreed.XdrFeedData;
import com.agridata.cdzhdj.data.pigbreed.feed.AddFeedStorageBean;
import com.agridata.cdzhdj.databinding.ActivityAddFeedStorageBinding;
import com.agridata.cdzhdj.utils.TimeDialogUtils;
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
 * @Date : on 2023-04-26 17:15.
 * @Description :描述 添加饲料
 */
public class AddFeedStorageActivity extends BaseActivity<ActivityAddFeedStorageBinding> implements View.OnClickListener {
    private final static String TAG = "AddFeedStorageActivity------》";

    private List<XdrFeedData.Result>  xdrBeanList = new ArrayList<>();

    private OptionsPickerView xdrOptions;
    private int xdrPos=0;
    private String xdrMid;

    private AddFeedStorageBean addFeedStorageBean;

    private CustomLoadingDialog mLoadingDialog;






    public static void start(Context context) {
        Intent intent = new Intent(context, AddFeedStorageActivity.class);
        context.startActivity(intent);
    }




    @Override
    protected ActivityAddFeedStorageBinding getBinding() {
        return ActivityAddFeedStorageBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.xdrNameEt.setOnClickListener(this);
        binding.submitBtn.setOnClickListener(this);
        binding.dateProductionEt.setOnClickListener(this);
        binding.validityEt.setOnClickListener(this);

        getXdrForTel();
        //	饲料	6102   兽药	6101
        addFeedStorageBean = new AddFeedStorageBean();
        addFeedStorageBean.DepositorUser = new AddFeedStorageBean.DepositorUserBean();
        addFeedStorageBean.DepositorUser.mid = UserDataSP.getInstance().getUserInfo().Result.userId;//TODO:此处使用mid 还是 userid 相对人没有mid
        addFeedStorageBean.DepositorUser.Name = UserDataSP.getInstance().getUserInfo().Result.name;

        addFeedStorageBean.Farm = new AddFeedStorageBean.FarmBean();
        addFeedStorageBean.GoodsType = new AddFeedStorageBean.GoodsTypeBean();
        addFeedStorageBean.GoodsType.Name = "饲料";
        addFeedStorageBean.GoodsType.key = "6102";
        addFeedStorageBean.Region = new AddFeedStorageBean.RegionBean();



    }

    @Override
    protected void initDatas() {

    }

    @Override
    public void onClick(View v) {
        if (v==binding.xdrNameEt){
            LogUtil.d("lzx--------》","12312312312");
            if (xdrBeanList.size()>0){
                setXdrUi();
            }else {
                Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this,"该手机号当前暂无备案"));
            }
        }else  if (v==binding.submitBtn){
            if (checkInfo()){
                submitData();
            }
        }else  if (v==binding.dateProductionEt){
            TimeDialogUtils.initTimePicker2(this, binding.dateProductionEt);
            TimeDialogUtils.pvTime1.show();
        }else if (v==binding.validityEt){
            TimeDialogUtils.initTimePicker2(this, binding.validityEt);
            TimeDialogUtils.pvTime1.show();
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
        addFeedStorageBean.WarehousingTime = simpleDateFormat.format(calendar.getTime());

        addFeedStorageBean.Manufacturer = binding.feedManufacturerEt.getText().toString();
        addFeedStorageBean.Enterprise = binding.feedBusinessEt.getText().toString();
        addFeedStorageBean.Brand = binding.feedBrandEt.getText().toString();
        addFeedStorageBean.Batch = binding.feedBatchEt.getText().toString();
        addFeedStorageBean.BeBornDate = binding.dateProductionEt.getText().toString();
        addFeedStorageBean.Validity = binding.validityEt.getText().toString();
        addFeedStorageBean.GoodsNumber = Integer.parseInt(binding.storageQuantityEt.getText().toString());
        addFeedStorageBean.GoodsName = binding.feedNameEt.getText().toString();
        showLoading("正在提交中...");
        HttpRequest.addFeed(AddFeedStorageActivity.this,addFeedStorageBean, new CallBackLis<StatusData>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                if (content.ErrorCode == 0) {
                   Objects.requireNonNull(RxToast.success(AddFeedStorageActivity.this,"饲料入库提交成功"));
                   finish();
                }else {
                    Objects.requireNonNull(RxToast.error(AddFeedStorageActivity.this,content.Message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(AddFeedStorageActivity.this,error));
            }
        });

    }
    private boolean checkInfo() {
        if (TextUtils.isEmpty(binding.feedManufacturerEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请输入饲料厂商"));
            return false;
        }

        if (TextUtils.isEmpty(binding.feedBusinessEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请输入饲料经营企业"));
            return false;
        }
        if (TextUtils.isEmpty(binding.feedBrandEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请输入饲料品牌"));
            return false;
        }
        if (TextUtils.isEmpty(binding.feedBatchEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请输入饲料批次"));
            return false;
        }
        if (TextUtils.isEmpty(binding.dateProductionEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请选择生产日期"));
            return false;
        }
        if (TextUtils.isEmpty(binding.validityEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请输入有效期"));
            return false;
        }
        if (TextUtils.isEmpty(binding.storageQuantityEt.getText().toString())) {
            Objects.requireNonNull(RxToast.warning(AddFeedStorageActivity.this, "请输入入库数量（kg）"));
            return false;
        }

        return  true;
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
            addFeedStorageBean.Farm.Name = xdrBeanList.get(options1).displayName;
            addFeedStorageBean.Farm.mid = String.valueOf(xdrBeanList.get(options1).mid);
            binding.regionNameEt.setText("");
            binding.regionNameEt.setText(xdrBeanList.get(options1).region.regionFullName);
            addFeedStorageBean.Region.id = xdrBeanList.get(options1).region.iD;
            addFeedStorageBean.Region.RI1 = xdrBeanList.get(options1).region.rI1;
            addFeedStorageBean.Region.RI2 = xdrBeanList.get(options1).region.rI2;
            addFeedStorageBean.Region.RI3 = xdrBeanList.get(options1).region.rI3;
            addFeedStorageBean.Region.RI4 = xdrBeanList.get(options1).region.rI4;
            addFeedStorageBean.Region.RI5 = xdrBeanList.get(options1).region.rI5;
            addFeedStorageBean.Region.RegionCode = xdrBeanList.get(options1).region.regionCode;
            addFeedStorageBean.Region.RegionName = xdrBeanList.get(options1).region.regionName;
            addFeedStorageBean.Region.RegionLevel = xdrBeanList.get(options1).region.regionLevel;
            addFeedStorageBean.Region.RegionFullName = xdrBeanList.get(options1).region.regionFullName;
            addFeedStorageBean.Region.RegionParentID = xdrBeanList.get(options1).region.regionParentID;
            
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
        HttpRequest.getMobileXdr(AddFeedStorageActivity.this, UserDataSP.getInstance().getUserInfo().Result.mobile, new CallBackLis<XdrFeedData>() {
            @Override
            public void onSuccess(String method, XdrFeedData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                        if (content.result.size()>0){
                            xdrBeanList = content.result;
                        }

                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });

    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(AddFeedStorageActivity.this);
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
