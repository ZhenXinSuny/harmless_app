/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.agridata.cdzhdj.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.network.utils.LogUtil;

import java.io.File;

/**
 * @author 56454
 */
public class FileUtil {

    private static boolean deleted;

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic_card.jpg");
    }

    public static File getSaveFileBank(Context context) {
        return new File(context.getFilesDir(), "pic_bank.jpg");
    }


    public static boolean deleteFile(Context context) {
        // 获取外部存储的 Pictures 目录下的 "apply" 文件夹路径
        File applyDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "apply");
        // 检查该目录是否存在
        if (applyDir.exists() && applyDir.isDirectory()) {
            // 获取该目录下的所有文件
            File[] files = applyDir.listFiles();
            if (files != null && files.length>0) {
                // 遍历文件数组
                LogUtil.d("lzx-----》","files" + files.length);
                for (File file : files) {
                    // 检查是否为图片文件，这里简单通过文件扩展名判断，实际可根据需要调整判断逻辑
                    if (file.isFile() && (file.getName().toLowerCase().endsWith(".jpg") || file.getName().toLowerCase().endsWith(".png") || file.getName().toLowerCase().endsWith(".jpeg"))) {
                        // 尝试删除文件
                        deleted = file.delete();
                    }
                }
            } else {
                deleted = true;
                LogUtil.d("lzx-----》","没有文件");
            }
        } else {
            deleted = true;
            LogUtil.d("lzx-----》","没有文件夹");
        }

        return deleted;
    }
}
