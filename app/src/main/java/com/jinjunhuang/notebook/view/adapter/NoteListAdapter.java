package com.jinjunhuang.notebook.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.jinjunhuang.notebook.view.activity.AddNoteActivity;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteListAdapter extends RecyclerAdapter<NoteListBean> implements RecyclerViewHolder.OnItemClickListener {

    private int type;

    public NoteListAdapter(Context context, List<NoteListBean> list, int layoutId, int type) {
        super(context, list, layoutId);
        this.type = type;
    }

    @Override
    public void convert(final RecyclerViewHolder viewHolder, NoteListBean noteListBean, final int position) {
        viewHolder.setOnItemClickListener(this);
        viewHolder.setText(R.id.note_list_title_tv, noteListBean.getTitle());
        viewHolder.setText(R.id.note_list_size_tv, noteListBean.getSize() + " bytes");
        viewHolder.setText(R.id.note_list_time_tv, noteListBean.getDate());
        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, viewHolder.getConvertView());
                popupMenu.inflate(R.menu.del_note_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (onItemPopupMenuClickListener != null) {
                            onItemPopupMenuClickListener.onItemPopupMenuClickListener(item, position);
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });
    }

    public interface OnItemPopupMenuClickListener {
        void onItemPopupMenuClickListener(MenuItem item, int position);
    }

    private OnItemPopupMenuClickListener onItemPopupMenuClickListener;

    public void setOnItemPopupMenuClickListener(OnItemPopupMenuClickListener onItemPopupMenuClickListener) {
        this.onItemPopupMenuClickListener = onItemPopupMenuClickListener;
    }

    @Override
    public void onItemClick(View convertView, int position) {
        NoteListBean bean = list.get(position);
        Intent intent = new Intent(context, AddNoteActivity.class);
        Bundle bundle = new Bundle();
        if (type == AppConfig.NOTE_FRAGMENT_TYPE) {
            bundle.putInt(AppConfig.FEATURE_TAG_KEY, AppConfig.FEATURE_SHOW);
        } else {
            bundle.putInt(AppConfig.FEATURE_TAG_KEY, AppConfig.FEATURE_EDIT);
        }
        bundle.putString(AppConfig.NOTE_ID_KEY, bean.getNoteId());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
