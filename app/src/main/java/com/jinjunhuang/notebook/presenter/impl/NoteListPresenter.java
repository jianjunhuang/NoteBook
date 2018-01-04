package com.jinjunhuang.notebook.presenter.impl;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;
import com.jinjunhuang.notebook.contract.NoteListContract;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.jinjunhuang.notebook.model.impl.NoteListModel;
import com.jinjunhuang.notebook.presenter.INoteListPresenter;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteListPresenter implements INoteListPresenter {

    private NoteListContract.View<NoteListBean> mView;
    private NoteListContract.Model<NoteListBean> mModel;

    public NoteListPresenter(NoteListContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void loadData(int type) {
        mModel.getData(type);
    }

    @Override
    public void delNote(String noteId) {
        mModel.delete(noteId);
    }


    @Override
    public void onStart() {
        mModel = new NoteListModel();
        mModel.setAfterGetDatas(new NoteListContract.AfterGetData<NoteListBean>() {

            @Override
            public void onDelSuccess() {
                mView.onDelSuccess();
            }

            @Override
            public void onDelFailed(String reason) {
                mView.onDelFailed(reason);
            }

            @Override
            public void onFailed(String reason, int errCode) {
                mView.onFailed();
            }

            @Override
            public void onSuccess(List data) {
                mView.onSuccess(data);
            }
        });
    }

    @Override
    public void onDestroy() {
        mView = null;
        mModel = null;
    }
}
