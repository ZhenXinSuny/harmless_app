package com.agridata.cdzhdj.activity.harmless.offlinesubmission;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.harmless.collection.onCollectionListener;
import com.agridata.cdzhdj.activity.mine.pic.PicActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.CollectInfoData;
import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.bean.CollectionDBModel;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.mapper.ApplyMapper;
import com.agridata.cdzhdj.data.db.mapper.CollectionModelMapper;
import com.agridata.cdzhdj.data.db.repository.CustomDisposable;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.databinding.ActivityOfflineCoDetailsBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

import java.util.List;

import io.reactivex.Flowable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-06 11:06.
 * @Description :描述
 */
public class OfflineSubmissionCollectionDetailsActivity extends BaseActivity<ActivityOfflineCoDetailsBinding> implements onCollectionListener {

    private CustomLoadingDialog mLoadingDialog;
    private ApplyMapper applyMapper;
    private CollectionModelMapper collectionModelMapper;
    private String collectionNo, applyMid;
    private CollectInfoData result;
    private ApplyBean.ResultBean resultBean;

    public static void start(Context context, String collectionNo, String applyMid) {
        Intent intent = new Intent(context, OfflineSubmissionCollectionDetailsActivity.class);
        Bundle data = new Bundle();
        data.putString("collectionNo", collectionNo);
        data.putString("applyMid", applyMid);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    @Override
    protected ActivityOfflineCoDetailsBinding getBinding() {
        return ActivityOfflineCoDetailsBinding.inflate(getLayoutInflater());
    }

    private void getArguments() {
        Bundle bundle = getIntent().getBundleExtra("data");
        assert bundle != null;
        collectionNo = bundle.getString("collectionNo");
        applyMid = bundle.getString("applyMid");
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();
        binding.titlebarLeft.setOnClickListener(v -> finish());

    }

    @Override
    protected void initDatas() {
        applyMapper = new ApplyMapper();
        collectionModelMapper = new CollectionModelMapper();
        getLocalInfo();
    }

    /**
     * 按钮监听
     *
     * @param position
     */
    @Override
    public void collection(int position) {

    }

    private void getLocalInfo() {
        showLoading("数据获取中...");
        Flowable<List<CollectionDBModel>> listFlowable = AppDatabase.getInstance(this).collectionDao().queryList(collectionNo);
        CustomDisposable.addDisposable(listFlowable, collectionDbModelList -> {
            if (!collectionDbModelList.isEmpty()) {


                result   =   collectionModelMapper.convertToInfoData(collectionDbModelList.get(0));

                setCollectionInfo();
            }
            LogUtil.d("lzx----->", "最终数据" + collectionDbModelList.size());
        });


        Flowable<List<ApplyDBean>> listFlowable1 = AppDatabase.getInstance(this).applyDao().queryList(applyMid);
        CustomDisposable.addDisposable(listFlowable1, applyList -> {
            hideLoading();
            if (!applyList.isEmpty()) {
                ApplyDBean applyDbean = applyList.get(0);
                resultBean = applyMapper.convertToBeanModel(applyDbean);
                setApplyInfo();
            }
            LogUtil.d("lzx----->", "最终数据" + applyList.size());
        });
    }


    private void setApplyInfo() {
        binding.sqdhTv.setText(resultBean.applyNo);
        binding.yzchTv.setText(resultBean.userName);
        binding.sjhTv.setText(resultBean.mobile);
        binding.qhTv.setText(resultBean.region.regionFullName);
        binding.xxdzTv.setText(resultBean.applyAddress);
        binding.sbdwTv.setText(resultBean.animal.name);
        if (!TextUtils.isEmpty(resultBean.dieAmount)) {
            binding.sbslTv.setText(resultBean.dieAmount +"头/只/羽");
        } else {
            binding.sbslTv.setText("暂无");
        }
        binding.sfzhTv.setText(resultBean.iDCard);
        binding.sbsjTv.setText(resultBean.applyTime);
    }

    private void setCollectionInfo() {
        binding.sjZtTv.setBackground(getDrawable(R.drawable.textview_border_no_status));
        binding.sjZtTv.setText("未审核");
        binding.shenheLl.setVisibility(View.GONE);
        binding.sjdhTv.setText(result.collectNo);
        binding.sjcpTv.setText(result.carInfo.Name);
        binding.sjsfzhTv.setText(result.idcard);
        binding.sjyhkhTv.setText(result.bankCardNo);
        binding.sjxzTv.setText(result.animal.AnimalName);
        binding.sjslTv.setText(result.dieAmount);
        binding.sjzlTv.setText(result.dieWeight + "KG");
        binding.sjcllTv.setText(result.scale + "");
        binding.sjZdybTv.setText(result.disease ? "否" : "是");
        binding.xdqkTv.setText(result.disinfect);
        binding.swyyTv.setText(result.dieReasonType.equals("1") ? "疾病" : result.dieReasonType.equals("2") ? "意外" : "扑杀");


        LogUtil.d("lzx-------------->",result.itemDatas.toString());

        binding.recyclerAnimal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.recyclerAnimal.setNestedScrollingEnabled(false);
        OfflineAnimalAdapter offlineAnimalAdapter = new OfflineAnimalAdapter(R.layout.item_collected_detail_animal, this);
        binding.recyclerAnimal.setAdapter(offlineAnimalAdapter);
        offlineAnimalAdapter.refreshDataList(result.itemDatas);



        LogUtil.d("lzx-------------->",result.imgFiles.SiChuPicList.size()+"");

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.recyclerDeadAnimalEartag.setLayoutManager(manager);
        binding.recyclerDeadAnimalEartag.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        LiXianPicAdapter liXianPicAdapter = new LiXianPicAdapter(R.layout.gv_filter_image, this);
        binding.recyclerDeadAnimalEartag.setAdapter(liXianPicAdapter);
        liXianPicAdapter.refreshDataList(result.imgFiles.SiChuPicList);
        liXianPicAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                PicActivity.start(OfflineSubmissionCollectionDetailsActivity.this, "file://" + liXianPicAdapter.getData(position).Mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        FullyGridLayoutManager manager1 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.recyclerShiti.setLayoutManager(manager1);
        binding.recyclerShiti.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        LiXianPicAdapter liXianPicAdapter1 = new LiXianPicAdapter(R.layout.gv_filter_image, this);
        binding.recyclerShiti.setAdapter(liXianPicAdapter1);
        liXianPicAdapter1.refreshDataList(result.imgFiles.ShiTiPicList);
        liXianPicAdapter1.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                PicActivity.start(OfflineSubmissionCollectionDetailsActivity.this, "file://" + liXianPicAdapter1.getData(position).Mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.recyclerZhuangche.setLayoutManager(manager2);
        binding.recyclerZhuangche.addItemDecoration(new GridSpacingItemDecoration(4, ScreenUtils.dip2px(this, 8), false));
        LiXianPicAdapter liXianPicAdapter2 = new LiXianPicAdapter(R.layout.gv_filter_image, this);
        binding.recyclerZhuangche.setAdapter(liXianPicAdapter2);
        liXianPicAdapter2.refreshDataList(result.imgFiles.ZhuangChePicList);
        liXianPicAdapter2.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                PicActivity.start(OfflineSubmissionCollectionDetailsActivity.this, "file://" + liXianPicAdapter2.getData(position).Mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        if (result.imgFiles!=null){
            Glide.with(OfflineSubmissionCollectionDetailsActivity.this)
                    .load("file://"  + result.imgFiles.ShouYunYuanPic)
                    .error(R.drawable.ic_default_iv)
                    .placeholder(R.drawable.ic_default_iv)
                    .fallback( R.drawable.ic_default_iv)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(22)))
                    .into(binding.shouyunrenIv);

            Glide.with(OfflineSubmissionCollectionDetailsActivity.this)
                    .load("file://" + result.imgFiles.YangZhiChangHuPic)
                    .error(R.drawable.ic_default_iv)
                    .placeholder( R.drawable.ic_default_iv)
                    .fallback( R.drawable.ic_default_iv)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(22)))
                    .into(binding.yzchIv);

            Glide.with(OfflineSubmissionCollectionDetailsActivity.this)
                    .load("file://" + result.imgFiles.CollectCertPic)
                    .error(R.drawable.ic_default_iv)
                    .placeholder( R.drawable.ic_default_iv)
                    .fallback( R.drawable.ic_default_iv)
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(22)))
                    .into(binding.zhizhidanjuIv);


        }
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
}
