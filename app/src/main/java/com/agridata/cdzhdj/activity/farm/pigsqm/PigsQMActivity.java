package com.agridata.cdzhdj.activity.farm.pigsqm;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.farm.pigsqm.handlercase.HandlerCaseActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.LawEnforcementCheckMainActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.data.law.AgencyData;
import com.agridata.cdzhdj.databinding.ActivityPigsQmBinding;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-30 14:07.
 * @Description :描述 生猪检疫管理
 */
public class PigsQMActivity extends BaseActivity<ActivityPigsQmBinding> {
    private static final String TAG = "PigsQMActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;


    @Override
    protected ActivityPigsQmBinding getBinding() {
        return ActivityPigsQmBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, PigsQMActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());


    }

    @Override
    protected void initDatas() {
        setMenuUi();
    }

    private void setMenuUi() {
        setMenuData();
        GridLayoutManager layoutManager = new GridLayoutManager(PigsQMActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, PigsQMActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id) {
                    case 1:
                        getAgencyList();
                        break;
                    case 2://执法办案
                        HandlerCaseActivity.start(PigsQMActivity.this,1);
                        break;
                    default:
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }

    private void getAgencyList() {
        HttpRequest.getAgencyData(PigsQMActivity.this, new CallBackLis<AgencyData>() {
            @Override
            public void onSuccess(String method, AgencyData agencyData) {
                if (agencyData.status == 0) {
                    List<String> mAgencyMidList = new ArrayList<>();
                    for (AgencyData.Result result : agencyData.result) {
                        mAgencyMidList.add(result.mid);
                    }
                    if (UserDataSP.getInstance().getUserInfo().Result.dependency != null) {
                        if (mAgencyMidList.contains(UserDataSP.getInstance().getUserInfo().Result.dependency.AgencyMID)) {
                            LawEnforcementCheckMainActivity.start(PigsQMActivity.this);
                        }else {
                            Objects.requireNonNull(RxToast.warning(PigsQMActivity.this, "当前账号不能进行执法检查"));
                        }
                    }
                }else {
                    Objects.requireNonNull(RxToast.warning(PigsQMActivity.this, "当前账号不能进行执法检查"));
                }
                LogUtil.d("lzx---------》", agencyData.toString());
            }

            @Override
            public void onFailure(String method, String error) {
                Objects.requireNonNull(RxToast.error(PigsQMActivity.this, error));
            }
        });
    }

    private void setMenuData() {
        mMenuDataList = new ArrayList<>();
        MenuData menuData = new MenuData("执法检查", R.drawable.whh, 1);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("执法办案", R.drawable.zsgl, 2);
        mMenuDataList.add(menuData1);

    }
}
