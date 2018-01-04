package com.jinjunhuang.notebook.presenter;

import com.demo.jianjunhuang.mvptools.mvp.IPresenter;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public interface IAddNotePresenter<T> extends IPresenter {

    void addToDb(T t);
}
