package com.jinjunhuang.notebook.model.impl;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;
import com.jinjunhuang.notebook.contract.AddNoteContract;
import com.jinjunhuang.notebook.db.dao.impl.NoteDao;
import com.jinjunhuang.notebook.model.bean.NoteListBean;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public class AddNoteModel implements AddNoteContract.Model<NoteListBean> {

    private AddNoteContract.AfterGetData<NoteListBean> afterGetDatas;

    private NoteDao noteDao;

    public AddNoteModel() {
        noteDao = new NoteDao();
    }

    @Override
    public void onDestroy() {
        afterGetDatas = null;
        noteDao = null;
    }

    @SuppressWarnings("unused")
    @Override
    public void setAfterGetData(AfterGetDatas afterGetDatas) {
    }

    @SuppressWarnings("unused")
    @Override
    public void getDate() {

    }

    @Override
    public void setAfterGetData(AddNoteContract.AfterGetData afterGetDatas) {
        this.afterGetDatas = afterGetDatas;
    }

    @Override
    public void addToDb(NoteListBean noteListBean) {
        NoteListBean bean = noteDao.getById(noteListBean.getNoteId());
        if (bean == null) {
            if (noteDao.addNote(noteListBean)) {
                afterGetDatas.onAddSuccess();
            } else {
                afterGetDatas.onAddFailed("can't save");
            }
        } else {
            if (noteDao.updateNote(noteListBean)) {
                afterGetDatas.onAddSuccess();
            } else {
                afterGetDatas.onAddFailed("can't save");
            }
        }

    }

    @Override
    public void getData(String noteId) {
        NoteListBean bean = noteDao.getById(noteId);
        if (bean != null) {
            afterGetDatas.onGetDataSuccess(bean);
        } else {
            afterGetDatas.onGetDataFailed("failed to get note");
        }
    }
}
