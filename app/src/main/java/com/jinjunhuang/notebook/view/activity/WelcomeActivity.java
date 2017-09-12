package com.jinjunhuang.notebook.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnticipateOvershootInterpolator;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.view.weiget.WelcomeView;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 *         就只有一个动画的 activity，
 */

public class WelcomeActivity extends BaseActivity {

    private Handler handler = new Handler();

    private WelcomeView welcomeView;

    @Override
    protected int getLayoutId() {
        return R.layout.welcome_activity;
    }

    @Override
    protected void initView() {

        welcomeView = findView(R.id.welcome_view);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SPUtils sp = SPUtils.instance(AppConfig.SP_USR_INFO_FILE_NAME);
                String usrname = sp.getInfo(AppConfig.SP_USR_KEY, "");
                String pwd = sp.getInfo(AppConfig.SP_PWD_KEY, "");
                if (!isEmptyOrNull(usrname) && !isEmptyOrNull(pwd)) {
                    Intent intent = new Intent(WelcomeActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }
        }, 2000);
    }

    @Override
    protected void initListener() {

    }
}
