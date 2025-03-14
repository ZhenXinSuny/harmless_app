package com.agridata.cdzhdj.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agridata.cdzhdj.R;

/**
 * @auther 56454
 * @date 2022/7/8
 * @time 16:26.
 */
public class WaterMaskViewMap extends RelativeLayout {

    private TextView tv_date2;
    private TextView tv_zuobiao;

    public WaterMaskViewMap(Context context) {
        this(context, null);
    }

    public WaterMaskViewMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_map_watermask, this, true);


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
