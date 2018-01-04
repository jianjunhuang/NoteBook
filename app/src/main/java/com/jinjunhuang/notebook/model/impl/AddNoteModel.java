package com.jinjunhuang.notebook.model.impl;

import android.text.TextUtils;
import android.util.Log;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.common.UrlValues;
import com.jinjunhuang.notebook.contract.AddNoteContract;
import com.jinjunhuang.notebook.data.dao.impl.NoteDao;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.JSONCallback;

import org.json.JSONObject;

import okhttp3.Call;

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

    @Override
    public void setAfterGetData(AddNoteContract.AfterGetData afterGetDatas) {
        this.afterGetDatas = afterGetDatas;
    }

    @Override
    public void addToDb(NoteListBean noteListBean) {
//        NoteListBean bean = noteDao.getById(noteListBean.getNoteId());
//        if (bean == null) {
//
//            if (noteDao.addNote(noteListBean)) {
//                afterGetDatas.onAddSuccess();
//            } else {
//                afterGetDatas.onAddFailed("can't save");
//            }
//        } else {
//            if (noteDao.updateNote(noteListBean)) {
//                afterGetDatas.onAddSuccess();
//            } else {
//                afterGetDatas.onAddFailed("can't save");
//            }
//        }
        Log.i(TAG, "addToDb: " + noteListBean.getNoteId());

        String isDraft = noteListBean.isDraft() == 1 ? "true" : "false";
        if (!TextUtils.isEmpty(noteListBean.getNoteId())) {
            Log.i(TAG, "addToDb: edit");
            OkHttpUtils.getInstance().postJsonAsy()
                    .baseURL(UrlValues.EDIT_NOTE)
                    .params("noteTitle", noteListBean.getTitle())
                    .params("noteContent", noteListBean.getContent())
                    .params("isDraft", isDraft)
                    .params("noteId", noteListBean.getNoteId())
                    .build()
                    .execute(new JSONCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Log.i(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onJSON(JSONObject jsonObject) {
                            Log.i(TAG, "onJSON: " + jsonObject.toString());
                        }
                    });
        } else {
            Log.i(TAG, "addToDb: add");
            OkHttpUtils.getInstance().postJsonAsy()
                    .baseURL(UrlValues.ADD_NOTE)
                    .params("noteTitle", noteListBean.getTitle())
                    .params("noteContent", noteListBean.getContent())
                    .params("isDraft", isDraft)
                    .params("userName", AppConfig.USR)
                    .build()
                    .execute(new JSONCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            Log.i(TAG, "onError: " + e.getMessage());
                        }

                        @Override
                        public void onJSON(JSONObject jsonObject) {
                            Log.i(TAG, "onJSON: " + jsonObject.toString());
                        }
                    });
        }


    }

    private static final String TAG = "AddNoteModel";
}
