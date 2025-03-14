package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.reviewassignment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.RealmDetailsActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.law.AgencyData;
import com.agridata.cdzhdj.data.law.AgencyPersonData;
import com.agridata.cdzhdj.data.law.AssignmentBean;
import com.agridata.cdzhdj.data.law.EnforcementDetailBean;
import com.agridata.cdzhdj.databinding.ActivityAssignmentPersonBinding;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-17 14:56.
 * @Description :描述  复查指派人员
 */
public class AssignmentPersonActivity extends BaseActivity<ActivityAssignmentPersonBinding> implements View.OnClickListener {
    private final static String TAG = "lzx------》";

    private CustomLoadingDialog mLoadingDialog;


    private OptionsPickerView AssigningAgencyOptions;
    private int AssigningAgencyPos = 0;
    public String AssigningAgencyMid;


    private OptionsPickerView AssignPersonOptions;
    private int AssignPersonPos = 0;
    private String AssignPersonMid;

    private BottomSheetBehavior mBottomSheetBehavior;
    private String inspectionFieldName;
    private final AssignmentBean assignmentBean = new AssignmentBean();
    private String mMid;



    @Override
    protected ActivityAssignmentPersonBinding getBinding() {
        return ActivityAssignmentPersonBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        this.mMid = this.getIntent().getStringExtra("Mid");
    }
    public static void start(Context context,String Mid) {
        Intent intent = new Intent(context, AssignmentPersonActivity.class);
        intent.putExtra("Mid", Mid);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        assignmentBean.AssigningAgency = new AssignmentBean.AssigningAgencyBean();
        assignmentBean.AssignPerson = new AssignmentBean.AssignPersonBean();

        binding.assigningAgencyEt.setOnClickListener(this);//指派机构
        binding.assignPersonnelEt.setOnClickListener(this);//指派人员
        binding.submitBtn.setOnClickListener(this);//提交


        mBottomSheetBehavior = BottomSheetBehavior.from(binding.llBottomSheet);
        ViewGroup.LayoutParams para1;
        para1 = binding.llBottomSheet.getLayoutParams();
        int height2 = (int) (getResources().getDisplayMetrics().heightPixels * 0.6);
        para1.height=height2;
        binding.llBottomSheet.setLayoutParams(para1);


        binding.detailsBtn.setOnClickListener(v -> {
            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return;
            }

            if (mBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                return;
            }
        });



