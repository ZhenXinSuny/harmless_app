package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.reviewassignment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.EnforcementDetailsActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.databinding.ActivityReviewAssignmentMainBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-17 14:56.
 * @Description :描述  复查指派主页
 */
public class ReviewAssignmentMainActivity extends BaseActivity<ActivityReviewAssignmentMainBinding> implements onAssignmentPersonListener {
    private final static String TAG = "lzx------》";

    private ReviewAssignmentMainAdapter reviewAssignmentMainAdapter;
    private CustomLoadingDialog mLoadingDialog;
    @Override
    protected ActivityReviewAssignmentMainBinding getBinding() {
        return ActivityReviewAssignmentMainBinding.inflate(getLayoutInflater());
    }
    public static void start(Context context) {
        Intent intent = new Intent(context, ReviewAssignmentMainActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(ReviewAssignmentMainActivity.this, LinearLayoutManager.VERTICAL, false));
        reviewAssignmentMainAdapter = new ReviewAssignmentMainAdapter(R.layout.item_review_assignment_list, ReviewAssignmentMainActivity.this);
        reviewAssignmentMainAdapter.setAssignmentPersonListener(this);
        binding.recyclerview.setAdapter(reviewAssignmentMainAdapter);

        reviewAssignmentMainAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                EnforcementDetailsActivity.start(ReviewAssignmentMainActivity.this,reviewAssignmentMainAdapter.getData(position).mid);
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

    @Override
    protected void onResume() {
        super.onResume();
        getAssignList();
    }

    @Override
    public void assignment(String Mid, int position,int assignmentStatus) {
        LogUtil.d(TAG,Mid);
        if (assignmentStatus==1){
            Objects.requireNonNull(RxToast.warning(ReviewAssignmentMainActivity.this,"您已经指派过该任务，暂无法指派"));
        }else {
            AssignmentPersonActivity.start(ReviewAssignmentMainActivity.this,Mid);
        }

    }
   //TODO:支队长问题待确认
    private void  getAssignList(){

        String AgencyMID = UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Mid;
        if (UserDataSP.getInstance().getUserInfo().Result.dependency.IsMaster ==1){
            showLoading("数据加载中...");
            HttpRequest.getAssignList(ReviewAssignmentMainActivity.this, AgencyMID, new CallBackLis<>() {
                @Override
                public void onSuccess(String method, EnforcementHomeData enforcementHomeData) {
                    hideLoading();
                    if (enforcementHomeData.status==0){
                        if (enforcementHomeData.result.size()>0){
                            binding.notDataRl.setVisibility(View.GONE);
                            reviewAssignmentMainAdapter.refreshDataList(enforcementHomeData.result);
                        }else {
                            binding.recyclerview.setVisibility(View.GONE);
                            binding.notDataRl.setVisibility(View.VISIBLE);
                        }
                    }
                }


                @Override
                public void onFailure(String method, String error) {
                    Objects.requireNonNull(RxToast.error(ReviewAssignmentMainActivity.this,error));
                }
            });
        }else {
            showIsMasterDialog(UserDataSP.getInstance().getUserInfo().Result.dependency.Dep_AgencyMID.Name);
        }

    }
    private void showIsMasterDialog(String name) {
        binding.notDataRl.setVisibility(View.VISIBLE);
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogIsMaster = new AlertDialog.Builder(this).create();
        exitDialogIsMaster.setView(view);
        exitDialogIsMaster.setCanceledOnTouchOutside(false);
        exitDialogIsMaster.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        cancelTv.setVisibility(View.GONE);
        title_tv.setText(name);
        contentTv.setText("您当前不是支队长，暂无法进行复查指派。");


        cancelTv.setOnClickListener(view12 -> exitDialogIsMaster.dismiss());
        confirmTv.setOnClickListener(v -> {
            exitDialogIsMaster.dismiss();
            finish();
                }
        );
        exitDialogIsMaster.show();
        WindowManager.LayoutParams params = exitDialogIsMaster.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialogIsMaster.getWindow().setAttributes(params);
    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(ReviewAssignmentMainActivity.this);
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
