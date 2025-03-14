package com.agridata.cdzhdj.activity.harmless.chulifragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.chuli.ChuLiShouJiListActivity;
import com.agridata.cdzhdj.activity.adapter.ChuLiMxdAdapter;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.ChuLiDetailBean;
import com.agridata.cdzhdj.data.RuMxBean;
import com.agridata.cdzhdj.data.whh.RuKuListForMidBean;
import com.agridata.cdzhdj.databinding.FragmentClRuListBinding;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class CLRkFragment extends BaseFragment<FragmentClRuListBinding> {

    private CustomLoadingDialog mLoadingDialog;
    private String Mid;
    private List<RuMxBean> ruMxBeanList;


    private final static String TAG = "RkMxFragment------》";
    private ChuLiMxdAdapter chuLiMxdAdapter;

    public static CLRkFragment newInstance(String Mid) {
        CLRkFragment fragment = new CLRkFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mid", Mid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initView() {
        assert getArguments() != null;
        this.Mid = getArguments().getString("mid");
        LogUtil.d(TAG, "获取的Mid" + Mid);

        binding.clRuRecyclerview.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        chuLiMxdAdapter = new ChuLiMxdAdapter(R.layout.item_chuli_ruku_layout, requireActivity());
        binding.clRuRecyclerview.setAdapter(chuLiMxdAdapter);


        initLoadingView();
        showLoading("加载中...");
        HttpRequest.getChuLiDetails(requireActivity(), Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ChuLiDetailBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (content.result.itemDatas.size() > 0) {
                        //chuLiMxdAdapter.refreshDataList(content.result.itemDatas);
                        List<String> midList = new ArrayList<>();
                        for (ChuLiDetailBean.Result.ItemDatas itemData : content.result.itemDatas) {
                            midList.add(itemData.mid);
                        }
                        getRuKuForMidList(midList);
                    }
                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(requireActivity(), error);
                hideLoading();
            }
        });
        chuLiMxdAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                ChuLiShouJiListActivity.start(requireActivity(), chuLiMxdAdapter.getData(position).mid);
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

    private void getRuKuForMidList(List<String> midList) {
        HttpRequest.getRuKuForMidList(requireActivity(), midList, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RuKuListForMidBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (content.result.size() > 0) {
                        chuLiMxdAdapter.refreshDataList(content.result);
                    }
                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(requireActivity(), error);
                hideLoading();
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
