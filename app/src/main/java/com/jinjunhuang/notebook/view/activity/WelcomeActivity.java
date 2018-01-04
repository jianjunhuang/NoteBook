package com.jinjunhuang.notebook.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AnticipateOvershootInterpolator;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.common.UrlValues;
import com.jinjunhuang.notebook.view.weiget.WelcomeView;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.JSONCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import okhttp3.Call;

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
        //TODO Login
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SPUtils sp = SPUtils.instance(AppConfig.SP_USR_INFO_FILE_NAME);
                String usrname = sp.getInfo(AppConfig.SP_USR_KEY, "");
                String pwd = sp.getInfo(AppConfig.SP_PWD_KEY, "");
                AppConfig.TOKEN = sp.getInfo(AppConfig.TOKEN_KEY, "");
                AppConfig.USR = usrname;
                AppConfig.PWD = pwd;
                if (!isEmptyOrNull(usrname) && !isEmptyOrNull(pwd)) {
                    OkHttpUtils.getInstance().postJsonAsy()
                            .baseURL(UrlValues.SECRET_LOGIN)
                            .params("userName", usrname)
                            .params("password", pwd)
                            .params("token", AppConfig.TOKEN)
                            .build()
                            .execute(new JSONCallback() {
                                @Override
                                public void onError(Call call, Exception e) {
                                    intentLoginActivity();
                                }

                                @Override
                                public void onJSON(JSONObject jsonObject) {
                                    try {
                                        String status = jsonObject.getString("status");
                                        if ("accept".equals(status)) {

                                            intentHomeActivity();
                                        } else {
                                            intentLoginActivity();
                                            showShort(jsonObject.getString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        intentLoginActivity();
                                    }

                                }
                            });
                } else {
                    intentLoginActivity();
                }
            }
        }, 2000);
    }

    private void intentLoginActivity() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void intentHomeActivity() {
        Intent intent = new Intent(WelcomeActivity.this, HomepageActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @Override
    protected void initListener() {

    }
}
