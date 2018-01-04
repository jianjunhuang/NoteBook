package com.jinjunhuang.notebook.contract;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IPresenter;
import com.demo.jianjunhuang.mvptools.mvp.IView;

/**
 * Created by jianjunhuang on 17-12-29.
 */

public interface LoginContract {
    interface View extends IView {
        void onLoginSuccess();

        void onLoginFailed(String reason);

        void onRegisterSuccess();

        void onRegisterFailed(String reason);
    }

    interface Model extends IModel {
        void login(String usr, String pwd);

        void register(String usr, String pwd);

        void setOnLoginCallback(OnLoginCallback onLoginCallback);

        void setOnRegisterCallback(OnRegisterCallback onRegisterCallback);
    }

    interface OnLoginCallback {
        void onLoginSuccess();

        void onLoginFailed(String reason);
    }

    interface OnRegisterCallback {
        void onRegisterSuccess();

        void onRegisterFailed(String reason);
    }

    interface Presenter extends IPresenter {
        void login(String usr, String pwd);

        void register(String usr, String pwd);
    }
}
