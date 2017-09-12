package com.jinjunhuang.notebook.model.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteListBean {

    private String title;
    private String date;
    private String content;
    // 0 for false , 1 for true
    private int isDraft;
    private String noteId;
    private long size;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public int isDraft() {
        return isDraft;
    }

    public void setDraft(int draft) {
        isDraft = draft;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/mm/dd HH:MM:ss");
        this.date = simpleDateFormat.format(date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
