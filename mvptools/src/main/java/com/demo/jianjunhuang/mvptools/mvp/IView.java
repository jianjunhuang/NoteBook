package com.demo.jianjunhuang.mvptools.mvp;

/**
 * @author jianjunhuang.me@foxmail.com
 * @since 2017/5/24.
 */

public interface IView {
//    void onSuccess();

    void onFailed(int code, String reason);
}
