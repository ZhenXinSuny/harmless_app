package com.agridata.cdzhdj.base;


import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.service.CoreService;
import com.agridata.cdzhdj.utils.ActivityManager;
import com.agridata.cdzhdj.utils.KeyBoardUtil;
import com.agridata.cdzhdj.utils.NetworkStateReceiver;
import com.agridata.cdzhdj.utils.RxManager;
import com.agridata.network.utils.LogUtil;
import com.gyf.immersionbar.ImmersionBar;

import java.io.UnsupportedEncodingException;


/**
 * @ProjectName : AdmissionCheck
 * @Author :
 * @Time : 2021/9/27 11:10 收集填报
 * @Description :
 */
public abstract class BaseActivity <T extends ViewBinding> extends AppCompatActivity implements NetworkStateReceiver.NetworkStateChangedListener {

    protected T binding;
    protected RxManager mRxManager = null;
    protected  Bundle   mSavedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setContentView(binding.getRoot());
        mSavedInstanceState = savedInstanceState;
        CoreService.checkServiceIsHealthy(this);
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).init();//沉浸式状态栏
        this.mRxManager = new RxManager();//实列RxManager 用于发送消息
        ActivityManager.getInstance().addActivity(this);
        initView();
        initDatas();
        this.OnEventMainThread();
        this.initListener();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

    }



    /**
     * RxBus事件处理-主线程
     */
    protected void OnEventMainThread() {

    }

    protected abstract T getBinding();



    //初始化数据
    protected abstract void initView();
    //初始化数据
    protected abstract void initDatas();

    protected void initListener() {
        NetworkStateReceiver.getInstance().addNetworkStateChangedListener(this);
    }

    /**
     * 网络状态变化回调
     *
     * @param isNetAvailable 网络是否可用
     */
    @Override
    public void onNetworkStateChanged(boolean isNetAvailable) {
        LogUtil.i("网络是否可用：" + isNetAvailable);
    }
    @Override
    public Resources getResources() {
        //禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.mRxManager == null) {
            this.mRxManager = new RxManager();
        }
    }
    @Override
    protected void onDestroy() {
        // 必须调用该方法，防止内存泄漏
        ImmersionBar.destroy(this,null);
        ActivityManager.getInstance().finishActivity(this);
        if (null != this.mRxManager) {
            this.mRxManager.clear();
            this.mRxManager = null;
        }
        super.onDestroy();
        LogUtil.i("activity: " + getClass().getSimpleName() + " onDestroy() size:" + ActivityManager.getInstance().getActivitySizes());
    }


    //使editText点击外部时候失去焦点
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {//点击editText控件外部
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    assert v != null;
                    KeyBoardUtil.closeKeyboard(v);//软键盘工具类
                    if (editText != null) {
                        editText.clearFocus();
                    }
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        return getWindow().superDispatchTouchEvent(ev) || onTouchEvent(ev);
    }
    EditText editText = null;

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            editText = (EditText) v;
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

}
