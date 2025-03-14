package com.agridata.cdzhdj.activity.farm.pigepidemic.vaccinemanagement;

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
 * @Date : on 2023-05-09 16:14.
 * @Description :描述
 */
public class VaccineManagementMainActivity extends BaseActivity<ActivityMenuMainBinding> {

    private static final String TAG = "PigBreedingInputsMainActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;



    @Override
    protected ActivityMenuMainBinding getBinding() {
        return ActivityMenuMainBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, VaccineManagementMainActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.mainTitleTv.setText("疫苗管理");
        binding.titlebarMiddle.setText("疫苗管理");

    }

    @Override
    protected void initDatas() {
        setMenuUi();
    }
    private void setMenuUi() {
        setMenuData();
        GridLayoutManager layoutManager = new GridLayoutManager(VaccineManagementMainActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, VaccineManagementMainActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id){
                    case 1:
                        IssueXdrActivity.start(VaccineManagementMainActivity.this);
                        break;
                    case 2:
                        XdrCheckActivity.start(VaccineManagementMainActivity.this);
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
        MenuData menuData = new MenuData("防疫员签收发放", R.drawable.whh, 1);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("养殖户签收", R.drawable.zsgl, 2);
        mMenuDataList.add(menuData1);
    }
}
