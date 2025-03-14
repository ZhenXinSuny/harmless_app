package com.agridata.util;


import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Base64Util {
    private static final String UTF_8 = "UTF-8";
    private static final String TAG = "Base64";

    /**
     * 对给定的字符串进行base64解码操作
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decodeData(String inputData) throws UnsupportedEncodingException {
        final Base64.Decoder decoder = Base64.getDecoder();
        return  new String(decoder.decode(inputData), "UTF-8");
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encodeData(String inputData) throws UnsupportedEncodingException {
        final Base64.Encoder encoder = Base64.getEncoder();
//编码
        final String encodedText = encoder.encodeToString(inputData.getBytes(UTF_8));
        System.out.println(encodedText);
        return  encodedText;
    }

}
