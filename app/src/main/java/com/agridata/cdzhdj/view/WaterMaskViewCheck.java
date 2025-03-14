package com.agridata.cdzhdj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agridata.cdzhdj.R;

/**
 * @Author : lzx
 * @PROJECT_NAME : HarmlessApp
 * @Date : on 2023-07-17 13:52.
 * @Description :描述
 */
public class WaterMaskViewCheck extends RelativeLayout {

    private TextView tv_date2;
    private TextView tv_zuobiao;
    private TextView tv_danhao;
    private TextView tv_shenbao_name;
    private TextView tv_shouji_name;

    private TextView title_danhao_tv;
    private TextView title_shenbaomingcheng_tv;
    private TextView title_shoujiren_tv;

    private TextView title_num_tv;
    private TextView num_tv;

    private TextView title_animal_tv;

    private TextView type_animal_tv;

    public WaterMaskViewCheck(Context context) {
        this(context, null);
    }

    public WaterMaskViewCheck(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_watermask_check, this, true);


        tv_date2 = (TextView) findViewById(R.id.tv_date2);
        tv_zuobiao = (TextView) findViewById(R.id.tv_zuobiao);


    }

    public void setInfoDate(String content) {
        tv_date2.setText(content);
    }

    public void setInfoZuoBiao(String content) {
        tv_zuobiao.setText(content);
    }


}