package com.agridata.util;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DateTimeUtils {

    /**
     * 日期格式转换yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ) TO  yyyy-MM-dd HH:mm:ss
     * 2020-04-09T23:00:00.000+08:00 TO 2020-04-09 23:00:00
     * @throws ParseException
     */
    public static String dealDateFormat(String oldDateStr) throws ParseException{
        DateFormat df = null;  //yyyy-MM-dd'T'HH:mm:ss.SSSZ
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        }
        Date date = df.parse(oldDateStr);
        SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
        Date date1 =  df1.parse(date.toString());
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df2.format(date1);
    }

    /**
     * 日期格式转换 yyyy-MM-dd HH:mm:ss  TO yyyy-MM-dd'T'HH:mm:ss.SSSXXX  (yyyy-MM-dd'T'HH:mm:ss.SSSZ)
     * 2020-04-09 23:00:00 TO 2020-04-09T23:00:00.000+08:00
     * @throws ParseException
     */
    public static String dealDateFormatReverse(String oldDateStr) throws ParseException{
        DateFormat df = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        }
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 =  df2.parse(oldDateStr);
        return df.format(date1);
    }

    /**
     * 获取系统当前时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    /**
     * 将dateTime类型转换为String类型
     *
     * @param dateTime   日期
     * @param dateFormat 日期格式，默认：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String dateToString(long dateTime, String dateFormat) {
        if (TextUtils.isEmpty(dateFormat)) {
            dateFormat = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date(dateTime));
    }

    /**
     * 判断指定时间和当前时间是否超过1天
     *
     * @param date
     *
     * @return
     */
    public static boolean isDay(long date) {
        String str1 = dateToString(date, "yyyy-MM-dd HH:mm:ss");
        String str2 = dateToString(getCurrentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        return getContrastTime(str1, str2);
    }

    public static boolean getContrastTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        boolean isDay;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            }
            else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day >= 1) {
            isDay = true;
        }
        else {
            isDay = false;
        }
        return isDay;
    }

    /**
     * 判断指定时间和当前时间是否超过1天
     *
     * @param date
     *
     * @return
     */
    public static boolean isFiveDay(long date) {
        String str1 = dateToString(date, "yyyy-MM-dd HH:mm:ss");
        String str2 = dateToString(getCurrentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        return getContrastTime(str1, str2);
    }

    public static boolean getContrastTimeFive(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        boolean isDay;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            }
            else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000 * 5);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day >= 1) {
            isDay = true;
        }
        else {
            isDay = false;
        }
        return isDay;
    }

    /**
     * 将String类型转换为dateTime类型
     *
     * @param dateStr    时间字符串
     * @param dateFormat 日期格式，默认：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static Date stringToDate(String dateStr, String dateFormat) {
        try {
            if (TextUtils.isEmpty(dateFormat)) {
                dateFormat = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    public static  String  getNowTimes(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strTimes  = formatter.format(curDate);
        return  strTimes;
    }

    public static  String  getCollectionDanHao(){


        Random random = new Random();
        // 生成一个 6 位的随机数
        int randomNumber = random.nextInt(900000) + 100000;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strTimes  = formatter.format(curDate);
        return  "SJ-"+ strTimes+"-"+randomNumber;
    }

    public static int getSixNum(){
        Random random = new Random();
        // 生成一个 6 位的随机数
        return random.nextInt(900000) + 100000;
    }
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getSevenDaysAgoTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -3); // 减去七天
        Date sevenDaysAgo = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(sevenDaysAgo);
    }


    public static boolean isStartTimeBeforeEndTime(String startTime, String endTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()); // 假设时间格式为HH:mm
        try {
            Date startDate = dateFormat.parse(startTime);
            Date endDate = dateFormat.parse(endTime);
            return startDate.getTime() < endDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  static  void   certATimes(){
        String certificateIssueTimeStr = "2024-05-29 10:30:00"; // 开证时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            LocalDateTime issueTime = LocalDateTime.parse(certificateIssueTimeStr, formatter);
            LocalDateTime threeDaysLater = issueTime.plusDays(4);
            System.out.println("threeDaysLater"+ threeDaysLater.format(formatter));

            // 获取当前时间
            LocalDateTime now = LocalDateTime.now();

            System.out.println("now"+ now.format(formatter));
            // 判断是否超过当前时间

            System.out.println("threeDaysLater.isAfter(now)"+ threeDaysLater.isAfter(now));
            if (!threeDaysLater.isAfter(now)) {
                System.out.println("证书有效期未过期");
            } else {
                System.out.println("证书已过期");

            }
        } catch (Exception e) {
            System.err.println("错误解析日期时间：" + e.getMessage());
        }
    }

}
