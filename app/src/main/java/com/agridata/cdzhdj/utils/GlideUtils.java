package com.agridata.cdzhdj.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agridata.cdzhdj.R;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.utils.ActivityCompatHelper;

import java.io.ByteArrayOutputStream;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-22 09:50.
 * @Description :描述
 */
public class GlideUtils {


    private String imageBase64;

    private GlideUtils() {
    }

    private static final class InstanceHolder {
        static final GlideUtils instance = new GlideUtils();
    }

    public static GlideUtils createGlideEngine() {
        return GlideUtils.InstanceHolder.instance;
    }


    /**
     * 加载常规图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadImage(Context context, String url, ImageView imageView) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context).load(url).centerCrop().into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param imageView
     * @param radius
     */
    public void loadRounderImage(Context context, String url, ImageView imageView, int radius) {
        if (!ActivityCompatHelper.assertValidRequest(context)) {
            return;
        }
        Glide.with(context).load(url).error(R.drawable.ic_default_iv).placeholder(R.drawable.ic_default_iv).apply(RequestOptions.bitmapTransform(new RoundedCorners(radius))).into(imageView);
    }



}
