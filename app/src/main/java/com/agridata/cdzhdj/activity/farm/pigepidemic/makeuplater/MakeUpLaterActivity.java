package com.agridata.cdzhdj.activity.farm.pigepidemic.makeuplater;

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
 * @Date : on 2023-06-16 15:47.
 * @Description :描述 先打后补
 */
public class MakeUpLaterActivity extends BaseActivity<ActivityMenuMainBinding> {

    private static final String TAG = "MakeUpLaterActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;



    @Override
    protected ActivityMenuMainBinding getBinding() {
        return ActivityMenuMainBinding.inflate(getLayoutInflater());
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, MakeUpLaterActivity.class);
        context.startActivity(intent);
    }



    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.mainTitleTv.setText("先打后补");
        binding.titlebarMiddle.setText("先打后补");

    }

    @Override
    protected void initDatas() {
        setMenuUi();
    }
    private void setMenuUi() {
        setMenuData();
        GridLayoutManager layoutManager = new GridLayoutManager(MakeUpLaterActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, MakeUpLaterActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id){
                    case 1:
                         MakeUpLaterApplyActivity.start(MakeUpLaterActivity.this);
                        break;
                    case 99:
                        MakeUpLaterCheckActivity.start(MakeUpLaterActivity.this);
                        break;
                    case 2:
                        MakeUpLaterWebViewActivity.start(MakeUpLaterActivity.this,2);
                        break;
                    case 3:
                        MakeUpLaterWebViewActivity.start(MakeUpLaterActivity.this,3);
                        break;
                    case 4://台账
                         MakeUpLaterWebViewActivity.start(MakeUpLaterActivity.this,4);
                        break;
                    case 5:
                        MakeUpLaterWebViewActivity.start(MakeUpLaterActivity.this,5);
                        break;
                    case 6:
                        MakeUpLaterWebViewActivity.start(MakeUpLaterActivity.this,6);
                        break;
                    case 7:
                        MakeUpLaterWebViewActivity.start(MakeUpLaterActivity.this,7);
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
        MenuData menuData = new MenuData("先打后补申请", R.drawable.whh, 1);
        mMenuDataList.add(menuData);
        MenuData menuData99 = new MenuData("先打后补审核", R.drawable.zfjd, 99);
        mMenuDataList.add(menuData99);

        MenuData menuData1 = new MenuData("疫苗采购", R.drawable.zsgl, 2);
        mMenuDataList.add(menuData1);
        MenuData menuData2 = new MenuData("疫苗销售", R.drawable.rccy, 3);
        mMenuDataList.add(menuData2);
        MenuData menuData3 = new MenuData("销售台账", R.drawable.jylcyx, 4);
        mMenuDataList.add(menuData3);
        MenuData menuData4 = new MenuData("防疫信息填报", R.drawable.whh, 5);
        mMenuDataList.add(menuData4);
        MenuData menuData5 = new MenuData("免疫效果评价上传", R.drawable.zfjd, 6);
        mMenuDataList.add(menuData5);
        MenuData menuData6 = new MenuData("补助申请", R.drawable.rccy, 7);
        mMenuDataList.add(menuData6);
    }
}
