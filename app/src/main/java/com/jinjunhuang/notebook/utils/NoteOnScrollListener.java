package com.jinjunhuang.notebook.utils;

import android.support.v7.widget.RecyclerView;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */


public class NoteOnScrollListener extends RecyclerView.OnScrollListener {

    private int preY = 0;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

    }
}