package com.agridata.cdzhdj.activity.harmless.collection;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.RoleSP;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.RoleBean;
import com.agridata.cdzhdj.data.StatusData;
import com.agridata.cdzhdj.data.db.bean.ApplyDBean;
import com.agridata.cdzhdj.data.db.dao.AppDatabase;
import com.agridata.cdzhdj.data.db.mapper.ApplyMapper;
import com.agridata.cdzhdj.data.db.repository.ApplyBeanRepository;
import com.agridata.cdzhdj.data.db.repository.CustomDisposable;
import com.agridata.cdzhdj.data.harmless.ApplyBean;
import com.agridata.cdzhdj.data.harmless.ApplyBoHuiBean;
import com.agridata.cdzhdj.databinding.FragmentApplyBinding;
import com.agridata.cdzhdj.utils.NetworkStateReceiver;
import com.agridata.cdzhdj.utils.NetworkUtils;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.cdzhdj.utils.ToastUtil;
import com.agridata.cdzhdj.view.CustomLoadingDialog;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-28 10:57.
 * @Description :描述
 */
public class DaiApplyFragment extends BaseFragment<FragmentApplyBinding> implements NetworkStateReceiver.NetworkStateChangedListener,onCollectionListener {
    private ApplyInfoAdapter applyInfoAdapter;
    private String startTime;
    private String endTime;
    private CustomLoadingDialog mLoadingDialog;

    private final static String TAG = "DaiApplyFragment------》";
    private List<ApplyBean.ResultBean> resultBeans;


    public static DaiApplyFragment newInstance(String startTime, String endTime) {
        DaiApplyFragment fragment = new DaiApplyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("startTime", startTime);
        bundle.putString("endTime", endTime);
        fragment.setArguments(bundle);
        return fragment;
    }


