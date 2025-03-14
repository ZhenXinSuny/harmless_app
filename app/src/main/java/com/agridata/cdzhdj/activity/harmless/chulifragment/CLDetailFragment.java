package com.agridata.cdzhdj.activity.harmless.chulifragment;

import android.os.Bundle;
import android.view.View;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.ChuLiDetailBean;
import com.agridata.cdzhdj.data.whh.RuKuListForMidBean;
import com.agridata.cdzhdj.data.whh.ShouJiListForMidBean;
import com.agridata.cdzhdj.databinding.ClDetailFragmentBinding;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.utils.UrlUtils;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CLDetailFragment extends BaseFragment<ClDetailFragmentBinding> {

    private CustomLoadingDialog mLoadingDialog;
    private String Mid;


    private final static String TAG = "RkDetailFragment------》";

    public static CLDetailFragment newInstance(String Mid) {
        CLDetailFragment fragment = new CLDetailFragment();
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
        initLoadingView();
        showLoading("加载中...");
        HttpRequest.getChuLiDetails(requireActivity(), Mid, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ChuLiDetailBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    List<String> midList = new ArrayList<>();
                    for (ChuLiDetailBean.Result.ItemDatas itemData : content.result.itemDatas) {
                        midList.add(itemData.mid);
                    }
                    getRuKuForMidList(midList);



                    setInfo(content);


                }
            }
            @Override
            public void onFailure(String method, String error) {
                ToastUtil.showToast(requireActivity(),error);
                hideLoading();
            }
        });
    }

    private void  setInfo(ChuLiDetailBean chuLiDetailBean){

        if (chuLiDetailBean!=null){
            ChuLiDetailBean.Result result = chuLiDetailBean.result;

            if (result.checkStatus.equals("0")) {
                binding.ztTv.setBackground(getResources().getDrawable(R.drawable.textview_border_no_status));
                binding.ztTv.setText("未审核");

            } else {
                binding.ztTv.setBackground(getResources().getDrawable(R.drawable.textview_border_chuli));
                binding.ztTv.setText("已审核");
            }
            binding.cldhTv.setText(result.harmlessNo);
            binding.clrTv.setText(result.handleUser.name);


            binding.clzlTv.setText(result.weight+"KG");
            binding.clfsTv.setText(result.handleCategory.name);
            binding.bzxxTv.setText(result.remark);
            binding.clsjTv.setText(result.createAtStr.substring(0,19));
            if (result.imgFiles.chuLiYuanQianMing!=null) {
                Glide.with(requireActivity())
                        .load(UrlUtils.pic_url + result.imgFiles.chuLiYuanQianMing)
                        .error(R.drawable.ic_default_iv)
                        .placeholder(R.drawable.ic_default_iv)
                        .fallback(R.drawable.ic_default_iv)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                        .into(binding.rkrqmIv);
            }
            if (result.checkStatus.equals("1") || result.checkStatus.equals("2")){//未审核
                binding.shjgTv.setText(result.checkStatus.equals("1")?"通过":"驳回");
                binding.shrTv.setText(result.checkUser);
                binding.shsjTv.setText(result.checkTime);
                binding.shyjTv.setText(result.checkRemark);

                if (result.imgFiles.shenHeQianMing!=null){
                    Glide.with(requireActivity())
                            .load(UrlUtils.pic_url + result.imgFiles.shenHeQianMing)
                            .error(R.drawable.ic_default_iv)
                            .placeholder( R.drawable.ic_default_iv)
                            .fallback( R.drawable.ic_default_iv)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                            .into(binding.clshqmIv);
                }
            }else {
                binding.shxxLl.setVisibility(View.GONE);
            }
        }
    }




    @Override
    protected void initDatas() {

    }

    /**
     * 获取入库
     * @param midList
     */
    private void getRuKuForMidList(List<String> midList) {
        HttpRequest.getRuKuForMidList(requireActivity(), midList, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, RuKuListForMidBean content) {
                LogUtil.d("lzx---------》", content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (!content.result.isEmpty()) {
                        List<String> midList = new ArrayList<>();
                        for (RuKuListForMidBean.Result result : content.result) {
                            List<RuKuListForMidBean.Result.ItemDatas> itemDatas = result.itemDatas;
                            for (RuKuListForMidBean.Result.ItemDatas itemData : itemDatas) {
                                midList.add(itemData.mid);
                            }
                        }

                        LogUtil.i(TAG,"查询收集");
                        getShouJiForMidList(midList);
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





    private void  getShouJiForMidList(List<String> midList){
        HttpRequest.getShouJiForMidList(requireActivity(), midList, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ShouJiListForMidBean content) {
                LogUtil.d(TAG, "收集单" + content.toString());
                if (content.status == 0) {
                    hideLoading();
                    if (!content.result.isEmpty()){
                        Map<String,Integer> scoreMap = new HashMap<>();
                        for (ShouJiListForMidBean.Result result : content.result) {
                            String animalName = result.animal.animalName;
                            int  dieAmount = Integer.parseInt(result.dieAmount);

                            int  dieWeight = Integer.parseInt(result.dieWeight);

                            if ("猪".equals(animalName) || "牛".equals(animalName) ||"羊".equals(animalName)){
                                if (scoreMap.containsKey(animalName)){
                                    scoreMap.put(animalName, scoreMap.get(animalName) + dieAmount);
                                }else {
                                    scoreMap.put(animalName, dieAmount);
                                }
                            }else {
                                if (scoreMap.containsKey(animalName)){
                                    scoreMap.put(animalName, scoreMap.get(animalName) + dieWeight);
                                }else {
                                    scoreMap.put(animalName, dieWeight);
                                }
                            }


                        }
                        StringBuilder buffer = new StringBuilder();
                        for (Map.Entry<String,Integer> entry : scoreMap.entrySet()) {
                            if ("猪".equals(entry.getKey()) || "牛".equals(entry.getKey()) || "羊".equals(entry.getKey())){
                                if (scoreMap.size()>1){
                                    buffer.append(entry.getKey()).append(entry.getValue()).append("头").append(",");
                                }else {
                                    buffer.append(entry.getKey()).append(entry.getValue()).append("头");
                                }
                            }else {
                                if (scoreMap.size()>1){
                                    buffer.append(entry.getKey()).append(entry.getValue()).append("公斤").append(",");
                                }else {
                                    buffer.append(entry.getKey()).append(entry.getValue()).append("公斤");
                                }
                            }
                        }
                        LogUtil.i(TAG,buffer.toString());
                        binding.clslTv.setText(buffer.toString());
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
