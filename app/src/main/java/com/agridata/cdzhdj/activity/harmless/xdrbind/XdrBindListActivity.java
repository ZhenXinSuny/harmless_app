package com.agridata.cdzhdj.activity.harmless.xdrbind;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.epidemic.xdr.AddXdrWebActivity;
import com.agridata.cdzhdj.activity.harmless.xdr.XdrWebViewActivity;
import com.agridata.cdzhdj.activity.harmless.xdrbind.adapter.XdrBindListAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.XdrBindListData;
import com.agridata.cdzhdj.databinding.ActivityXdrBindListBinding;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-02-27 09:14.
 * @Description :描述 綁定的相對人列表
 */
public class XdrBindListActivity extends BaseActivity<ActivityXdrBindListBinding> {
    private XdrBindListAdapter xdrBindListAdapter;
    private CustomLoadingDialog mLoadingDialog;
    private String Mid;

    private List<String> mXdrMidList;

    private List<XdrBindListData.DataBean.FarmListBean> mBindResult;
    private List<XdrBindListData.DataBean.FarmListBean> mBindTrueResult;

    public static void start(Context context) {
        Intent intent = new Intent(context, XdrBindListActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityXdrBindListBinding getBinding() {
        return ActivityXdrBindListBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setOnClickListener(v -> finish());
        initLoadingView();
        mXdrMidList = new ArrayList<>();
        mBindResult = new ArrayList<>();
        mBindTrueResult = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        xdrBindListAdapter = new XdrBindListAdapter(R.layout.item_xdr_bind, this);
        binding.recyclerView.setAdapter(xdrBindListAdapter);

        getXdrBindInfo();
        binding.bindTv.setOnClickListener(v -> XdrBindListQueryActivity.start(XdrBindListActivity.this));
        binding.beianBt.setOnClickListener(v -> AddXdrWebActivity.start(XdrBindListActivity.this));
        binding.beianOneBt.setOnClickListener(v -> AddXdrWebActivity.start(XdrBindListActivity.this));

        xdrBindListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                xdrBindListAdapter.singleChoice(position,xdrBindListAdapter.getData(position));
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });


        binding.declareBtn.setOnClickListener(v -> {
            HashMap<Integer, XdrBindListData.DataBean.FarmListBean> hashMap = xdrBindListAdapter.getHashMap();
            if (hashMap.size()>0){
                hashMap.forEach((key, value) -> {
                    System.out.println(value);
                    Mid = value.mid;
                });
                XdrWebViewActivity.start(XdrBindListActivity.this,Mid,2);
            }else {
                Objects.requireNonNull(RxToast.warning(XdrBindListActivity.this, "请先选中养殖场户进行无害化处理申报~"));
            }
        });

    }

    @Override
    protected void initDatas() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("lzx----》","go Back");
        getXdrBindInfo();
    }

    /**
     * 初始化loading组件
     */
    private void initLoadingView() {
        this.mLoadingDialog = new CustomLoadingDialog(this);
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



    private void getXdrBindInfo() {
        mBindResult.clear();
        mBindTrueResult.clear();
        HttpRequest.getXdrBindList(this, UserDataSP.getInstance().getUserInfo().Result.mobile, new CallBackLis<>() {
            @SuppressLint("CheckResult")
            @Override
            public void onSuccess(String method, XdrBindListData content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.code == 200) {
                    if ( content.data!=null && !content.data.farmList.isEmpty()){
                        mBindResult = content.data.farmList;
                        binding.notDataRl.setVisibility(View.GONE);
                        binding.declareLl.setVisibility(View.VISIBLE);
                        LogUtil.d("lzx---------》", "刷新");
                        xdrBindListAdapter.refreshDataList(content.data.farmList);
                    }else {
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        Objects.requireNonNull(RxToast.error(XdrBindListActivity.this,"当前暂无养殖场户，请先备案"));
                    }
                }else {
                    Objects.requireNonNull(RxToast.error(XdrBindListActivity.this,content.msg));
                }


            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }






}
