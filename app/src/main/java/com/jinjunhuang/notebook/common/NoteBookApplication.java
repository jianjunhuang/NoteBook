package com.jinjunhuang.notebook.common;

import com.demo.jianjunhuang.mvptools.integration.BaseApplication;
import com.demo.jianjunhuang.mvptools.utils.NetworkUtils;
import com.jinjunhuang.notebook.data.NoteDbOpenHelper;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteBookApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NoteDbOpenHelper.init(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .readTimeout(5000L, TimeUnit.MILLISECONDS);
        OkHttpUtils.initUtils(builder.build());
        NetworkUtils.init(this);
    }
}
