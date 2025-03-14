package com.agridata.cdzhdj.activity.harmless.regionAdmin.collected;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.mine.pic.PicActivity;
import com.agridata.cdzhdj.activity.adapter.CollectedDetailAnimalAdapter;
import com.agridata.cdzhdj.activity.adapter.SCPicAdapter;
import com.agridata.cdzhdj.activity.adapter.STPicAdapter;
import com.agridata.cdzhdj.activity.adapter.ZCPicAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.CollectedDetailBean;
import com.agridata.cdzhdj.databinding.ActivityCollectedDetailBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;

public class CollectedDetailActivity extends BaseActivity<ActivityCollectedDetailBinding> {

    private final static String TAG = "ToBeCollectedDetailActivity------》";
    private String Mid;
    private String type;
    private CustomLoadingDialog mLoadingDialog;

    private CollectedDetailBean collectedDetailBean;
    private CollectedDetailAnimalAdapter collectedDetailAnimalAdapter;


    private SCPicAdapter gridImageDeathAdapter;



    private STPicAdapter imageShiTiAdapter;



    private ZCPicAdapter imageZhuangCheAdapter;


    public static void start(Activity context, String Mid,String Type) {
        Intent intent = new Intent(context, CollectedDetailActivity.class);
        intent.putExtra("Mid", Mid);
        intent.putExtra("Type", Type);
        context.startActivity(intent);
    }

    @Override
    protected ActivityCollectedDetailBinding getBinding() {
        return ActivityCollectedDetailBinding.inflate(getLayoutInflater());
    }

    /**
     * 获取参数
     */
    private void getArguments() {
        Mid = this.getIntent().getStringExtra("Mid");
        type = this.getIntent().getStringExtra("Type");
        LogUtil.d(TAG, "MID" + Mid);
    }

    @Override
    protected void initView() {
        getArguments();
        initLoadingView();

        binding.titlebarLeft.setOnClickListener(v -> finish());
        showLoading("加载中...");
        HttpRequest.getCollectedDetail(CollectedDetailActivity.this, Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, CollectedDetailBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    collectedDetailBean = content;
                    setInfo();
                }
            }

