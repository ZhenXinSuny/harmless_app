package com.agridata.cdzhdj.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.agridata.cdzhdj.data.BankBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther 56454
 * @date 2022/6/27
 * @time 18:42.
 */
public class AssertDataUtil {

    public static List<BankBean> initBank(Context context) {
        // 解析Json数据
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open("bank.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str =  newstringBuilder .toString();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<BankBean>>() {
        }.getType();
        return  gson.fromJson(str, type);
    }
    public String getStringData(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public InputStreamReader getData(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(assetManager.open(fileName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStreamReader;
    }
}