        binding.lingyuDetailsBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmDetailsActivity.start(AssignmentPersonActivity.this,inspectionFieldName,mMid);
            }
        });
    }

    @Override
    protected void initDatas() {
        getEnforcementDetails();
    }

    @Override
    public void onClick(View v) {
        if (v == binding.assigningAgencyEt) {
            showLoading("正在加载中...");
            getAgencyInfo();
        } else if (v == binding.assignPersonnelEt) {
            if (!TextUtils.isEmpty(binding.assigningAgencyEt.getText().toString())) {
                if (!TextUtils.isEmpty(AssigningAgencyMid)) {
                    showLoading("指派人员加载中...");
                    getAgencyPersonInfo(AssigningAgencyMid);
                }
            } else {
                Objects.requireNonNull(RxToast.warning(AssignmentPersonActivity.this, "请先选择协助执法单位"));
            }
        }else if (v==binding.submitBtn){
            SubmitData();
        }
    }

    /**
     * 提交数据
     */
    private void SubmitData(){
        if (checkInfo()) {
            LogUtil.d(TAG,"最终的提交数据"+assignmentBean.toString());
            assignmentBean.AssignmentStatus = 1;
            assignmentBean.Mid = mMid;
            mCommitInfo(assignmentBean);
        }
    }
    private boolean checkInfo() {
        if (TextUtils.isEmpty(assignmentBean.AssigningAgency.Mid)) {
            Objects.requireNonNull(RxToast.warning(AssignmentPersonActivity.this, "请选择指派机构"));
            return false;
        }
        if (TextUtils.isEmpty(assignmentBean.AssignPerson.Mid)) {
            Objects.requireNonNull(RxToast.warning(AssignmentPersonActivity.this, "请选择指派人员"));
            return false;
        }

        return  true;
    }

    private void mCommitInfo(AssignmentBean assignmentBean){
        showLoading("数据提交中...");
        HttpRequest.updataAssignment(AssignmentPersonActivity.this, assignmentBean , new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData status) {
                hideLoading();
                if (status.ErrorCode==0){
                    Objects.requireNonNull(RxToast.success(AssignmentPersonActivity.this,"提交成功")) ;
                    finish();
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(AssignmentPersonActivity.this,error));
            }
        });
    }
    /**
     * 获取机构人员
     *
     * @param AgencyMid
     */
    private void getAgencyPersonInfo(String AgencyMid) {
        HttpRequest.getAgencyPersonData(AssignmentPersonActivity.this, AgencyMid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, AgencyPersonData agencyPersonData) {
                hideLoading();
                if (agencyPersonData.status == 0) {
                    if (agencyPersonData.result.size() > 0) {
                        setJointlyUnitPersonUi(agencyPersonData.result);
                    } else {
                        Objects.requireNonNull(RxToast.warning(AssignmentPersonActivity.this, "当前协助执法单位下暂无人员"));
                    }

                }
                LogUtil.d("lzx---------》", agencyPersonData.toString());
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(AssignmentPersonActivity.this, error));
            }
        });
    }

    //选择执法单位人员
    private void setJointlyUnitPersonUi(List<AgencyPersonData.Result> agencyPersonDataList) {
        List<String> listString = new ArrayList<>();
        for (AgencyPersonData.Result agencyData : agencyPersonDataList) {
            listString.add(agencyData.name);
        }
        AssignPersonOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.assignPersonnelEt.setText(agencyPersonDataList.get(options1).name);
            AssignPersonPos = options1;
            AssignPersonMid = agencyPersonDataList.get(options1).mid;

            //协办人员
            assignmentBean.AssignPerson.Name = agencyPersonDataList.get(options1).name;
            assignmentBean.AssignPerson.Mid = agencyPersonDataList.get(options1).mid;
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    final TextView title_tv = v.findViewById(R.id.title_tv);
                    title_tv.setText("协办人员选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        AssignPersonOptions.returnData();
                        AssignPersonOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> AssignPersonOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(AssignPersonPos)
                .build();
        AssignPersonOptions.setPicker(listString);//添加数据
        AssignPersonOptions.show();
    }


    /**
     * 获取机构
     */
    private void getAgencyInfo() {
        HttpRequest.getAgencyData(AssignmentPersonActivity.this, new CallBackLis<AgencyData>() {
            @Override
            public void onSuccess(String method, AgencyData agencyData) {
                hideLoading();
                if (agencyData.status == 0) {
                    setAssigningAgencyUi(agencyData.result);
                }
                LogUtil.d("lzx---------》", agencyData.toString());
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(AssignmentPersonActivity.this, error));
            }
        });
    }


    private void setAssigningAgencyUi(List<AgencyData.Result> agencyDataList) {
        List<String> listString = new ArrayList<>();
        for (AgencyData.Result agencyData : agencyDataList) {
            listString.add(agencyData.name);
        }
        AssigningAgencyOptions = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.assigningAgencyEt.setText(agencyDataList.get(options1).name);
            AssigningAgencyPos = options1;
            AssigningAgencyMid = agencyDataList.get(options1).mid;

            assignmentBean.AssigningAgency.Name = agencyDataList.get(options1).name;
            assignmentBean.AssigningAgency.Mid = agencyDataList.get(options1).mid;
        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                    final TextView title_tv = v.findViewById(R.id.title_tv);
                    title_tv.setText("指派机构");
                    tvSubmit.setOnClickListener(v1 -> {
                        AssigningAgencyOptions.returnData();
                        AssigningAgencyOptions.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> AssigningAgencyOptions.dismiss());
                })
                .isDialog(false)
                .setContentTextSize(16)//滚轮文字大小
                .setLineSpacingMultiplier(3.0f)
                .setItemVisibleCount(9)
                .setSelectOptions(AssigningAgencyPos)
                .build();
        AssigningAgencyOptions.setPicker(listString);//添加数据
        AssigningAgencyOptions.show();
    }


    private void getEnforcementDetails() {
        HttpRequest.getEnforcementDetails(this, mMid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementDetailBean content) {
                if (content.status==0){
                    mSetBaseInfo(content);
                }
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(AssignmentPersonActivity.this,error));
            }
        });
    }


    private void mSetBaseInfo(EnforcementDetailBean content){
        EnforcementDetailBean.Result result = content.result;
        inspectionFieldName = result.inspectionField.name;

        binding.zfzbdwTv.setText(result.sponsorEnforcementUnit.name);
        binding.zbryTv.setText(result.organizer.name);
        binding.xbdwTv.setText(result.coOrganizeEnforcementUnits.name);
        binding.xbryTv.setText(result.coOrganizer.name);
        binding.jclyTv.setText(result.inspectionField.name);
        binding.dwmcTv.setText(result.companyName);
        binding.fzrTv.setText(result.legalRepresentative);
        binding.telTv.setText(result.tel);
        binding.quhuTv.setText(result.region.regionFullName);
        binding.jcjgTv.setText(result.inspectionResult.name);
        binding.xiangxiTv.setText(result.detailedAddress);
        if (!TextUtils.isEmpty(result.otherProblems)){
            binding.qtwtTv.setText(result.otherProblems	);
        }else {
            binding.qtwtTv.setText("暂无");
        }

        Glide.with(AssignmentPersonActivity.this)
                .load(UrlUtils.pic_url +result.frontViewOfTheInspectedUnit)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwzmIv);

        if (result.inspectionResult!=null){
            if ("4".equals(result.inspectionResult.id)){
                binding.fuchaLl.setVisibility(View.VISIBLE);
                if (result.reviewStatus==0){
                    binding.fuchazhuangtaiTv.setText("未复查");
                    binding.fuchazhuangtaiTv.setBackground(getDrawable(R.drawable.textview_border_no_status));
                    binding.fuchajigouLl.setVisibility(View.GONE);
                    binding.fucharyLl.setVisibility(View.GONE);
                    if (result.assignmentStatus==1){
                        binding.fuchajigouLl.setVisibility(View.VISIBLE);
                        binding.fucharyLl.setVisibility(View.VISIBLE);
                        binding.fczfjgTv.setText(result.assigningAgency.Name);
                        binding.fczfryTv.setText(result.assignPerson.Name);
                    }
                }else {
                    binding.fuchazhuangtaiTv.setText("已复查");
                    binding.fuchazhuangtaiTv.setBackground(getDrawable(R.drawable.textview_border));
                    binding.fuchajigouLl.setVisibility(View.VISIBLE);
                    binding.fucharyLl.setVisibility(View.VISIBLE);
                    binding.fczfjgTv.setText(result.assigningAgency.Name);
                    binding.fczfryTv.setText(result.assignPerson.Name);
                }
                binding.zhenggaitimeTv.setText(result.deadlineForRectification);
            }else {
                binding.fuchaLl.setVisibility(View.GONE);
            }
        }
        Glide.with(AssignmentPersonActivity.this)
                .load(UrlUtils.pic_url +result.signatureOfOrganizer)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.zbryIv);

        Glide.with(AssignmentPersonActivity.this)
                .load(UrlUtils.pic_url +result.unitUnderInspectionSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.bjcdwfzrIv);

        Glide.with(AssignmentPersonActivity.this)
                .load(UrlUtils.pic_url +result.eyewitnessSignature)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(binding.jzrIv);
    }


    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(AssignmentPersonActivity.this);
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
