package com.jinjunhuang.notebook.presenter;

import com.demo.jianjunhuang.mvptools.mvp.IPresenter;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public interface INoteListPresenter extends IPresenter{
    void loadData(int type);
    void delNote(String noteId);
}
