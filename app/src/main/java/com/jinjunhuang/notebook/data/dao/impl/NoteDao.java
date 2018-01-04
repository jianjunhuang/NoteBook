package com.jinjunhuang.notebook.data.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jinjunhuang.notebook.data.NoteDbOpenHelper;
import com.jinjunhuang.notebook.data.dao.INoteDao;
import com.jinjunhuang.notebook.model.bean.NoteListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public class NoteDao implements INoteDao {

    private NoteDbOpenHelper helper;

    private String tableName = "note";

    public NoteDao() {
        helper = NoteDbOpenHelper.getHelper();
    }

    @Override
    public boolean addNote(NoteListBean bean) {
        boolean flag = false;
        SQLiteDatabase database = null;
        String sql = "insert into " + tableName + "(noteId,title,size,date,content,isDraft)values(?,?,?,?,?,?)";
        try {
            database = helper.getWritableDatabase();
            database.execSQL(sql, new Object[]{bean.getNoteId(), bean.getTitle(), bean.getSize(), bean.getDate(), bean.getContent(), bean.isDraft()});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean deleteNote(String id) {
        boolean flag = false;
        SQLiteDatabase database = null;
        String sql = "delete from " + tableName + " where noteId = ?";
        try {
            database = helper.getWritableDatabase();
            database.execSQL(sql, new Object[]{id});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateNote(NoteListBean bean) {
        boolean flag = false;
        SQLiteDatabase database = null;
        String sql = "update " + tableName + " set title = ? , date = ? , size = ? , content = ? , isDraft = ? where noteId = ?";
        try {
            database = helper.getWritableDatabase();
            database.execSQL(sql, new Object[]{bean.getTitle(), bean.getDate(), bean.getSize(), bean.getContent(), bean.isDraft(), bean.getNoteId()});
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public List<NoteListBean> getAll() {
        List<NoteListBean> list = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        String sql = "select * from " + tableName + " where isDraft = ?";
        try {
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(sql, new String[]{"0"});
            while (cursor.moveToNext()) {
                NoteListBean bean = new NoteListBean();
                bean.setNoteId(cursor.getString(0));
                bean.setTitle(cursor.getString(1));
                bean.setDate(cursor.getString(2));
                bean.setSize(cursor.getLong(3));
                bean.setDraft(cursor.getInt(5));
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    @Override
    public NoteListBean getById(String id) {
        NoteListBean bean = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;
        String sql = "select * from " + tableName + " where noteId = ?";
        try {
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(sql, new String[]{id});
            if (cursor.moveToNext()) {
                bean = new NoteListBean();
                bean.setNoteId(cursor.getString(0));
                bean.setTitle(cursor.getString(1));
                bean.setDate(cursor.getString(2));
                bean.setSize(cursor.getLong(3));
                bean.setContent(cursor.getString(4));
                bean.setDraft(cursor.getInt(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return bean;
    }

    @Override
    public List<NoteListBean> getAllDraft() {
        List<NoteListBean> list = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        String sql = "select * from " + tableName + " where isDraft = ?";
        try {
            database = helper.getReadableDatabase();
            cursor = database.rawQuery(sql, new String[]{"1"});
            while (cursor.moveToNext()) {
                NoteListBean bean = new NoteListBean();
                bean.setNoteId(cursor.getString(0));
                bean.setTitle(cursor.getString(1));
                bean.setDate(cursor.getString(2));
                bean.setSize(cursor.getLong(3));
                bean.setDraft(cursor.getInt(5));
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }
}

