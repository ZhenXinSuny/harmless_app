package com.agridata.cdzhdj.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @auther 56454
 * @date 2022/6/24
 * @time 10:01.
 */
public class ImageLoaderUtils {
    public static boolean assertValidRequest(Context context) {
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !isDestroy(activity);
        } else if (context instanceof ContextWrapper){
            ContextWrapper contextWrapper = (ContextWrapper) context;
            if (contextWrapper.getBaseContext() instanceof Activity){
                Activity activity = (Activity) contextWrapper.getBaseContext();
                return !isDestroy(activity);
            }
        }
        return true;
    }

    private static boolean isDestroy(Activity activity) {
        if (activity == null) {
            return true;
        }
        return activity.isFinishing() || activity.isDestroyed();
    }




    /**
     * 保存图片
     *
     * @param bitmap
     * @param toPath
     * @return
     * @throws IOException
     */
    public static String saveBitmap(Bitmap bitmap, String toPath, boolean isRewrite) throws IOException {
        if (bitmap == null) {
            throw new IOException();
        }
        if (TextUtils.isEmpty(toPath) || toPath.endsWith("/")) {
            throw new IOException();
        }
        File destFile = new File(toPath);
        if (destFile.exists()) {
            if (isRewrite) {
                destFile.delete();
            } else {
                return toPath;
            }
        }
        destFile.createNewFile();
        OutputStream os = new FileOutputStream(destFile);
        String suffix = getFileSuffix(toPath);
        if (suffix != null && suffix.equalsIgnoreCase("png")) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        } else {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        }

        os.flush();
        os.close();
        return toPath;
    }


    /**
     * 获取文件后缀
     *
     * @param path
     * @return
     */
    public static String getFileSuffix(String path) {
        String suffix = null;
        if (path != null) {
            int index = path.lastIndexOf("/");
            if (index != -1) {
                String name = path.substring(index + 1);
                int suffixindex = name.lastIndexOf(".");
                if (suffixindex != -1) {
                    suffix = name.substring(suffixindex + 1);
                }
            }

        }
        return suffix;
    }

}
