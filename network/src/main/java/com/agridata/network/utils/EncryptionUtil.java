package com.agridata.network.utils;

import android.os.Build;

import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-12-27 17:19.
 * @Description :描述
 */
public class EncryptionUtil {
    // HMACSHA1 签名方法
    public static String generateHmacSHA1(String data, String secret) {
        try {
            // 使用 HMAC-SHA1 算法
            SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(keySpec);
            // 计算签名
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            // 使用 Base64 编码输出签名结果
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(rawHmac);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
