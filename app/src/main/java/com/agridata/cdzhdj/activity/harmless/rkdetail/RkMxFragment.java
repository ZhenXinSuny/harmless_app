package com.agridata.cdzhdj.activity.harmless.rkdetail;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collected.CollectedDetailActivity;
import com.agridata.cdzhdj.activity.adapter.RuMxdAdapter;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.RuKuDetailBean;
import com.agridata.cdzhdj.data.RuMxBean;
import com.agridata.cdzhdj.data.whh.ShouJiListForMidBean;
import com.agridata.cdzhdj.databinding.RuKuMxFragmentBinding;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class RkMxFragment extends BaseFragment<RuKuMxFragmentBinding> {

    private CustomLoadingDialog mLoadingDialog;
    private String Mid;
    private List<RuMxBean>  ruMxBeanList  ;


    private final static String TAG = "RkMxFragment------》";
    private RuMxdAdapter ruMxdAdapter;

    public static RkMxFragment newInstance(String Mid) {
        RkMxFragment fragment = new RkMxFragment();
        Bundle bundle = new Bundle();
        bundle.putString("mid", Mid);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initView() {
        assert getArguments() != null;
        this.Mid = getArguments().getString("mid");
        LogUtil.d(TAG,"获取的Mid" + Mid);

        binding.mxRecyvlerview.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        ruMxdAdapter = new RuMxdAdapter(R.layout.item_rk_mx, requireActivity());
        binding.mxRecyvlerview.setAdapter(ruMxdAdapter);
        this.addHeaderView(this.binding.mxRecyvlerview);

        initLoadingView();
        showLoading("加载中...");
        HttpRequest.getRuKuDetail(requireActivity(), Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RuKuDetailBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    //setInfo(content);
                    List<String> midList = new ArrayList<>();
                    for (RuKuDetailBean.Result.ItemDatas itemData : content.result.itemDatas) {
                        midList.add(itemData.mid);
                    }
                    getShouJiForMidList(midList);
                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(requireActivity(),error);
                hideLoading();
            }
        });
        ruMxdAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                CollectedDetailActivity.start(requireActivity(),ruMxdAdapter.getData(position).Mid,"3");
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }
    private void  getShouJiForMidList(List<String> midList){
        HttpRequest.getShouJiForMidList(requireActivity(), midList, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ShouJiListForMidBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (content.result.size()>0){
                        ruMxBeanList = new ArrayList<>();
                        for (ShouJiListForMidBean.Result itemData : content.result) {
                            // if (itemData.animal.animalName.equals("猪") || itemData.animal.animalName.equals("牛")  || itemData.animal.animalName.equals("羊")){
                            RuMxBean ruMxBean = new RuMxBean();
                            ruMxBean.SJNum = itemData.collectNo;
                            ruMxBean.Mid = itemData.mid;
                            ruMxBean.SJPerson = itemData.worker.name;
                            ruMxBean.SJTimes = itemData.createAtStr.substring(0,10);
                            ruMxBean.SJAnimalNN = itemData.animal.animalName + itemData.dieAmount;
                            ruMxBeanList.add(ruMxBean);
                            //  }
                        }
                        ruMxdAdapter.refreshDataList(ruMxBeanList);
                    }
                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(requireActivity(),error);
                hideLoading();
            }
        });
    }



    /**
     * 添加头部view
     */
    private void addHeaderView(RecyclerView recyclerView) {
        View headerView = this.getLayoutInflater().inflate(R.layout.item_rk_mx_head, recyclerView, false);
        this.ruMxdAdapter.addHeaderView(headerView);    // 添加头视图
    }
    @Override
    protected void initDatas() {

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
