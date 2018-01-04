package com.jinjunhuang.notebook.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.contract.AddNoteContract;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.jinjunhuang.notebook.presenter.impl.AddNotePresenter;

import java.util.Date;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/10.
 */

public class AddNoteActivity extends BaseActivity implements AddNoteContract.View<NoteListBean> {

    private Toolbar toolbar;
    private EditText titleEdt;
    private EditText contentEdt;
    private int featureTag = 0;
    private String noteId = "";
    private AddNotePresenter presenter;
    private AlertDialog dialog;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new AddNotePresenter(this);
        presenter.onStart();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            featureTag = bundle.getInt(AppConfig.FEATURE_TAG_KEY, 0);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_note_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        titleEdt = findView(R.id.add_title_edt);
        contentEdt = findView(R.id.add_content_edt);
        if (featureTag != AppConfig.FEATURE_ADD) {
            titleEdt.setEnabled(false);
            contentEdt.setEnabled(false);
        }
        initAlterDialog();
    }

    private void initToolbar() {
        toolbar = findView(R.id.actionbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_activity_menu, menu);
        if (featureTag == AppConfig.FEATURE_SHOW) {
            menu.findItem(R.id.add_note_save_menu_item).setVisible(false);
            menu.findItem(R.id.add_note_edit_menu_item).setVisible(true);
        } else {
            menu.findItem(R.id.add_note_save_menu_item).setVisible(true);
            menu.findItem(R.id.add_note_edit_menu_item).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setToEditStatus() {
        featureTag = AppConfig.FEATURE_EDIT;
        titleEdt.setEnabled(true);
        contentEdt.setEnabled(true);
        invalidateOptionsMenu();
    }

    private void setToShowStatus() {
        featureTag = AppConfig.FEATURE_SHOW;
        titleEdt.setEnabled(false);
        contentEdt.setEnabled(false);
        invalidateOptionsMenu();
    }

    private void initAlterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提醒");
        builder.setMessage("是否暂时保存为草稿");
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = titleEdt.getText().toString();
                NoteListBean bean = new NoteListBean();
                bean.setDate(new Date());
                bean.setNoteId(noteId);
                String content = contentEdt.getText().toString();
                bean.setContent(content);
                bean.setTitle(title);
                bean.setSize(content.getBytes().length + title.getBytes().length);
                bean.setDraft(1);
                presenter.addToDb(bean);
                finish();
            }
        });
        dialog = builder.create();
    }

    @Override
    protected void initListener() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.add_note_save_menu_item) {
                    setToShowStatus();
                    String title = titleEdt.getText().toString();
                    if (isEmptyOrNull(title)) {
                        ToastUtils.show("please input title !!");
                        return true;
                    }
                    NoteListBean bean = new NoteListBean();
                    bean.setDate(new Date());
                    bean.setNoteId(noteId);
                    String content = contentEdt.getText().toString();
                    bean.setContent(content);
                    bean.setTitle(title);
                    bean.setSize(content.getBytes().length + title.getBytes().length);
                    bean.setDraft(0);
                    presenter.addToDb(bean);
                } else {
                    setToEditStatus();
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyOrNull(contentEdt.getText().toString()) && isEmptyOrNull(titleEdt.getText().toString())) {
                    finish();
                } else {
                    if (featureTag == AppConfig.FEATURE_EDIT || featureTag == AppConfig.FEATURE_ADD) {
                        dialog.show();
                    } else {
                        finish();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isEmptyOrNull(contentEdt.getText().toString()) && isEmptyOrNull(titleEdt.getText().toString())) {
            super.onBackPressed();
        } else {
            if (featureTag == AppConfig.FEATURE_EDIT || featureTag == AppConfig.FEATURE_ADD) {
                dialog.show();
            } else {
                finish();
            }
        }

    }

    @Override
    public void onAddSuccess() {
        ToastUtils.show("save success");
        setToShowStatus();
    }

    @Override
    public void onAddFailed(String reason) {
        ToastUtils.show("save failed " + reason);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (featureTag != AppConfig.FEATURE_ADD) {
            NoteListBean noteListBean = bundle.getParcelable("note");
            titleEdt.setText(noteListBean.getTitle());
            contentEdt.setText(noteListBean.getContent());
            noteId = noteListBean.getNoteId();
        }
    }
}
