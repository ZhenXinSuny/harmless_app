package com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.report.LawEnforcementCheckListActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.reviewassignment.ReviewAssignmentMainActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.spot.LawReportSpotCheckMainActivity;
import com.agridata.cdzhdj.activity.farm.pigsqm.enforcementcheck.todolist.ToDoListMainActivity;
import com.agridata.cdzhdj.activity.adapter.MainMenuAdapter;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.MenuData;
import com.agridata.cdzhdj.data.law.CheckTjData;
import com.agridata.cdzhdj.databinding.ActivityLawenforcementCheckMainBinding;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewAdapter;
import com.agridata.cdzhdj.view.recyclerview.BaseRecyclerViewHolder;
import com.agridata.network.listener.CallBackLis;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-03-28 13:51.
 * @Description :描述 执法检查
 */
public class LawEnforcementCheckMainActivity extends BaseActivity<ActivityLawenforcementCheckMainBinding> {
    private static final String TAG = "LawEnforcementCheckMainActivity---->";
    private MainMenuAdapter mainMenuAdapter;
    private List<MenuData> mMenuDataList;

    private OptionsPickerView zhiwuPicker;

    @Override
    protected ActivityLawenforcementCheckMainBinding getBinding() {
        return ActivityLawenforcementCheckMainBinding.inflate(getLayoutInflater());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, LawEnforcementCheckMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        getCheckTj();
//        if (UserDataSP.getInstance().getUserInfo().Result.dependency.IsMaster==1){
//            binding.nameTv.setText("姓名:" + UserDataSP.getInstance().getUserInfo().Result.name);
//            binding.zhiwuTv.setText("职务:" + "支队长");
//            binding.zhanghaoTv.setText("账号:" + UserDataSP.getInstance().getUserInfo().Result.mobile);
//        }
        if ("18583669118".equals(UserDataSP.getInstance().getUserInfo().Result.mobile)) {
            binding.nameTv.setText("姓名:" + UserDataSP.getInstance().getUserInfo().Result.name);
            binding.zhiwuTv.setText("职务:" + "总支队长");
            binding.zhanghaoTv.setText("账号:" + UserDataSP.getInstance().getUserInfo().Result.mobile);
        } else if ("18582859118".equals(UserDataSP.getInstance().getUserInfo().Result.mobile)) {
            binding.nameTv.setText("姓名:" + UserDataSP.getInstance().getUserInfo().Result.name);
            binding.zhiwuTv.setText("职务:" + "副支队长");
            binding.zhanghaoTv.setText("账号:" + UserDataSP.getInstance().getUserInfo().Result.mobile);
        } else if ("13880280788".equals(UserDataSP.getInstance().getUserInfo().Result.mobile)) {
            binding.nameTv.setText("姓名:" + UserDataSP.getInstance().getUserInfo().Result.name);
            binding.zhiwuTv.setText("职务:" + "总支队长");
            binding.zhanghaoTv.setText("账号:" + UserDataSP.getInstance().getUserInfo().Result.mobile);
        } else if ("13739467372".equals(UserDataSP.getInstance().getUserInfo().Result.mobile)) {
            binding.nameTv.setText("姓名:" + UserDataSP.getInstance().getUserInfo().Result.name);
            binding.zhiwuTv.setText("职务:" + "副总支队长");
            binding.zhanghaoTv.setText("账号:" + UserDataSP.getInstance().getUserInfo().Result.mobile);
        } else {
            binding.nameTv.setText("姓名:" + UserDataSP.getInstance().getUserInfo().Result.name);
            binding.zhiwuTv.setText("职务:暂无");
            binding.zhanghaoTv.setText("账号:" + UserDataSP.getInstance().getUserInfo().Result.mobile);
        }

        binding.chooseZhiwu.setOnClickListener(v -> {
            showDialog();
        });
    }

    private void getCheckTj() {
        HttpRequest.getCheckTj(LawEnforcementCheckMainActivity.this, new CallBackLis<CheckTjData>() {
            @Override
            public void onSuccess(String method, CheckTjData content) {
                if (content.code == 200) {
                    binding.checkMainTv.setText("检查主体：" + content.data.zhuti);
                    binding.personCountTv.setText("出动人员次数(人次)：" + content.data.chudongrenyuancishu);
                    binding.checkCountTv.setText("检查次数：" + content.data.jianchacishu);
                    binding.fuchaCountTv.setText("复查完成次数：" + content.data.fuchawanchengcishu);
                    binding.daiFuchaCountTv.setText("待完成复查数：" + content.data.daiwanchengfuchashu);
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    @Override
    protected void initDatas() {
        setMenuUi();
    }

    private void setMenuUi() {
        setMenuData();
        GridLayoutManager layoutManager = new GridLayoutManager(LawEnforcementCheckMainActivity.this, 3);
        binding.recyclerview.setLayoutManager(layoutManager);
        mainMenuAdapter = new MainMenuAdapter(R.layout.item_menu, mMenuDataList, LawEnforcementCheckMainActivity.this, layoutManager);
        binding.recyclerview.setAdapter(mainMenuAdapter);
        mainMenuAdapter.refreshDataList(mMenuDataList);

        mainMenuAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BaseRecyclerViewHolder viewHolder, int position) {
                MenuData data = mainMenuAdapter.getData(position);
                switch (data.id) {
                    case 1:
                        LawEnforcementCheckListActivity.start(LawEnforcementCheckMainActivity.this);
                        break;
                    case 2:
                        LawReportSpotCheckMainActivity.start(LawEnforcementCheckMainActivity.this);
                        break;
                    case 3:
                        ToDoListMainActivity.start(LawEnforcementCheckMainActivity.this);
                        break;
                    case 4:
                        ReviewAssignmentMainActivity.start(LawEnforcementCheckMainActivity.this);
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
        MenuData menuData = new MenuData("执法检查", R.drawable.whh, 1);
        mMenuDataList.add(menuData);
        MenuData menuData1 = new MenuData("执法抽查", R.drawable.zsgl, 2);
        mMenuDataList.add(menuData1);
        MenuData menuData3 = new MenuData("待办事项", R.drawable.jylcyx, 3);
        mMenuDataList.add(menuData3);
        if (UserDataSP.getInstance().getUserInfo().Result.dependency.IsMaster == 1) {
            MenuData menuData4 = new MenuData("复查指派", R.drawable.zfjd, 4);
            mMenuDataList.add(menuData4);
        }

    }

    private void showDialog() {

        List<String> listString = new ArrayList<>();
        listString.add("一支队");
        listString.add("二支队");
        listString.add("三支队");
        listString.add("四支队");
        listString.add("五支队");
        listString.add("六支队");
        listString.add("七支队");
        zhiwuPicker = new OptionsPickerBuilder(this, (options1, option2, options3, v) -> {
            binding.chooseZhiwu.setText(listString.get(options1));

        })
                .setLayoutRes(R.layout.custom_unit_select, v -> {
                    final TextView iv_cancel = (TextView) v.findViewById(R.id.iv_cancel);
                    final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    final TextView title_tv = (TextView) v.findViewById(R.id.title_tv);
                    title_tv.setText("职务选择");
                    tvSubmit.setOnClickListener(v1 -> {
                        zhiwuPicker.returnData();
                        zhiwuPicker.dismiss();
                    });
                    iv_cancel.setOnClickListener(v12 -> zhiwuPicker.dismiss());

                })
                .isDialog(false)
                .setContentTextSize(18)//滚轮文字大小
                .setLineSpacingMultiplier(2.6f)
                .build();
        zhiwuPicker.setPicker(listString);//添加数据
        zhiwuPicker.show();
    }
}
