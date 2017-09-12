package com.jinjunhuang.notebook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.view.fragment.NoteListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class HomepageActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton addFab;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.homepage_activity;
    }

    @Override
    protected void initView() {
        initToolbar();
        initTabLayout();
        initFragments();
        initViewPager();
        addFab = findView(R.id.add_fab);
    }

    private void initToolbar() {
        mToolbar = findView(R.id.actionbar);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initTabLayout() {
        mTabLayout = findView(R.id.homepage_tl);
    }

    private void initViewPager() {
        mViewPager = findView(R.id.homepage_vp);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        fragments.add(NoteListFragment.newInstance(AppConfig.NOTE_FRAGMENT_TYPE));
        fragments.add(NoteListFragment.newInstance(AppConfig.DRAFT_FRAGMENT_TYPE));
    }


    @Override
    protected void initListener() {
        addFab.setOnClickListener(this);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.exit_menu) {
                    SPUtils.instance(AppConfig.SP_USR_INFO_FILE_NAME).clearAll();
                    Intent intent = new Intent(HomepageActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_fab) {
            Intent intent = new Intent(HomepageActivity.this, AddNoteActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt(AppConfig.FEATURE_TAG_KEY, AppConfig.FEATURE_ADD);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private String[] titles = {"笔记", "草稿"};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
