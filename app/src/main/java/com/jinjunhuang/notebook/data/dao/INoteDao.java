package com.jinjunhuang.notebook.data.dao;

import com.jinjunhuang.notebook.model.bean.NoteListBean;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public interface INoteDao {

    public boolean addNote(NoteListBean bean);

    public boolean deleteNote(String id);

    public boolean updateNote(NoteListBean id);

    public List<NoteListBean> getAll();

    public NoteListBean getById(String id);

    public List<NoteListBean> getAllDraft();

}
