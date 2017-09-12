package com.jinjunhuang.notebook.model.impl;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.contract.NoteListContract;
import com.jinjunhuang.notebook.db.dao.impl.NoteDao;
import com.jinjunhuang.notebook.model.bean.NoteListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteListModel implements NoteListContract.Model<NoteListBean> {

    private NoteListContract.AfterGetData<NoteListBean> afterGetDatas;

    private NoteDao noteDao;

    public NoteListModel() {
        noteDao = new NoteDao();
    }

    @Override
    public void onDestroy() {
        afterGetDatas = null;
        noteDao = null;
    }

    @SuppressWarnings("hint")
    @Override
    public void setAfterGetData(AfterGetDatas afterGetDatas) {
//        this.afterGetDatas = afterGetDatas;
    }

    public void setAfterGetDatas(NoteListContract.AfterGetData afterGetDatas) {
        this.afterGetDatas = afterGetDatas;
    }

    @Override
    public void getDate() {
        //no use
    }

    @Override
    public void getData(int type) {
        List<NoteListBean> list;
        if (type == AppConfig.NOTE_FRAGMENT_TYPE) {
            list = noteDao.getAll();
        } else {
            list = noteDao.getAllDraft();
        }
        if (list != null) {
            afterGetDatas.onSuccess(list);
        } else {
            afterGetDatas.onFailed("can't get notes", -1);
        }
    }

    @Override
    public void delete(String noteId) {
        if (noteDao.deleteNote(noteId)) {
            afterGetDatas.onDelSuccess();
        } else {
            afterGetDatas.onDelFailed("can't del");
        }
    }
}
