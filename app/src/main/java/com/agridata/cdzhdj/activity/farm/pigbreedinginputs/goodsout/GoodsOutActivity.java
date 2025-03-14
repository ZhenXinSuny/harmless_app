package com.agridata.cdzhdj.activity.farm.pigbreedinginputs.goodsout;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.pigbreed.goods.GoodsOutListBean;
import com.agridata.cdzhdj.databinding.ActivityGoodsOutBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-05 15:49.
 * @Description :描述 物品出库
 */
public class GoodsOutActivity extends BaseActivity<ActivityGoodsOutBinding> {

    private GoodsOutAdapter goodsOutAdapter;

    private CustomLoadingDialog mLoadingDialog;

    public static void start(Context context) {
        Intent intent = new Intent(context, GoodsOutActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityGoodsOutBinding getBinding() {
        return ActivityGoodsOutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());

        binding.addFab.setOnClickListener(v -> AddGoodsOutActivity.start(GoodsOutActivity.this));

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(GoodsOutActivity.this, LinearLayoutManager.VERTICAL, false));
        goodsOutAdapter = new GoodsOutAdapter(R.layout.item_goods_out_list, GoodsOutActivity.this);
        binding.recyclerview.setAdapter(goodsOutAdapter);


        goodsOutAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                GoodsOutDetailsActivity.start(GoodsOutActivity.this,goodsOutAdapter.getData(position).mid);
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
        getFeedStorageListData();
    }

    private void  getFeedStorageListData(){
        showLoading("正在加载中...");
        HttpRequest.getGoodsList(GoodsOutActivity.this, UserDataSP.getInstance().getUserInfo().Result.userId,new CallBackLis<>() {
            @Override
            public void onSuccess(String method, GoodsOutListBean content) {
                hideLoading();
                if (content.errorCode==0){
                    if (content.result.size()>0){
                        binding.notDataRl.setVisibility(View.GONE);
                        goodsOutAdapter.refreshDataList(content.result);
                    }
                }else {
                    Objects.requireNonNull( RxToast.error(GoodsOutActivity.this,content.message));
                }
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull( RxToast.error(GoodsOutActivity.this,error));
            }
        });

    }
    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(GoodsOutActivity.this);
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
