package com.agridata.cdzhdj.activity.mine.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.agridata.cdzhdj.net.HttpApi.HttpRequest;
import com.agridata.cdzhdj.R;
import com.agridata.cdzhdj.base.BaseActivity;
import com.agridata.cdzhdj.data.AppVerBean;
import com.agridata.cdzhdj.databinding.ActivityAboutBinding;
import com.agridata.cdzhdj.utils.AppConstants;
import com.agridata.cdzhdj.utils.AppUtil;
import com.agridata.cdzhdj.utils.ScreenUtils;
import com.agridata.network.listener.CallBackLis;
import com.agridata.network.utils.LogUtil;
import com.azhon.appupdate.listener.OnDownloadListener;
import com.azhon.appupdate.manager.DownloadManager;
import com.azhon.appupdate.view.NumberProgressBar;
import com.gyf.immersionbar.ImmersionBar;
import com.lzx.utils.RxToast;

import java.io.File;
import java.util.Objects;

/**
 * @auther 56454
 * @date 2022/8/4
 * @time 11:45.
 */
public class AboutActivity extends BaseActivity<ActivityAboutBinding> {
    private AlertDialog exitDialog;
    private NumberProgressBar my_bar;
    private String AppUrl;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected ActivityAboutBinding getBinding() {
        return ActivityAboutBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true).statusBarColor(R.color.white).autoDarkModeEnable(true).statusBarDarkFont(true).init();//沉浸式状态栏
        binding.titlebarLeft.setImageResource(R.drawable.title_back);
        binding.titlebarMiddle.setText("关于我们");
        String mCurrentVersion = AppUtil.getVersionName(this);
        binding.versionTvNum.setText("v"+mCurrentVersion);
        binding.appVersionTv.setText(String.format(getString(R.string.app_version), mCurrentVersion));
        binding.titlebarLeft.setOnClickListener(v -> finish());
        binding.versionLl.setOnClickListener(v -> getAppUpData());
    }

    @Override
    protected void initDatas() {

    }

    //对比进行更新
    private void getAppUpData() {
        HttpRequest.getVer(AboutActivity.this, new CallBackLis<AppVerBean>() {
            @Override
            public void onSuccess(String method, AppVerBean appVerBean) {
                if (appVerBean.ErrorCode == 0) {
                    String currentVersion = AppUtil.getVersionName(AboutActivity.this);  //获取当前版本名
                    if (AppUtil.compareVersionNew(appVerBean.Result.get(0).VersionNo, currentVersion) > 0) {
                        showUpDataNewVersionDialog(appVerBean.Result.get(0).VersionNo, appVerBean.Result.get(0).Remark);
                        AppUrl = AppConstants.AppUrlDown + appVerBean.Result.get(0).FilePath + "&type=file";
                    }else {
                        Objects.requireNonNull(RxToast.info(AboutActivity.this,"您当前已是最新版本!"));
                    }
                }
            }

            @Override
            public void onFailure(String method, String error) {
            }
        });
    }

    /**
     * 更新弹框
     */
    private void showUpDataNewVersionDialog(String newVersion, String Remark) {
        View view = getLayoutInflater().inflate(R.layout.dialog_update_h, null);
        exitDialog = new AlertDialog.Builder(this).create();
        exitDialog.setView(view);
        exitDialog.setCanceledOnTouchOutside(false);
        exitDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        Button btn_update = (Button) view.findViewById(R.id.btn_update);//确定按钮
        ImageView ib_close = (ImageView) view.findViewById(R.id.ib_close);//确定按钮
        TextView tv_description = (TextView) view.findViewById(R.id.tv_description);//内容
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);//标题
        View line = view.findViewById(R.id.line);
        line.setVisibility(View.VISIBLE);
        ib_close.setVisibility(View.VISIBLE);
        tv_title.setText("发现新版v" + newVersion + "可以下载啦！");
        my_bar = view.findViewById(R.id.number_progress_bar);
        tv_description.setText(Remark.replace("\\n", "\n"));
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog.dismiss();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upApk();
            }
        });
        exitDialog.show();
        WindowManager.LayoutParams params = exitDialog.getWindow().getAttributes();
        params.width = (int) (ScreenUtils.getScreenWidth(this) * 0.85);
        exitDialog.getWindow().setAttributes(params);
    }


    private void upApk() {
        new DownloadManager.Builder(this).apkUrl(AppUrl)
                .smallIcon(R.drawable.ic_whh)
                .apkName("智慧动监.apk")
                .onDownloadListener(listenerAdapter)
                .showBgdToast(false)
                .jumpInstallPage(true)
                .build()
                .download();
    }

    private OnDownloadListener listenerAdapter = new OnDownloadListener() {
        //开始下载
        @Override
        public void start() {
            LogUtil.d("lzx---》", "开始下载");
        }

        //下载中
        @Override
        public void downloading(int max, int progress) {
            int curr = (int) (progress / (double) max * 100.0);
            my_bar.setMax(100);
            my_bar.setProgress(curr);
        }

        //下载完成
        @Override
        public void done(File apk) {
            LogUtil.d("lzx---》", "下载完成");
            exitDialog.dismiss();
        }

        //取消
        @Override
        public void cancel() {
            LogUtil.d("lzx---》", "取消下载");
        }

        @Override
        public void error(@NonNull Throwable throwable) {
            LogUtil.d("lzx---》", "错误日志" + throwable.toString());
        }
    };
}
