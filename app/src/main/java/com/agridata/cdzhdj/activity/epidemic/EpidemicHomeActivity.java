package com.agridata.cdzhdj.activity.epidemic;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.agridata.cdzhdj.SPUtil.UserDataSP;
import com.agridata.cdzhdj.activity.epidemic.updata.UpDataInfoActivity;
import com.agridata.cdzhdj.activity.epidemic.xdr.CheckXdrListActivity;
import com.agridata.cdzhdj.activity.epidemic.xdr.ImmuneXdrListActivity;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.LoginData;
import com.agridata.cdzhdj.databinding.ActivityEpidemicHomeBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.network.utils.LogUtil;
import com.lzx.utils.RxToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/9/22
 * @time 17:28.
 */
public class EpidemicHomeActivity extends BaseActivity<ActivityEpidemicHomeBinding> implements View.OnClickListener {


    public static void start(Activity context) {
        Intent intent = new Intent(context, EpidemicHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityEpidemicHomeBinding getBinding() {
        return ActivityEpidemicHomeBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void initView() {
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.sjtbLl.setOnClickListener(this);
        binding.xdrLl.setOnClickListener(this);
        binding.yzzucxLl.setOnClickListener(this);
        binding.myLl.setOnClickListener(this);
        binding.xdrCheckLl.setOnClickListener(this);


        List<String> roleList = new ArrayList<>();
        for (LoginData.ResultBean.RolesBean role : UserDataSP.getInstance().getUserInfo().Result.roles) {
            roleList.add(role.id);
        }
        LogUtil.d("lzx-------》","角色ID" + roleList.toString());
        if (roleList.contains(AppConstants.FangYiYuanID)
                || roleList.contains(AppConstants.XZFYMaster)
                || roleList.contains(AppConstants.XianMaster)
                ||roleList.contains(AppConstants.ShiMaster)
                || roleList.contains(AppConstants.WHHXCJDY)) {
            binding.xdrCheckLl.setVisibility(View.VISIBLE);
            binding.xdrCheckLine.setVisibility(View.VISIBLE);
        } else {
            binding.xdrCheckLl.setVisibility(View.GONE);
            binding.xdrCheckLine.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initDatas() {
    }

    @Override
    public void onClick(View v) {
        if (v == binding.xdrLl) {
            ImmuneXdrListActivity.start(EpidemicHomeActivity.this,2);
        } else if (v == binding.myLl) {
            ImmuneMenuActivity.start(EpidemicHomeActivity.this);
        } else if (v == binding.yzzucxLl) {
            Objects.requireNonNull(RxToast.info(this, "功能正在研发中..."));
        } else if (v == binding.sjtbLl) {
           // Objects.requireNonNull(RxToast.info(this, "功能正在研发中..."));
            UpDataInfoActivity.start(EpidemicHomeActivity.this);
        }else  if (v ==  binding.xdrCheckLl){
            //审核
            CheckXdrListActivity.start(EpidemicHomeActivity.this,"xdr");
        }
    }
}
