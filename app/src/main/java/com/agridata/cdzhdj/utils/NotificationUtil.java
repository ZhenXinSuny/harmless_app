package com.agridata.cdzhdj.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.MyApplication;
import com.agridata.network.utils.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2024-05-28 13:52.
 * @Description :描述
 */
public class NotificationUtil {
    /**
     * 小米手机创建通知信息并创建角标
     * @param context
     * @param num      */
    public  static  void setXiaoMiBadgeNum(Context context, int num) {
        try{
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String title = "消息提示";
            String desc = "您有" + num + "条未读消息";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                String channelId = "default";
                String channelName = "默认通知";
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setShowBadge(true);
                notificationManager.createNotificationChannel(channel);
            }
            Notification notification = new NotificationCompat.Builder(context, "default")
                    .setContentTitle(title)
                    .setContentText(desc)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.old_applogo_pic)
                    .setAutoCancel(true)
                    .setChannelId("default")
                    .setNumber(num)
                    .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                    .build();
            //取消掉上一条通知消息
            notificationManager.cancel(1);
            notificationManager.notify(1, notification);
            ShortcutBadger.applyNotification(MyApplication.getContext(), notification, 12);
            LogUtil.d("lzx----》","G0G0G0G0G0");

        }catch (Exception e){
            LogUtil.d("lzx----》",e.toString());
            e.printStackTrace();
        }
    }




}
