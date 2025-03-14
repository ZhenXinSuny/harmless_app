package com.agridata.cdzhdj.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.agridata.network.utils.LogUtil;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeDialogUtils {
    public  static TimePickerView pvTime,pvTime1,pvTime3;
    private static Date time;
    private static Calendar selectedDate1;
    private static String queryTimes;



    public  static  void initTimePicker3(Context context, TextView textView) {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);
        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);
        SimpleDateFormat formatter_day = new SimpleDateFormat("dd");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);
        Calendar selectedDate = Calendar.getInstance();//系统当前时间

        Date time = selectedDate.getTime();
        selectedDate.setTime(getTimeSevenDay());//设置选中的默认时间


        LogUtil.d("lzx---》" , getTime(getTimeSevenDay()));
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, mouth_int - 1, day_int);
        LogUtil.d("lzx---》" , mouth_int-1 +"");
        Calendar endDate = Calendar.getInstance();
        // endDate.set(year_int+10, mouth_int - 1, day_int);
        pvTime3 = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTime(date));
                LogUtil.d("lzx---》","当前选中的时间" + date.getDate());
                LogUtil.d("lzx---》","当前选中的时间" + getTime(date));
            }
        })       .setTitleSize(20)
                .setTitleText("开始时间")//标题
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.submit_tv);
                        TextView ivCancel = (TextView) v.findViewById(R.id.cancel_tv);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime3.returnData();
                                pvTime3.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime3.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .setTextXOffset(-40, -40, -40, -40, -40, -40)
                .isCenterLabel(false)
                .setLineSpacingMultiplier(2.f)
                .setTitleColor(R.color.black)
                .setDividerColor(context.getResources().getColor(R.color.D1))
                .setDate(selectedDate)
                .isDialog(false)
                .isCyclic(false)
                .setRangDate(startDate, endDate)
                .build();
    }















    public  static  void initTimePicker1(Context context, TextView textView) {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);
        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);
        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);
        Calendar selectedDate = Calendar.getInstance();//系统当前时间

        Date time = selectedDate.getTime();
        selectedDate.setTime(getTimeOneMonth());//设置选中的默认时间


        LogUtil.d("lzx---》" , getTime(getTimeOneMonth()));
        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, mouth_int - 1, day_int);
        LogUtil.d("lzx---》" , mouth_int-1 +"");
        Calendar endDate = Calendar.getInstance();
        // endDate.set(year_int+10, mouth_int - 1, day_int);
        pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTime(date));
                LogUtil.d("lzx---》","当前选中的时间" + date.getDate());
                LogUtil.d("lzx---》","当前选中的时间" + getTime(date));
            }
        })       .setTitleSize(20)
                .setTitleText("开始时间")//标题
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.submit_tv);
                        TextView ivCancel = (TextView) v.findViewById(R.id.cancel_tv);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();

                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .setTextXOffset(-40, -40, -40, -40, -40, -40)
                .isCenterLabel(false)
                .setLineSpacingMultiplier(2.f)
                .setTitleColor(R.color.black)
                .setDividerColor(context.getResources().getColor(R.color.D1))
                .setDate(selectedDate)
                .isDialog(false)
                .isCyclic(false)
                .setRangDate(startDate, endDate)
                .build();
    }

    public static   void initTimePicker2(Context context,TextView textView) {//选择出生年月日
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11



        if (time==null){
            //系统当前时间
            selectedDate1 = Calendar.getInstance();
            time = selectedDate1.getTime();
            LogUtil.d("lzx---》","time " + time);
        }
        selectedDate1.setTime(time);//设置选中的默认时间


        Calendar startDate = Calendar.getInstance();
        startDate.set(2010, getMonth() - 1, getDay());


        Calendar endDate = Calendar.getInstance();
        endDate.set(getYear()+10, getMonth() - 1, getDay());

        pvTime1 = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                textView.setText(getTime(date));
                time = date;
            }
        })       .setTitleSize(20)
                .setTitleText("结束时间")//标题
                .setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.submit_tv);
                        TextView ivCancel = (TextView) v.findViewById(R.id.cancel_tv);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime1.returnData();
                                pvTime1.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime1.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false}) //年月日时分秒 的显示与否，不设置则默认全部显示
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .setTextXOffset(-40, -40, -40, -40, -40, -40)
                .isCenterLabel(false)
                .setLineSpacingMultiplier(2.f)
                .setTitleColor(R.color.black)
                .setDividerColor(context.getResources().getColor(R.color.D1))
                .setDate(selectedDate1)
                .isDialog(false)
                .isCyclic(false)
//                .setRangDate(startDate, endDate)
                .build();
    }





    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(date);
    }


    /**
     * 获取月
     * @return
     */
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日
     * @return
     */
    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }

    public static int getYear(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.YEAR);
    }

    public static Date getTimeOneMonth() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取一个月前的时间
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return m;
    }

    public static Date getTimeSevenDay() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取一个月前的时间
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -7);
        Date m = c.getTime();
        return m;
    }

    public static Date getTimeMonth() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取一个月前的时间
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, 0);
        Date m = c.getTime();
        return m;
    }

    public static Date getTimeOneDay() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //获取一个月前的时间
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, +1);
        Date m = c.getTime();
        return m;
    }

}
