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
public class MakeUpLaterCheckActivity extends BaseActivity<ActivityMakeUpLaterApplyBinding> {


    public static void start(Context context) {
        Intent intent = new Intent(context, MakeUpLaterCheckActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected ActivityMakeUpLaterApplyBinding getBinding() {
        return ActivityMakeUpLaterApplyBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        binding.titlebarMiddle.setText("先打后补审核");
        binding.checkBtn.setText("先打后补审核");
        binding.checkTv.setText("未审核");
        binding.checkBtn.setOnClickListener(v -> {
                binding.checkTv.setText("已审核");
                binding.checkTv.setBackground(getDrawable(R.drawable.textview_border));
        });


    }

    @Override
    protected void initDatas() {

    }
}
