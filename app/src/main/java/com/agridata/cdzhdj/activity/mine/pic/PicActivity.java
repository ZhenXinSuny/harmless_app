package com.agridata.cdzhdj.activity.mine.pic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.databinding.ActivityHeadBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luck.picture.lib.photoview.OnPhotoTapListener;

/**
 * @auther 56454
 * @date 2022/7/8
 * @time 17:12.
 */
public class PicActivity  extends BaseActivity<ActivityHeadBinding> {


    private String PicUrl;
    public static void start(Activity context, String pic_url) {
        Intent intent = new Intent(context, PicActivity.class);
        Bundle data = new Bundle();
        data.putString("pic_url", pic_url);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }


    private void getArguments() {
        Bundle data = getIntent().getBundleExtra("data");
        this.PicUrl = data.getString("pic_url");
    }
    @Override
    protected ActivityHeadBinding getBinding() {
        return ActivityHeadBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        getArguments();



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }



        Glide.with(PicActivity.this)
                .load(PicUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.groupHeadPv);
        binding.groupHeadPv.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                PicActivity.this.finish();
            }
        });
    }

    @Override
    protected void initDatas() {

    }
}
