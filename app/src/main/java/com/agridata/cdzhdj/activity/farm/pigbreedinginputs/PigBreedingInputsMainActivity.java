package com.agridata.cdzhdj.activity.farm.pigbreedinginputs;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.feed.FeedStorageActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.goodsout.GoodsOutActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.inventory.GoodsInventoryActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.statistics.GoodsStatisticsActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.veterinarydrug.VeterinaryDrugActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.Inputgoodsreceived.CheckGoodsReceivedActivity;
import com.agridata.cdzhdj.activity.farm.pigbreedinginputs.Inputgoodsreceived.InputGoodsReceivedActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.databinding.ActivityPigBreedingInputsMainBinding;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-04-26 10:27.
 * @Description :描述
  生猪养殖投入品
 */
public class PigBreedingInputsMainActivity extends BaseActivity<ActivityPigBreedingInputsMainBinding> {

  private static final String TAG = "PigBreedingInputsMainActivity---->";
  private MainMenuAdapter mainMenuAdapter;
  private List<MenuData> mMenuDataList;



  @Override
  protected ActivityPigBreedingInputsMainBinding getBinding() {
    return ActivityPigBreedingInputsMainBinding.inflate(getLayoutInflater());
  }


  public static void start(Context context) {
    Intent intent = new Intent(context, PigBreedingInputsMainActivity.class);
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
    GridLayoutManager layoutManager = new GridLayoutManager(PigBreedingInputsMainActivity.this, 3);
    binding.recyclerview.setLayoutManager(layoutManager);
    mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, PigBreedingInputsMainActivity.this, layoutManager);
    binding.recyclerview.setAdapter(mainMenuAdapter);
    mainMenuAdapter.refreshDataList(mMenuDataList);

    mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
        MenuData data = mainMenuAdapter.getData(position);
        switch (data.id){
          case 1:
//            FeedStorageMainActivity.start(PigBreedingInputsMainActivity.this);
            FeedStorageActivity.start(PigBreedingInputsMainActivity.this);
            break;
          case 2:
            VeterinaryDrugActivity.start(PigBreedingInputsMainActivity.this);
            break;
          case 3:
            GoodsOutActivity.start(PigBreedingInputsMainActivity.this);
            break;
          case 4:
            GoodsStatisticsActivity.start(PigBreedingInputsMainActivity.this);
            break;
          case 5:
            GoodsInventoryActivity.start(PigBreedingInputsMainActivity.this);
            break;
          case 6:
            InputGoodsReceivedActivity.start(PigBreedingInputsMainActivity.this);
            break;
          case  7:
            CheckGoodsReceivedActivity.start(PigBreedingInputsMainActivity.this);
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
    MenuData menuData = new MenuData("饲料入库", R.drawable.whh, 1);
    mMenuDataList.add(menuData);
    MenuData menuData1 = new MenuData("兽药入库", R.drawable.zsgl, 2);
    mMenuDataList.add(menuData1);
    MenuData menuData2 = new MenuData("物品出库", R.drawable.rccy, 3);
    mMenuDataList.add(menuData2);

    MenuData menuData6 = new MenuData("物品统计", R.drawable.jylcyx, 4);
    mMenuDataList.add(menuData6);

    MenuData menuData3 = new MenuData("物品盘点", R.drawable.jylcyx, 5);
    mMenuDataList.add(menuData3);

    MenuData menuData4 = new MenuData("投入品领用申请", R.drawable.jylcyx, 6);
    mMenuDataList.add(menuData4);

    MenuData menuData5 = new MenuData("投入品领用审批", R.drawable.jylcyx, 7);
    mMenuDataList.add(menuData5);

  }
}
