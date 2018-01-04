package com.jinjunhuang.notebook.presenter.impl;

import com.jinjunhuang.notebook.contract.LoginContract;
import com.jinjunhuang.notebook.model.impl.LoginModel;

/**
 * Created by jianjunhuang on 17-12-29.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPresenter(LoginContract.View mView) {
        this.mView = mView;
        mModel = new LoginModel();
    }

    @Override
    public void onStart() {
        mModel.setOnLoginCallback(new LoginContract.OnLoginCallback() {
            @Override
            public void onLoginSuccess() {
                mView.onLoginSuccess();
            }

            @Override
            public void onLoginFailed(String reason) {
                mView.onLoginFailed(reason);
            }
        });

        mModel.setOnRegisterCallback(new LoginContract.OnRegisterCallback() {
            @Override
            public void onRegisterSuccess() {
                mView.onRegisterSuccess();
            }

            @Override
            public void onRegisterFailed(String reason) {
                mView.onRegisterFailed(reason);
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
    }

    @Override
    public void login(String usr, String pwd) {
        mModel.login(usr, pwd);
    }

    @Override
    public void register(String usr, String pwd) {
        mModel.register(usr, pwd);
    }
}
