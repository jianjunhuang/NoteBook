package com.jinjunhuang.notebook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public class NoteDbOpenHelper extends SQLiteOpenHelper {

    private static volatile NoteDbOpenHelper helper = null;

    public static void init(@NonNull Context context) {
        if (helper == null) {
            synchronized (NoteDbOpenHelper.class) {
                if (helper == null) {
                    helper = new NoteDbOpenHelper(context);
                }
            }
        }
    }

    public static NoteDbOpenHelper getHelper() {
        if (helper == null) {
            throw new RuntimeException("please init NoteDbOpenHelper first");
        }
        return helper;
    }

    public NoteDbOpenHelper(Context context) {
        super(context, "note.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists note (noteId varchar(32) primary key , title varchar(32) , date varchar(32) , size long , content varchar(64) , isDraft integer default 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
