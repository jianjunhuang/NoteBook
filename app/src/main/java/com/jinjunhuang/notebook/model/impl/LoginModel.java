package com.jinjunhuang.notebook.model.impl;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;
import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.common.UrlValues;
import com.jinjunhuang.notebook.contract.LoginContract;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.JSONCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by jianjunhuang on 17-12-29.
 */

public class LoginModel implements LoginContract.Model {

    private LoginContract.OnLoginCallback onLoginCallback;
    private LoginContract.OnRegisterCallback onRegisterCallback;

    @Override
    public void onDestroy() {
        onLoginCallback = null;
        onRegisterCallback = null;
    }


    @Override
    public void login(final String usr, final String pwd) {
        OkHttpUtils.getInstance().postJsonAsy()
                .baseURL(UrlValues.DISPLAY_LOGIN)
                .params("userName", usr)
                .params("password", pwd)
                .build()
                .execute(new JSONCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        onLoginCallback.onLoginFailed(e.getMessage());
                    }

                    @Override
                    public void onJSON(JSONObject jsonObject) {
                        try {
                            String status = jsonObject.getString("status");
                            if ("accept".equals(status)) {
                                SPUtils sp = SPUtils.instance(AppConfig.SP_USR_INFO_FILE_NAME);
                                sp.store(AppConfig.SP_USR_KEY, usr);
                                sp.store(AppConfig.SP_PWD_KEY, pwd);
                                AppConfig.USR = usr;
                                AppConfig.PWD = pwd;
                                AppConfig.TOKEN = jsonObject.getString("token");
                                sp.store(AppConfig.TOKEN_KEY, AppConfig.TOKEN);

                                onLoginCallback.onLoginSuccess();
                            } else {
                                onLoginCallback.onLoginFailed(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onLoginCallback.onLoginFailed(e.getMessage());
                        }
                    }
                });

    }

    @Override
    public void register(String usr, String pwd) {
        OkHttpUtils.getInstance().postJsonAsy()
                .baseURL(UrlValues.REGISTER)
                .params("userName", usr)
                .params("password", pwd)
                .build()
                .execute(new JSONCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        onRegisterCallback.onRegisterFailed(e.getMessage());
                    }

                    @Override
                    public void onJSON(JSONObject jsonObject) {
                        try {
                            String status = jsonObject.getString("status");
                            if ("accept".equals(status)) {
                                onRegisterCallback.onRegisterSuccess();
                            } else {
                                onRegisterCallback.onRegisterFailed(jsonObject.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onRegisterCallback.onRegisterFailed(e.getMessage());
                        }
                    }
                });
    }

    @Override
    public void setOnLoginCallback(LoginContract.OnLoginCallback onLoginCallback) {
        this.onLoginCallback = onLoginCallback;
    }

    @Override
    public void setOnRegisterCallback(LoginContract.OnRegisterCallback onRegisterCallback) {
        this.onRegisterCallback = onRegisterCallback;
    }
}
