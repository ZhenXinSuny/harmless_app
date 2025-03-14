package com.agridata.cdzhdj.activity.farm.map;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
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
 * @Date : on 2023-05-29 17:15.
 * @Description :描述
 */
public class ShengZhuYuFeiMainActivity extends BaseActivity<ActivityMenuMainBinding> {

    private static final String TAG = "BeastsBirdsMainActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;


    @Override
    protected ActivityMenuMainBinding getBinding() {
        return ActivityMenuMainBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, ShengZhuYuFeiMainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());

        binding.titlebarMiddle.setText("生猪育肥体系");
        binding.mainTitleTv.setText("生猪育肥体系");
    }

    @Override
    protected void initDatas() {
        setMenuUi();
    }

    private void setMenuUi() {
        setMenuData();
        GridLayoutManager layoutManager = new GridLayoutManager(ShengZhuYuFeiMainActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, ShengZhuYuFeiMainActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id) {
                    case 1:
                        ShengZhuYuFeiWebViewActivity.start(ShengZhuYuFeiMainActivity.this,1);
                        break;
                    case 2:
                        ShengZhuYuFeiWebViewActivity.start(ShengZhuYuFeiMainActivity.this,2);
                        break;
                    case 3:
                        ShengZhuYuFeiWebViewActivity.start(ShengZhuYuFeiMainActivity.this,3);
                        break;
                    case 4:
                        ShengZhuYuFeiWebViewActivity.start(ShengZhuYuFeiMainActivity.this,4);
                        break;
                    case 5:
                        ShengZhuYuFeiWebViewActivity.start(ShengZhuYuFeiMainActivity.this,5);
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
        MenuData menuData = new MenuData("仔猪管理", R.drawable.whh, 1);
        mMenuDataList.add(menuData);

        MenuData menuData1 = new MenuData("商品猪管理", R.drawable.rccy, 2);
        mMenuDataList.add(menuData1);

        MenuData menuData2 = new MenuData("保供场可视化", R.drawable.zfjd, 3);
        mMenuDataList.add(menuData2);

        MenuData menuData3 = new MenuData("直联直报", R.drawable.zsgl, 4);
        mMenuDataList.add(menuData3);

        MenuData menuData4 = new MenuData("档案查看", R.drawable.rccy, 5);
        mMenuDataList.add(menuData4);
    }
}