    public void performNetworkRequest(String mStartTime, String mEndTime) {
        // 进行网络请求
        // 使用 startTime 和 endTime 发起网络请求
        LogUtil.d("lzx------------》", "startTimes" + startTime);
        LogUtil.d("lzx------------》", "endTime" + endTime);
        startTime = mStartTime;
        endTime = mEndTime;
        if (NetworkUtils.isNetAvailable(getActivity())) {
            getNetApply();
        } else {
            queryApplyListFromLocal();
        }
    }
    @Override
    protected void initView() {
        initLoadingView();
        NetworkStateReceiver.getInstance().addNetworkStateChangedListener(this); //添加网络监听器
        assert getArguments() != null;
        this.startTime = getArguments().getString("startTime");
        this.endTime = getArguments().getString("endTime");
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        applyInfoAdapter = new ApplyInfoAdapter(R.layout.item_apply_info, requireContext());
        applyInfoAdapter.setCollectionListener(this);
        binding.recyclerview.setAdapter(applyInfoAdapter);
        binding.refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        binding.refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (NetworkUtils.isNetAvailable(getActivity())) {
                getNetApply();
            } else {
                queryApplyListFromLocal();
            }
        });


    }

    @Override
    protected void initDatas() {

    }
    public void updateTime(String startTime,String endTime) {
        // 在这里更新你的Fragment UI，例如更新TextView显示新的时间
        // 示例代码：
        LogUtil.d("lzx------------》", "startTimes" + startTime);
        LogUtil.d("lzx------------》", "GOGOGOGOOGOGOGOGOG" + endTime);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (NetworkUtils.isNetAvailable(getActivity())) {
            getNetApply();
        } else {
            queryApplyListFromLocal();
        }
    }

    private void getNetApply() {
        List<String> regions = new ArrayList<>();
        List<RoleBean.DataBean.HarmlessRegionBean> shouYunRegion = RoleSP.getInstance().getRoleInfo().shouYunRegion;
        for (RoleBean.DataBean.HarmlessRegionBean harmlessRegionBean : shouYunRegion) {
            regions.add(String.valueOf(harmlessRegionBean.id));
        }
        showLoading("网络数据获取中...");
        HttpRequest.getApply(requireActivity(), regions, startTime + " " + "00:00:00", endTime + " " + "23:59:59",0, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, ApplyBean content) {
                hideLoading();
                binding.refreshLayout.finishRefresh();
                if (!content.result.isEmpty()) {
                    binding.refreshLayout.setVisibility(View.VISIBLE);
                    binding.noDataLl.setVisibility(View.GONE);
                    if (!content.result.isEmpty()){
                        applyInfoAdapter.refreshDataList(content.result);
                    }else {
                        binding.refreshLayout.setVisibility(View.GONE);
                        binding.noDataLl.setVisibility(View.VISIBLE);
                    }

                      setDBInfo(content);
//                    boolean b = FileUtil.deleteFile(requireActivity());//

                } else {
                    LogUtil.d("lzx----》","暂无数据");
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

    private void  setDBInfo(ApplyBean content){
//        Flowable<List<ApplyDBean>> listFlowable = AppDatabase.getInstance(requireContext()).applyDao().queryAllAsList(startTime + " " + "00:00:00", endTime + " " + "23:59:59", 0);
//        CustomDisposable.addDisposable(listFlowable, applyDbeanList -> {
//            //数据库==null 时进行全部保存
//            if (applyDbeanList.isEmpty()){
//                ApplyBeanRepository.getInstance().deleteAll(requireActivity(), content.result);
//                return;
//            }
//            //不为空时转换进行对比
//            resultBeans = new ArrayList<>();
//            ApplyMapper applyMapper = new ApplyMapper();
//            for (ApplyDBean applyDBean : applyDbeanList) {
//                ApplyBean.ResultBean resultBean = applyMapper.convertToBeanModel(applyDBean);
//                resultBeans.add(resultBean);
//            }
//        });
        //网络数据
        List<ApplyBean.ResultBean> resultNet = content.result;
        //网络数据不为空
        if (!resultNet.isEmpty()){
            //新数据
            for (ApplyBean.ResultBean newBean : resultNet) {
                Flowable<List<ApplyDBean>> listFlowable1 = AppDatabase.getInstance(requireActivity()).applyDao().queryList(newBean.mid);
                CustomDisposable.addDisposable(listFlowable1, applyDbeanList -> {
                    LogUtil.d("lzx--->查询",  applyDbeanList.size() +"");
                   if (applyDbeanList.isEmpty()) {
                       ApplyBeanRepository.getInstance().insert(requireActivity(),newBean);
                   }
                });
            }
        }


    }



    /**
     * 从本地获取
     */
    @SuppressLint("CheckResult")
    private void queryApplyListFromLocal() {
        showLoading("本地数据获取中...");  //"2024-01-01 00:00:00", "2024-01-31 23:59:59"  startTime + " " + "00:00:00", endTime + " " + "23:59:59"
        Flowable<List<ApplyDBean>> listFlowable = AppDatabase.getInstance(requireContext()).applyDao().queryAllAsList(startTime + " " + "00:00:00", endTime + " " + "23:59:59", 0);
        CustomDisposable.addDisposable(listFlowable, applyDbeanList -> {
            hideLoading();
            binding.refreshLayout.finishRefresh();
            if (applyDbeanList.isEmpty()) {
                binding.recyclerview.setVisibility(View.GONE);
                binding.noDataLl.setVisibility(View.VISIBLE);
                return;
            }
            List<ApplyBean.ResultBean> resultBeans = new ArrayList<>();
            ApplyMapper applyMapper = new ApplyMapper();
            for (ApplyDBean applyDBean : applyDbeanList) {
                ApplyBean.ResultBean resultBean = applyMapper.convertToBeanModel(applyDBean);
                resultBeans.add(resultBean);
            }
            applyInfoAdapter.refreshDataList(resultBeans);

            LogUtil.d("lzx----->", "最终数据" + resultBeans.size());
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

    @Override
    public void onNetworkStateChanged(boolean isNetAvailable) {
        if (isNetAvailable) {
            binding.noNetworkLayout.noNetworkTipLl.setVisibility(View.GONE);
        } else {
            binding.noNetworkLayout.noNetworkTipLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        NetworkStateReceiver.getInstance().removeNetworkStateChangedListener(this); //添加网络监听器
    }


    /**
     * 驳回
     * @param position
     */
    @Override
    public void collection(int position) {
        ApplyBean.ResultBean data = applyInfoAdapter.getData(position);
        ApplyBoHuiBean applyBoHuiBean = new ApplyBoHuiBean();
        applyBoHuiBean.Mid =data.mid;

        showBoHuiDialog(applyBoHuiBean);

    }


    private void showBoHuiDialog(ApplyBoHuiBean applyBoHuiBean) {
        View view = getLayoutInflater().inflate(R.layout.dialog_common, null);
        AlertDialog exitDialogLoginOut = new AlertDialog.Builder(requireActivity()).create();
        exitDialogLoginOut.setView(view);
        exitDialogLoginOut.setCanceledOnTouchOutside(false);
        exitDialogLoginOut.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView cancelTv = view.findViewById(R.id.negative_tv);//取消按钮
        TextView confirmTv = view.findViewById(R.id.positive_tv);//确定按钮
        TextView contentTv = view.findViewById(R.id.content_tv);
        TextView title_tv = view.findViewById(R.id.title_tv);
        title_tv.setVisibility(View.VISIBLE);
        title_tv.setText("申报驳回");
        contentTv.setText("确定要驳回该条申报信息？");
        cancelTv.setOnClickListener(view12 -> exitDialogLoginOut.dismiss());
        confirmTv.setOnClickListener(v -> {
            if (NetworkUtils.isNetAvailable(requireActivity())){
                applyBoHui(applyBoHuiBean);
            }else {
                Objects.requireNonNull(RxToast.warning(requireActivity(),"当前暂无网络，无法驳回请重试"));
            }
            exitDialogLoginOut.dismiss();
        });
        exitDialogLoginOut.show();
        WindowManager.LayoutParams params = exitDialogLoginOut.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(requireActivity()) * 0.85);
        exitDialogLoginOut.getWindow().setAttributes(params);
    }

    private  void  applyBoHui(ApplyBoHuiBean applyBoHuiBean){
        HttpRequest.applyBoHui(requireActivity(), applyBoHuiBean, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, StatusData content) {
                LogUtil.d("lzx---------》", content.toString());
                hideLoading();
                Objects.requireNonNull(RxToast.success(requireActivity(), "驳回成功"));
                getNetApply();
            }

            @Override
            public void onFailure(String method, String error) {
                hideLoading();
                Objects.requireNonNull(RxToast.error(requireActivity(), error));

            }
        });
    }
}
