package com.agridata.cdzhdj.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.network.utils.LogUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.CompletableFuture;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-04-29 11:54.
 * @Description :描述
 */
public class ImageLoader {
    private static File fileImage;


    public static CompletableFuture<String> loadImageAndSave(final Context context, final String imageUrl, String type, String mid) {
        final CompletableFuture<String> future = new CompletableFuture<>();

        if (imageUrl == null || imageUrl.isEmpty()) {
            future.complete(null);
        } else {
            int width = 200;
            int height = 200;
          // 创建 RequestOptions 对象，并设置图片的大小
            RequestOptions options = new RequestOptions().override(width, height);

            Glide.with(context).asBitmap().load(UrlUtils.pic_url + imageUrl).apply(options).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                    int width = resource.getWidth();
                    int height = resource.getHeight();
                    Bitmap bitmap = compressQuality(resource);
                    String localPath = saveToExternalStorage(context, bitmap, type, mid);
                    future.complete(localPath);

                }
            });
        }

        return future;
    }


    public static String saveToExternalStorage(Context context, Bitmap bitmap, String type, String mid) {
        File directory = MyApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (directory.exists() || directory.mkdir()) {
            File file = new File(directory, "apply");
            if (file.exists() || file.mkdir()) {
                fileImage = new File(file, type + mid + ".png");
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return fileImage.getAbsolutePath();
    }

    public static String saveToExternalStorageCollection(Context context, Bitmap bitmap, String type, String mid) {
        File directory = MyApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (directory.exists() || directory.mkdir()) {
            File file = new File(directory, "collection");
            if (file.exists() || file.mkdir()) {
                fileImage = new File(file, type + mid + ".png");
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileImage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return fileImage.getAbsolutePath();
    }

    public static Bitmap convertFileToBitmap(String filePath) {
        //将图片转为bitmap
        Bitmap fileBitmap = BitmapFactory.decodeFile(filePath, getBitmapOption(2));
        //将转为bitmap的图片压缩
        return compressQuality(fileBitmap);
    }


    /**
     * 解决图片太大转bitmap对象会出问题
     *
     * @param inSampleSize
     * @return
     */
    public static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressQuality(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 90;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            LogUtil.i("图片循环压缩--->" + baos.toByteArray().length / 1024);
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 将文件大小转换成字节
     *
     * @param fSize
     * @return
     */
    public static String formatFileSize(long fSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fSize < 1024) {
            fileSizeString = df.format((double) fSize) + "B";
        } else if (fSize > 104875) {
            fileSizeString = df.format((double) fSize / 1024) + "K";
        } else if (fSize > 1073741824) {
            fileSizeString = df.format((double) fSize / 104875) + "M";
        } else {
            fileSizeString = df.format((double) fSize / 1073741824) + "G";
        }
        return fileSizeString;

    }

    /**
     * 获取文件大小
     *
     * @param f
     * @return
     */
    public static long getFileSizes(File f) {
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(f);
                s = fis.available();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("文件不存在");
        }
        return s;

    }
}

