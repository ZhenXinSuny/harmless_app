package com.agridata.cdzhdj.activity.harmless.record;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.CollectedBean;
import com.agridata.cdzhdj.databinding.FragmentYiRecordBinding;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 18:05.
 * @Description :描述
 */
public class BoRecordFragment extends BaseFragment<FragmentYiRecordBinding> {
    private RecordInfoAdapter recordInfoAdapter;
    private String startTime;
    private String endTime;
    private CustomLoadingDialog mLoadingDialog;


    public static BoRecordFragment newInstance(String startTime, String endTime) {
        BoRecordFragment fragment = new BoRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString("startTime", startTime);
        bundle.putString("endTime", endTime);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void performNetworkRequest(String mStartTime, String mEndTime) {
        // 进行网络请求
        // 使用 startTime 和 endTime 发起网络请求
        LogUtil.d("lzx------------》", "startTimes" + startTime);
        LogUtil.d("lzx------------》", "endTime" + endTime);
        startTime = mStartTime;
        endTime = mEndTime;
        if (NetworkUtils.isNetAvailable(getActivity())) {
            getNetApply();
        }
    }

    @Override
    protected void initView() {
        initLoadingView();
        assert getArguments() != null;
        this.startTime = getArguments().getString("startTime");
        this.endTime = getArguments().getString("endTime");


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        recordInfoAdapter = new RecordInfoAdapter(R.layout.item_record, requireContext());
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            getNetApply();
        });


        binding.recyclerView.setAdapter(recordInfoAdapter);


    }

    @Override
    protected void initDatas() {
        if (NetworkUtils.isNetAvailable(getActivity())) {
            getNetApply();
        }
    }


    private void getNetApply() {

        showLoading("网络数据获取中...");
        HttpRequest.getCollectedInfo(requireActivity(), startTime + " " + "00:00:00", endTime + " " + "23:59:59", 2, RoleSP.getInstance().getRoleInfo().harmlessUser.factory.Mid, RoleSP.getInstance().getRoleInfo().harmlessUser.userId, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, CollectedBean content) {
                hideLoading();
                binding.refreshLayout.finishRefresh();
                if (!content.result.pageItems.isEmpty()) {
                    binding.refreshLayout.setVisibility(View.VISIBLE);
                    binding.notDataRl.setVisibility(View.GONE);
                    recordInfoAdapter.refreshDataList(content.result.pageItems);
                } else {
                    binding.refreshLayout.setVisibility(View.GONE);
                    binding.notDataRl.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                ToastUtil.showToast(requireActivity(), error);

            }
        });
    }


    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(requireActivity());
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
