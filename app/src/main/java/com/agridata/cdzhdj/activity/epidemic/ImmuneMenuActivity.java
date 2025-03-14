package com.agridata.cdzhdj.activity.epidemic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.epidemic.immune.ImmuneActivity;
import com.agridata.cdzhdj.activity.epidemic.immune.patch.ImmunePatchCodeActivity;
import com.agridata.cdzhdj.activity.epidemic.immune.query.QueryImmuneActivity;
import com.agridata.cdzhdj.activity.epidemic.immune.updata.UpDataImmuneActivity;
import com.agridata.cdzhdj.activity.epidemic.receiptrelease.EarTagOutActivity;
import com.agridata.cdzhdj.activity.epidemic.receiptrelease.EarTagSignActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.databinding.ActivityImmuneMenuBinding;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2022-12-13 09:31.
 * @Description :描述  防疫信息主界面
 */
public class ImmuneMenuActivity extends BaseActivity<ActivityImmuneMenuBinding> {

    private List<MenuData> mMenuDataList;
    private final static String TAG = "ImmuneMenuActivity------》";
    private MainMenuAdapter mainMenuAdapter;

    public static void start(Activity context) {
        Intent intent = new Intent(context, ImmuneMenuActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityImmuneMenuBinding getBinding() {
        return ActivityImmuneMenuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        setMenuData();

        setMenuUi();
    }

    @Override
    protected void initDatas() {

    }

    private void setMenuData() {
        mMenuDataList = new ArrayList<>();
        MenuData menuData = new MenuData("防疫信息填报", R.drawable.ic_immune_tianbao, 4);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("防疫信息查询", R.drawable.ic_immune_query, 5);
        MenuData menuData2 = new MenuData("防疫信息修改", R.drawable.ic_immune_updata, 6);
        MenuData menuData3 = new MenuData("耳标签收", R.drawable.ic_immune_qianshou, 7);
        mMenuDataList.add(menuData1);
        mMenuDataList.add(menuData2);
        mMenuDataList.add(menuData3);
        MenuData menuData4 = new MenuData("耳标发放", R.drawable.ic_immune_fafang, 8);
        mMenuDataList.add(menuData4);
        MenuData menuData5 = new MenuData("补戴标", R.drawable.jylcyx, 9);
        mMenuDataList.add(menuData5);
    }


    private void setMenuUi() {
        GridLayoutManager layoutManager = new GridLayoutManager(ImmuneMenuActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, ImmuneMenuActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);
        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                if (data.id == 4) {
                    ImmuneActivity.start(ImmuneMenuActivity.this);
                } else if (data.id == 5) {
                    QueryImmuneActivity.start(ImmuneMenuActivity.this);
                } else if (data.id == 6) {
                    UpDataImmuneActivity.start(ImmuneMenuActivity.this);
                } else if (data.id == 7) {
                    EarTagSignActivity.start(ImmuneMenuActivity.this);
                } else if (data.id == 8) {
                    EarTagOutActivity.start(ImmuneMenuActivity.this);
                } else if (data.id == 9) {
                    ImmunePatchCodeActivity.Companion.start(ImmuneMenuActivity.this);
                }
            }

            @Override
            public boolean onItemLongClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                return false;
            }
        });
    }
}
