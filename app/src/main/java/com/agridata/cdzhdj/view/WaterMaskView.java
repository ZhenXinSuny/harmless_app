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
public class WaterMaskView extends RelativeLayout {

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

    public WaterMaskView(Context context) {
        this(context, null);
    }

    public WaterMaskView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_watermask, this, true);


        tv_date2 = (TextView) findViewById(R.id.tv_date2);
        tv_zuobiao = (TextView) findViewById(R.id.tv_zuobiao);


        tv_danhao = (TextView) findViewById(R.id.tv_danhao);
        tv_shenbao_name = (TextView) findViewById(R.id.tv_shenbao_name);
        tv_shouji_name = (TextView) findViewById(R.id.tv_shouji_name);


        title_danhao_tv = findViewById(R.id.title_danhao_tv);
        title_shenbaomingcheng_tv = findViewById(R.id.title_shenbaomingcheng_tv);
        title_shoujiren_tv = findViewById(R.id.title_shoujiren_tv);



        title_num_tv = findViewById(R.id.title_num_tv);
        num_tv = findViewById(R.id.num_tv);


        title_animal_tv = findViewById(R.id.title_animal_tv);
        type_animal_tv = findViewById(R.id.type_animal_tv);







    }
    public void setInfoDate(String content) {
        tv_date2.setText(content);
    }

    public void setInfoZuoBiao(String content) {
        tv_zuobiao.setText(content);
    }

    public void setInfoDanHao(String content) {
        tv_danhao.setText(content);
    }


    public void setInfoShenBaoRen(String content) {
        tv_shenbao_name.setText(content);
    }


    public void setInfoShouJiRen(String content) {
        tv_shouji_name.setText(content);
    }


    public void setInfoShenBaoRenGson() {
        tv_shenbao_name.setVisibility(GONE);
    }
    public void setInfoDanHaoGson() {
        tv_danhao.setVisibility(GONE);
    }
    public void setInfoShouJiRenGson() {
        tv_shouji_name.setVisibility(GONE);
    }




    public void setTitle_danhao_tvGson() {
        title_danhao_tv.setVisibility(GONE);
    }
    public void setTitle_shenbaomingcheng_tvGson() {
        title_shenbaomingcheng_tv.setVisibility(GONE);
    }
    public void setTitle_shoujiren_tvGson() {
        title_shoujiren_tv.setVisibility(GONE);
    }



    public void setnum_tv(String content) {
        num_tv.setText(content);
    }


    public void setTitle_num_tvGson() {
        title_num_tv.setVisibility(GONE);
    }


    public void settype_animal_tv(String content) {
        type_animal_tv.setText(content);
    }


    public void settitle_animal_tvGson() {
        title_animal_tv.setVisibility(GONE);
    }



}