            @Override
            public void onFailure(String method, String error) {

            }
        });
    }

    private void setInfo() {
        if (collectedDetailBean != null) {
            CollectedDetailBean.Result result = collectedDetailBean.result;
            binding.sqdhTv.setText(result.dep_ApplyGUID.applyNo);
            binding.yzchTv.setText(result.dep_ApplyGUID.userName);
            binding.sjhTv.setText(result.dep_ApplyGUID.mobile);
            binding.qhTv.setText(result.dep_ApplyGUID.region.regionFullName);
            binding.xxdzTv.setText(result.dep_ApplyGUID.applyAddress);
            binding.sbdwTv.setText(result.animal.animalName);
            if (!TextUtils.isEmpty(result.dep_ApplyGUID.dieAmount)){
                binding.sbslTv.setText(result.dep_ApplyGUID.dieAmount);
            }else {
                binding.sbslTv.setText("暂无");
            }

            binding.sfzhTv.setText(result.dep_ApplyGUID.iDCard);
            binding.sbsjTv.setText(result.dep_ApplyGUID.applyTime);

            if (result.checkStatus == 0) {
                binding.sjZtTv.setBackground(getDrawable(R.drawable.textview_border_no_status));
                binding.sjZtTv.setText("未审核");
                binding.shenheLl.setVisibility(View.GONE);
            } else {
                    if (result.checkStatus == 2){
                        binding.sjZtTv.setBackground(getDrawable(R.drawable.textview_border_no_status));
                        binding.sjZtTv.setText("已驳回");
                    }else if (result.checkStatus == 1){
                        if (type.equals("2")){
                            binding.sjZtTv.setBackground(getDrawable(R.drawable.textview_border_chuli));
                        }else if (type.equals("3")){
                            binding.sjZtTv.setBackground(getDrawable(R.drawable.textview_border_ruku));
                        }else {
                            binding.sjZtTv.setBackground(getDrawable(R.drawable.textview_border));
                        }
                        binding.sjZtTv.setText("已审核");
                    }




                binding.shenheLl.setVisibility(View.VISIBLE);

                if (!TextUtils.isEmpty(result.checkUser)){
                    binding.shrTv.setText(result.checkUser);
                }
                if (!TextUtils.isEmpty(result.checkRemark)){
                    binding.shyjTv.setText(result.checkRemark);
                }
                if (!TextUtils.isEmpty(result.checkTime)){
                    binding.shsjTv.setText(result.checkTime);
                }

                if (result.imgFiles.shenHeQianMing!=null){
                    Glide.with(CollectedDetailActivity.this)
                            .load(UrlUtils.pic_url + result.imgFiles.shenHeQianMing)
                            .error(R.drawable.ic_default_iv)
                            .placeholder( R.drawable.ic_default_iv)
                            .fallback( R.drawable.ic_default_iv)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(binding.shryqmIv);
                }
            }


            binding.sjdhTv.setText(result.collectNo);

            binding.sjcpTv.setText(result.carInfo.name);
            binding.sjsfzhTv.setText(result.iDCard);
            binding.sjyhkhTv.setText(result.bankCardNo);
            binding.sjxzTv.setText(result.animal.animalName);
            binding.sjslTv.setText(result.dieAmount);
            binding.sjzlTv.setText(result.dieWeight+"KG");
            binding.sjcllTv.setText(result.scale+"");
            binding.sjZdybTv.setText(result.disease ?"否":"是");
            binding.xdqkTv.setText(result.disinfect);
            binding.swyyTv.setText(result.dieReasonType.equals("1")?"疾病":result.dieReasonType.equals("2")?"意外":"扑杀");

            binding.recyclerAnimal.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            binding.recyclerAnimal.setNestedScrollingEnabled(false);
            collectedDetailAnimalAdapter = new CollectedDetailAnimalAdapter(R.layout.item_collected_detail_animal, this);
            binding.recyclerAnimal.setAdapter(collectedDetailAnimalAdapter);
            collectedDetailAnimalAdapter.refreshDataList(result.itemDatas);




            FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                    4, GridLayoutManager.VERTICAL, false);
            binding.recyclerDeadAnimalEartag.setLayoutManager(manager);
            binding.recyclerDeadAnimalEartag.addItemDecoration(new GridSpacingItemDecoration(4,
                    ScreenUtils.dip2px(this, 8), false));
            gridImageDeathAdapter = new SCPicAdapter(R.layout.gv_filter_image,this);
            binding.recyclerDeadAnimalEartag.setAdapter(gridImageDeathAdapter);
            gridImageDeathAdapter.refreshDataList(result.imgFiles.siChuPicList);

            gridImageDeathAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                    PicActivity.start(CollectedDetailActivity.this, UrlUtils.pic_url + gridImageDeathAdapter.getData(position).mid);
                }

                @Override
                public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                    return false;
                }
            });





            FullyGridLayoutManager managerShiTi = new FullyGridLayoutManager(this,
                    4, GridLayoutManager.VERTICAL, false);
            binding.recyclerShiti.setLayoutManager(managerShiTi);
            binding.recyclerShiti.addItemDecoration(new GridSpacingItemDecoration(4,
                    ScreenUtils.dip2px(this, 8), false));
            imageShiTiAdapter =new STPicAdapter(R.layout.gv_filter_image,this);
            binding.recyclerShiti.setAdapter(imageShiTiAdapter);
            imageShiTiAdapter.refreshDataList(result.imgFiles.shiTiPicList);


            imageShiTiAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                    PicActivity.start(CollectedDetailActivity.this, UrlUtils.pic_url + imageShiTiAdapter.getData(position).mid);
                }

                @Override
                public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                    return false;
                }
            });



            FullyGridLayoutManager managerZhuangChe = new FullyGridLayoutManager(this,
                    4, GridLayoutManager.VERTICAL, false);
            binding.recyclerZhuangche.setLayoutManager(managerZhuangChe);
            binding.recyclerZhuangche.addItemDecoration(new GridSpacingItemDecoration(4,
                    ScreenUtils.dip2px(this, 8), false));
            imageZhuangCheAdapter =new ZCPicAdapter(R.layout.gv_filter_image,this);
            binding.recyclerZhuangche.setAdapter(imageZhuangCheAdapter);
            imageZhuangCheAdapter.refreshDataList(result.imgFiles.zhuangChePicList);

            imageZhuangCheAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                    PicActivity.start(CollectedDetailActivity.this, UrlUtils.pic_url + imageZhuangCheAdapter.getData(position).mid);
                }

                @Override
                public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                    return false;
                }
            });

            if (result.imgFiles!=null){
                Glide.with(CollectedDetailActivity.this)
                        .load(UrlUtils.pic_url + result.imgFiles.shouYunYuanPic)
                        .error(R.drawable.ic_default_iv)
                        .placeholder( R.drawable.ic_default_iv)
                        .fallback( R.drawable.ic_default_iv)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(binding.shouyunrenIv);

                Glide.with(CollectedDetailActivity.this)
                        .load(UrlUtils.pic_url + result.imgFiles.yangZhiChangHuPic)
                        .error(R.drawable.ic_default_iv)
                        .placeholder( R.drawable.ic_default_iv)
                        .fallback( R.drawable.ic_default_iv)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(binding.yzchIv);
            }


        }
    }

    @Override
    protected void initDatas() {

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
