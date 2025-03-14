package com.agridata.cdzhdj.activity.harmless.xdrinfo;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.regionAdmin.collected.CollectedDetailActivity;
import com.agridata.cdzhdj.activity.adapter.xdr.XdrCollectListAdapter;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.XdrCollectListBean;
import com.agridata.cdzhdj.databinding.FragmentXdrInfoBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.List;
import java.util.Objects;

public class XdrInfoListFragment extends BaseFragment<FragmentXdrInfoBinding> {

    private CustomLoadingDialog mLoadingDialog;
    private String Mid;
    private List<XdrCollectListBean>  xdrCollectListBeanList  ;


    private final static String TAG = "RkMxFragment------》";
    private XdrCollectListAdapter xdrCollectListAdapter;

    public static XdrInfoListFragment newInstance(String Mid) {
        XdrInfoListFragment fragment = new XdrInfoListFragment();
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


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        xdrCollectListAdapter = new XdrCollectListAdapter(R.layout.item_xdr_collect, requireActivity());
        binding.recyclerView.setAdapter(xdrCollectListAdapter);


        initLoadingView();
        showLoading("加载中...");
        HttpRequest.getXdrCollectList(requireActivity(), Mid,"", new CallBackLis<>() {
            @Override
            public void onSuccess(String method, XdrCollectListBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.code == 200) {
                    hideLoading();
                    xdrCollectListAdapter.refreshDataList(content.data);
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(requireActivity(),error));
                hideLoading();
            }
        });
        xdrCollectListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                CollectedDetailActivity.start(requireActivity(),xdrCollectListAdapter.getData(position).mid,"1");
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
