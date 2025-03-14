package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.adapter.EnforcementMianAdapter;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.details.EnforcementDetailsActivity;
import com.agridata.cdzhdj.base.BaseFragment;
import com.agridata.cdzhdj.data.AHIUserEXBean;
import com.agridata.cdzhdj.data.law.EnforcementHomeData;
import com.agridata.cdzhdj.databinding.FragmentCoSponsorBinding;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-31 10:16.
 * @Description :描述
 */
public class CoSponsorFragment extends BaseFragment<FragmentCoSponsorBinding> {
    private String mTimes;
    private int mLawType;
    private String endTimes;
    private String mid;
    private String userId;
    private EnforcementMianAdapter enforcementMianAdapter;


    public static CoSponsorFragment newInstance(String times,String endTimes, int lawType) {
        CoSponsorFragment fragment = new CoSponsorFragment();
        Bundle bundle = new Bundle();
        bundle.putString("times", times);
        bundle.putString("endTimes", endTimes);
        bundle.putInt("lawType", lawType);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected void initView() {
        assert getArguments() != null;
        this.mTimes = getArguments().getString("times");
        this.endTimes = getArguments().getString("endTimes");
        this.mLawType = getArguments().getInt("lawType");
        userId = UserDataSP.getInstance().getUserInfo().Result.userId;

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
        enforcementMianAdapter = new EnforcementMianAdapter(R.layout.item_enforcement_list, requireActivity());
        binding.recyclerview.setAdapter(enforcementMianAdapter);

        enforcementMianAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                EnforcementDetailsActivity.start(requireActivity(),enforcementMianAdapter.getData(position).mid);
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }
    @Override
    protected void initDatas() {
        getAHIUserExMid();
    }

    @Override
    protected void OnEventMainThread() {
        super.OnEventMainThread();
        mRxManager.on("startTime", o -> {
            mTimes = (String) o;
            LogUtil.d("lzx------------》", "startTimes" + mTimes);
        });
        mRxManager.on("endTime", o -> {
            endTimes = (String) o;
            LogUtil.d("lzx------------》", "startTimes" + mTimes);
        });
        getAHIUserExMid();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAHIUserExMid();
        LogUtil.d("CoSponsorFragment","onResume");
    }

    private void  getEnforcementListData(){
        HttpRequest.getEnforcementList(requireActivity(), mTimes,endTimes,"",mid,2, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, EnforcementHomeData enforcementHomeData) {
                if (enforcementHomeData.status==0){
                    if (enforcementHomeData.result.size()>0){
                        binding.notDataRl.setVisibility(View.GONE);
                        binding.recyclerview.setVisibility(View.VISIBLE);
                        enforcementMianAdapter.refreshDataList(enforcementHomeData.result);
                    }else {
                        binding.notDataRl.setVisibility(View.VISIBLE);
                        binding.recyclerview.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(requireActivity(),error));
            }
        });
    }

    private void  getAHIUserExMid(){
        HttpRequest.getAHIUserEXMid(requireActivity(), UserDataSP.getInstance().getUserInfo().Result.dependency.Name, new CallBackLis<>() {
            @Override
            public void onSuccess(String method, AHIUserEXBean ahiUserEXBean) {
                if (ahiUserEXBean.Status==0){
                    mid = ahiUserEXBean.Result.Mid;
                    getEnforcementListData();
                }
            }
            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(requireActivity(),error));
            }
        });
    }
}
