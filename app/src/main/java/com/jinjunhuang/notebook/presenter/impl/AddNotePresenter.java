package com.jinjunhuang.notebook.presenter.impl;

import com.jinjunhuang.notebook.contract.AddNoteContract;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.jinjunhuang.notebook.model.impl.AddNoteModel;
import com.jinjunhuang.notebook.presenter.IAddNotePresenter;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public class AddNotePresenter implements IAddNotePresenter<NoteListBean> {

    private AddNoteContract.View<NoteListBean> mView;
    private AddNoteContract.Model<NoteListBean> mModel;

    public AddNotePresenter(AddNoteContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void onStart() {
        mModel = new AddNoteModel();
        mModel.setAfterGetData(new AddNoteContract.AfterGetData<NoteListBean>() {

            @Override
            public void onAddSuccess() {
                mView.onAddSuccess();
            }

            @Override
            public void onGetDataSuccess(NoteListBean data) {
                mView.onGetDataSuccess(data);
            }

            @Override
            public void onAddFailed(String reason) {
                mView.onAddFailed(reason);
            }

            @Override
            public void onGetDataFailed(String reason) {
                mView.onGetDataFailed(reason);
            }
        });
    }

    @Override
    public void onDestroy() {
        mModel = null;
        mView = null;
    }

    @Override
    public void getData(String noteId) {
        mModel.getData(noteId);
    }

    @Override
    public void addToDb(NoteListBean noteListBean) {
        mModel.addToDb(noteListBean);
    }
}
