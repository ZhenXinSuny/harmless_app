package com.agridata.cdzhdj.activity.harmless.collection;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.databinding.FragmentApplyBinding;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.lzx.utils.RxToast;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 10:57.
 * @Description :描述
 */
public class BoApplyFragment extends BaseFragment<FragmentApplyBinding> {
    private ApplyInfoAdapter applyInfoAdapter;
    private String startTime;
    private String endTime;
    private CustomLoadingDialog mLoadingDialog;




    public void performNetworkRequest(String mStartTime, String mEndTime) {
        startTime = mStartTime;
        endTime = mEndTime;
        if (NetworkUtils.isNetAvailable(getActivity())) {
            getNetApply();
        } else {
            binding.refreshLayout.setEnableRefresh(false);
            Objects.requireNonNull(RxToast.warning(requireActivity(), "当前无法连接网络，请检查网络设置是否正常"));
        }
    }
    public static BoApplyFragment newInstance(String startTime, String endTime) {
        BoApplyFragment fragment = new BoApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("startTime", startTime);
        bundle.putString("endTime", endTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        initLoadingView();
        assert getArguments() != null;
        this.startTime = getArguments().getString("startTime");
        this.endTime = getArguments().getString("endTime");
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        applyInfoAdapter = new ApplyInfoAdapter(R.layout.item_apply_info, requireContext());
        binding.recyclerview.setAdapter(applyInfoAdapter);
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);

        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (NetworkUtils.isNetAvailable(getActivity())) {
                    getNetApply();
                } else {
                    Objects.requireNonNull(RxToast.warning(requireActivity(), "当前无法连接网络，请检查网络设置是否正常"));
                }
            }
        });
    }

    @Override
    protected void initDatas() {

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (NetworkUtils.isNetAvailable(getActivity())) {
                binding.refreshLayout.setEnableRefresh(true);
                getNetApply();
            } else {
                binding.refreshLayout.setVisibility(View.GONE);
                binding.noDataLl.setVisibility(View.VISIBLE);
                binding.refreshLayout.setEnableRefresh(false);
                Objects.requireNonNull(RxToast.warning(requireActivity(), "当前无法连接网络，请检查网络设置是否正常"));
            }
        }
    }
    private void getNetApply() {
        List<String> regions = new ArrayList<>();
        List<RoleBean.DataBean.HarmlessRegionBean> shouYunRegion = RoleSP.getInstance().getRoleInfo().shouYunRegion;
        for (RoleBean.DataBean.HarmlessRegionBean harmlessRegionBean : shouYunRegion) {
            regions.add(String.valueOf(harmlessRegionBean.id));
        }
        showLoading("网络数据获取中...");
        HttpRequest.getApply(requireActivity(), regions, startTime + " " + "00:00:00", endTime + " " + "23:59:59", 2, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ApplyBean content) {
                hideLoading();
                binding.refreshLayout.finishRefresh();
                if (!content.result.isEmpty()) {
                    applyInfoAdapter.refreshDataList(content.result);
                    binding.refreshLayout.setVisibility(View.VISIBLE);
                    binding.noDataLl.setVisibility(View.GONE);
                } else {
                    binding.refreshLayout.setVisibility(View.GONE);
                    binding.noDataLl.setVisibility(View.VISIBLE);
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
