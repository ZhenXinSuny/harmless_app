package com.agridata.cdzhdj.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.agridata.cdzhdj.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallClipRotateMultipleIndicator;
import com.wang.avi.indicators.LineScaleIndicator;
import com.wang.avi.indicators.LineScalePulseOutIndicator;
import com.wang.avi.indicators.LineSpinFadeLoaderIndicator;
import com.wang.avi.indicators.SquareSpinIndicator;
import com.wang.avi.indicators.TriangleSkewSpinIndicator;

/**
 * @auther 56454
 * @date 2022/6/28
 * @time 10:28.
 */
public class CustomLoadingDialog {
    private Context mContext;
    private Dialog mDialog;
    private AVLoadingIndicatorView mAVLoadingIndicatorView;
    private TextView  mTv;

    public CustomLoadingDialog(Context context) {
        this.mContext = context;
        this.init();
    }

    private void init() {
        LineSpinFadeLoaderIndicator DEFAULT_INDICATOR=new LineSpinFadeLoaderIndicator();
        BallClipRotateMultipleIndicator lineScaleIndicator = new BallClipRotateMultipleIndicator();
        this.mDialog = new Dialog(this.mContext, R.style.CustomLoadingDialogStyle);
        View loadingView = LayoutInflater.from(this.mContext).inflate(R.layout.custom_loading_dialog, null);
        this.mAVLoadingIndicatorView = (AVLoadingIndicatorView) loadingView.findViewById(R.id.loading_progress);
        this.mTv = loadingView.findViewById(R.id.message);
        this.mAVLoadingIndicatorView.setIndicator(lineScaleIndicator);
        this.mAVLoadingIndicatorView.setIndicatorColor(mContext.getResources().getColor(R.color.white));
        this.mAVLoadingIndicatorView.show();
        this.mDialog.setContentView(loadingView);
    }

    public void setTitleVisibility(int visibility) {
        this.mTv.setVisibility(visibility);
    }

    public void setTitle(String title) {
        this.mTv.setText(title);
    }

    /**
     * 加载指定布局
     *
     * @param layoutId
     */
    public void setLoadingView(int layoutId) {
        View loadingView = LayoutInflater.from(this.mContext).inflate(layoutId, null);
        this.mDialog.setContentView(loadingView);
    }

    public Dialog getDialog() {
        return mDialog;
    }

    /**
     * 显示
     */
    public void show() {
        if (null != this.mDialog && !this.mDialog.isShowing()) {
            this.mDialog.show();
        }
    }

    /**
     * 隐藏
     */
    public void hide() {
        if (null != this.mDialog && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
    }

    /**
     * 销毁
     */
    public void destroy() {
        if (null != this.mDialog && this.mDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        this.mDialog = null;
    }

    /**
     * 是否正在显示
     */
    public boolean isShowing() {
        return null != this.mDialog && this.mDialog.isShowing();
    }

    /**
     * 设置触摸是否取消对话框
     */
    public void setCanceledOnTouchOutside(boolean cancel) {
        if (null != this.mDialog) {
            this.mDialog.setCanceledOnTouchOutside(cancel);
        }
    }

    /**
     * 设置按键key监听（比如监听是否点击返回键）
     *
     * @param listener
     */
    public void setOnKeyListener(DialogInterface.OnKeyListener listener) {
        mDialog.setOnKeyListener(listener);
    }
}
