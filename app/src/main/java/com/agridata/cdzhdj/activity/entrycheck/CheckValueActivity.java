package com.agridata.cdzhdj.activity.entrycheck;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.entrycheck.ble.tagreader.LowScanEarTagBleInfoAdapter;
import com.agridata.cdzhdj.activity.entrycheck.check.EarTagImgAdapter;
import com.agridata.cdzhdj.activity.epidemic.eartag.lowble.LowBluetoothActivity;
import com.agridata.cdzhdj.activity.mine.pic.PicActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.CheckInfoData;
import com.agridata.cdzhdj.utils.TextStyleUtil;
import com.agridata.cdzhdj.databinding.ActivityCheckValueBinding;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.cdzhdj.view.FullyGridLayoutManager;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.utils.LogUtil;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CheckValueActivity extends BaseActivity<ActivityCheckValueBinding> {


    private CustomLoadingDialog mLoadingDialog;
    private static final String TAG = LowBluetoothActivity.class.getName();
    public static final int SEARCH_DEVICE_CODE = 100;
    private BluetoothSocket btSocket1 = null;
    private BluetoothAdapter mBluetoothAdapter = null;
    private LowScanEarTagBleInfoAdapter lowScanEarTagInfoAdapter;
    private InputStream mmInStream;
    private OutputStream mmOutStream;
    private String address;

    private EarTagImgAdapter adapter;
    private List<String> ImgViewList;
    private LocalMedia localMedia;
    private List<LocalMedia> localMediaList;


    long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
    private String mEarTags;
    private List<String> EarTagList;

    private boolean BluetoothStatus = false;
    private int qualified_type;//状态
    private int mType;
    private CheckInfoData checkInfoData;
    @Override
    protected void onStart() {
        super.onStart();

    }






    @Override
    protected ActivityCheckValueBinding getBinding() {
        return ActivityCheckValueBinding.inflate(getLayoutInflater());
    }
    public static void start(Context context,String earTags,int type,CheckInfoData checkInfoData) {
        Intent intent = new Intent(context, CheckValueActivity.class);
        intent.putExtra("earTags", earTags);
        intent.putExtra("type", type);
        intent.putExtra("checkInfoData", checkInfoData);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        initLoadingView();
        getArguments();
        binding.titlebarLeft.setOnClickListener(v ->{
            finish();
        });

    }

    private void  setFinishInfo(){
        finish();
    }
    /**
     * 获取参数
     */
    private void getArguments() {
        this.mEarTags = this.getIntent().getStringExtra("earTags");
        this.mType = this.getIntent().getIntExtra("type",-1);
        this.checkInfoData = (CheckInfoData) this.getIntent().getSerializableExtra("checkInfoData");

    }
    @Override
    protected void initDatas() {






        EarTagList = new ArrayList<>();
        EarTagList.clear();
        String[] split = mEarTags.split((","));
        LogUtil.d("lzx---》","split" + split.toString());
        for (String s : split) {
            EarTagList.add(s);
        }
        binding.eartagsCountTv.setText(EarTagList.size()+"个");
        int earTagSize = EarTagList.size();
        if (earTagSize>10 && earTagSize<40){//大于10小于40  抽10
            binding.needCountTv.setText("10");
        }else if (earTagSize>40){ //大于40抽25%
            double mul = TextStyleUtil.mul(0.25, earTagSize);
            LogUtil.d("lzx---》","mul" + mul);
            BigDecimal b = new BigDecimal(mul).setScale(0, BigDecimal.ROUND_CEILING );
            String s = b.toString();
            LogUtil.d("lzx---》","s" + s);
            binding.needCountTv.setText(s);
        }else if (earTagSize<10){//小于10抽 全部
            binding.needCountTv.setText(earTagSize+"");
        }

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        lowScanEarTagInfoAdapter = new LowScanEarTagBleInfoAdapter(R.layout.item_eartag_ec, this);
        binding.recyclerview.setAdapter(lowScanEarTagInfoAdapter);



        if (checkInfoData.EarTag!=null &&  checkInfoData.EarTag.size()>0){
            lowScanEarTagInfoAdapter.refreshDataList(checkInfoData.EarTag);
            binding.selectNumTv.setText(checkInfoData.EarTag.size()+"");
        }else {
            binding.noDataLl.setVisibility(View.VISIBLE);
        }
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        binding.imgRecyclerview.setLayoutManager(manager);
        binding.imgRecyclerview.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 6), false));
        adapter = new EarTagImgAdapter(R.layout.item_eartag_img,this);
        binding.imgRecyclerview.setAdapter(adapter);


        adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {

                PicActivity.start(CheckValueActivity.this,UrlUtils.pic_url +  adapter.getData(position));

            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });

        //2 为不合格  1为合格
        if (checkInfoData.Type==1){
            binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
            binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.qualified_iv));
            if (!TextUtils.isEmpty(checkInfoData.imgInfo)){
                binding.checkImageLl.setVisibility(View.VISIBLE);
                LogUtil.d("lzx---》","有图片");
                ImgViewList = new ArrayList<>();
                ImgViewList.clear();
                if (!checkInfoData.imgInfo.contains(",")){
                    ImgViewList.add(checkInfoData.imgInfo);
                }else {
                    String[] splitInfo =checkInfoData.imgInfo.split(",");
                    LogUtil.d("lzx---》","split" + split.toString());
                    for (String s : splitInfo) {
                        ImgViewList.add(s);
                    }

                }
                adapter.refreshDataList(ImgViewList);
                localMediaList = new ArrayList<>();
                for (int i = 0; i < ImgViewList.size(); i++) {
                    localMedia = new LocalMedia();
                    localMedia.setPath(UrlUtils.pic_url + ImgViewList.get(i));
                    localMediaList.add(localMedia);
                }
                LogUtil.d("lzx---》","查看大图" + localMediaList.size() +  localMediaList.toString() );
            }else {
                binding.checkImageLl.setVisibility(View.GONE);
            }


        }else {
            binding.qualifiedTypeIv.setVisibility(View.VISIBLE);
            binding.mismatchLl.setVisibility(View.VISIBLE);
            binding.eartagErrorTv.setText(checkInfoData.errorEarTag);
            binding.qualifiedTypeIv.setImageDrawable(getDrawable(R.drawable.unqualified_iv));
        }


    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setFinishInfo();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
