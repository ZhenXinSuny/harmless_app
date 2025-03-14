package com.agridata.cdzhdj.activity.farm.pigepidemic;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.epidemic.receiptrelease.EarTagOutActivity;
import com.agridata.cdzhdj.activity.epidemic.receiptrelease.EarTagSignActivity;
import com.agridata.cdzhdj.activity.farm.pigepidemic.makeuplater.MakeUpLaterActivity;
import com.agridata.cdzhdj.activity.farm.pigepidemic.vaccinemanagement.VaccineManagementMainActivity;
import com.agridata.cdzhdj.activity.farm.pigepidemic.vaccinevialrecovery.VaccineVialRecoveryWebViewActivity;
import com.agridata.cdzhdj.activity.farm.pigepidemic.map.MianYiMapActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.databinding.ActivityMenuMainBinding;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-05-09 16:14.
 * @Description :描述
 */
public class PigEpidemicMainActivity extends BaseActivity<ActivityMenuMainBinding> {

    private static final String TAG = "PigBreedingInputsMainActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;


    @Override
    protected ActivityMenuMainBinding getBinding() {
        return ActivityMenuMainBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, PigEpidemicMainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.mainTitleTv.setText("生猪防疫管理");
        binding.titlebarMiddle.setText("生猪防疫管理");
    }

    @Override
    protected void initDatas() {
        setMenuUi();
    }

    private void setMenuUi() {
        setMenuData();
        GridLayoutManager layoutManager = new GridLayoutManager(PigEpidemicMainActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, PigEpidemicMainActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id) {
                    case 1:
                        VaccineManagementMainActivity.start(PigEpidemicMainActivity.this);
                        break;
                    case 2://先打后补
                        MakeUpLaterActivity.start(PigEpidemicMainActivity.this);
                        break;
                    case 3://
                        EarTagOutActivity.start(PigEpidemicMainActivity.this);
                        break;
                    case 4://疫苗瓶回收
                        VaccineVialRecoveryWebViewActivity.start(PigEpidemicMainActivity.this, 1);
                        break;
                    case 5://耳标签收
                        EarTagSignActivity.start(PigEpidemicMainActivity.this);
                        break;
                    case 8://耳标签收
                        MianYiMapActivity.start(PigEpidemicMainActivity.this,1);
                        break;
                    case 9://耳标签收
                        MianYiMapActivity.start(PigEpidemicMainActivity.this,2);
                        break;
                    case 10://耳标签收
                        MianYiMapActivity.start(PigEpidemicMainActivity.this,3);
                        break;
                    case 11://免疫修改
                        MianYiMapActivity.start(PigEpidemicMainActivity.this,4);
                        break;
                    case 12://免疫体系评价
                        MianYiMapActivity.start(PigEpidemicMainActivity.this,5);
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

    private void setMenuData() {
        mMenuDataList = new ArrayList<>();
        MenuData menuData = new MenuData("疫苗管理", R.drawable.whh, 1);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("先打后补", R.drawable.zsgl, 2);
        mMenuDataList.add(menuData1);


        MenuData menuData2 = new MenuData("耳标发放养殖场户", R.drawable.ic_immune_fafang, 3);
        mMenuDataList.add(menuData2);

        MenuData menuData4 = new MenuData("耳标签收", R.drawable.ic_immune_qianshou, 5);
        mMenuDataList.add(menuData4);


        MenuData menuData3 = new MenuData("疫苗瓶回收", R.drawable.zsgl, 4);
        mMenuDataList.add(menuData3);
        MenuData menuData8 = new MenuData("免疫申报", R.drawable.zsgl, 8);
        mMenuDataList.add(menuData8);
        MenuData menuData5 = new MenuData("免疫受理", R.drawable.ic_immune_tianbao, 9);
        MenuData menuData6 = new MenuData("免疫驳回", R.drawable.ic_immune_updata, 10);
        mMenuDataList.add(menuData5);
        mMenuDataList.add(menuData6);
        MenuData menuData7 = new MenuData("免疫修改", R.drawable.whh, 11);
        mMenuDataList.add(menuData7);

        MenuData menuData12 = new MenuData("免疫体系评价", R.drawable.rccy, 12);
        mMenuDataList.add(menuData12);
    }
}
