package com.agridata.cdzhdj.activity.farm.pigepidemic.makeuplater;

import android.content.Context;
import android.content.Intent;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityMakeUpLaterApplyBinding;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-06-21 10:30.
 * @Description :描述
 */
public class MakeUpLaterApplyActivity extends BaseActivity<ActivityMakeUpLaterApplyBinding> {


    public static void start(Context context) {
        Intent intent = new Intent(context, MakeUpLaterApplyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityMakeUpLaterApplyBinding getBinding() {
        return ActivityMakeUpLaterApplyBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

        binding.checkBtn.setOnClickListener(v -> {
                binding.checkTv.setText("已申请");
                binding.checkTv.setBackground(getDrawable(R.drawable.textview_border));
        });


    }

    @Override
    protected void initDatas() {

    }
}
