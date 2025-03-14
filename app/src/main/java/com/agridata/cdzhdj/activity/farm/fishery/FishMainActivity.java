package com.agridata.cdzhdj.activity.farm.fishery;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.farm.fishery.banfishing.BanFishingMainActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.databinding.ActivityFishMainBinding;
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
public class FishMainActivity extends BaseActivity<ActivityFishMainBinding> {

    private static final String TAG = "PigBreedingInputsMainActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;



    @Override
    protected ActivityFishMainBinding getBinding() {
        return ActivityFishMainBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, FishMainActivity.class);
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
        GridLayoutManager layoutManager = new GridLayoutManager(FishMainActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, FishMainActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id){
                    case 1:
                        BanFishingMainActivity.start(FishMainActivity.this);
                        break;
                    case 2://资源养护

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
        MenuData menuData = new MenuData("禁渔", R.drawable.whh, 1);
        mMenuDataList.add(menuData);
//        MenuData menuData1 = new MenuData("资源养护", R.drawable.zsgl, 2);
//        mMenuDataList.add(menuData1);
    }
}
