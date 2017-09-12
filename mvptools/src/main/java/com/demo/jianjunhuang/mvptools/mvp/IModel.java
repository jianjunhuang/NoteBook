package com.demo.jianjunhuang.mvptools.mvp;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;

/**
 * @author jianjunhuang.me@foxmail.com
 * @since 2017/5/24.
 */

public interface IModel {
    void onDestroy();

    void setAfterGetData(AfterGetDatas afterGetDatas);

    void getDate();
}
