package com.jinjunhuang.notebook.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.demo.jianjunhuang.mvptools.utils.HLog;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.contract.NoteListContract;
import com.jinjunhuang.notebook.model.bean.NoteListBean;
import com.jinjunhuang.notebook.presenter.impl.NoteListPresenter;
import com.jinjunhuang.notebook.utils.NoteOnScrollListener;
import com.jinjunhuang.notebook.view.adapter.NoteListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteListFragment extends BaseFragment implements NoteListContract.View<NoteListBean> {

    private RecyclerView noteListRV;

    private NoteListAdapter mAdapter;

    private List<NoteListBean> mList = new ArrayList<>();

    private NoteListPresenter presenter;

    private int delPos;

    private int fragmentType;

    public static NoteListFragment newInstance(int fragmentType) {
        NoteListFragment fragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(AppConfig.FRAGMENT_TYPE_KEY, fragmentType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        fragmentType = bundle.getInt(AppConfig.FRAGMENT_TYPE_KEY, AppConfig.NOTE_FRAGMENT_TYPE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        presenter = new NoteListPresenter(this);
        presenter.onStart();
        return R.layout.note_list_fragment;
    }

    @Override
    protected void initView(View view) {
        noteListRV = findView(R.id.note_list_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        noteListRV.setLayoutManager(manager);
        if (mAdapter == null) {
            mAdapter = new NoteListAdapter(getContext(), mList, R.layout.note_list_item_layout, fragmentType);
        }
        noteListRV.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        noteListRV.addOnScrollListener(new NoteOnScrollListener());
        mAdapter.setOnItemPopupMenuClickListener(new NoteListAdapter.OnItemPopupMenuClickListener() {
            @Override
            public void onItemPopupMenuClickListener(MenuItem item, int position) {
                if (item.getItemId() == R.id.del_menu) {
                    delPos = position;
                    NoteListBean bean = mList.get(position);
                    presenter.delNote(bean.getNoteId());
                }
            }
        });
    }

    private static final String TAG = "NoteListFragment";

    @Override
    public void onFailed(int code, String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onSuccess(List<NoteListBean> notes) {
        this.mList.clear();
        mList.addAll(notes);
        mAdapter.setOnDataChange(mList);
    }

    @Override
    public void onDelSuccess() {
        mList.remove(delPos);
        mAdapter.setOnDataChange(mList);
    }

    @Override
    public void onDelFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData(fragmentType);
    }

}
