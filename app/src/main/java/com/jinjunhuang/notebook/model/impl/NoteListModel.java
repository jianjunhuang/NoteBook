package com.jinjunhuang.notebook.model.impl;

import android.util.Log;

import com.demo.jianjunhuang.mvptools.utils.AfterGetDatas;
import com.demo.jianjunhuang.mvptools.utils.NetworkUtils;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.common.UrlValues;
import com.jinjunhuang.notebook.contract.NoteListContract;
import com.jinjunhuang.notebook.data.dao.impl.NoteDao;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.library.jianjunhuang.okhttputils.okhttputils.OkHttpUtils;
import com.library.jianjunhuang.okhttputils.okhttputils.callback.JSONCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

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


    public void setAfterGetDatas(NoteListContract.AfterGetData afterGetDatas) {
        this.afterGetDatas = afterGetDatas;
    }

    private static final String TAG = "NoteListModel";

    @Override
    public void getData(final int type) {
        if (NetworkUtils.isConnected()) {
            String isDraft = "true";
            if (type == AppConfig.NOTE_FRAGMENT_TYPE) {
                isDraft = "false";
            }
            OkHttpUtils.getInstance().postAsy()
                    .baseURL(UrlValues.GET_NOTE)
                    .params("userName", AppConfig.USR)
                    .params("isDraft", isDraft)
                    .build()
                    .execute(new JSONCallback() {
                        @Override
                        public void onError(Call call, Exception e) {
                            afterGetDatas.onFailed(e.getMessage(), 0);
                        }

                        @Override
                        public void onJSON(JSONObject jsonObject) {
                            try {
                                String status = jsonObject.getString("status");
                                if ("accept".equals(status)) {
                                    JSONArray notes = (JSONArray) jsonObject.get("noteList");
                                    List<NoteListBean> list = new ArrayList<>();
                                    for (int i = 0; i < notes.length(); i++) {
                                        NoteListBean bean = new NoteListBean();
                                        JSONObject json = (JSONObject) notes.get(i);
                                        bean.setContent(json.getString("noteContent"));
                                        bean.setDate(new Date(Long.parseLong(json.getString("noteCreateTime"))));
                                        if (json.getBoolean("draft")) {
                                            bean.setDraft(1);
                                        } else {
                                            bean.setDraft(0);
                                        }
                                        bean.setTitle(json.getString("noteTitle"));
                                        bean.setSize(bean.getContent().getBytes().length);
                                        bean.setNoteId(json.getString("noteId"));
                                        list.add(bean);
                                    }
                                    afterGetDatas.onSuccess(list);
                                } else {
                                    getDateFromDb(type);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                getDateFromDb(type);
                            }
                        }
                    });
        } else {
            getDateFromDb(type);
        }
    }

    private void getDateFromDb(int type) {
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
