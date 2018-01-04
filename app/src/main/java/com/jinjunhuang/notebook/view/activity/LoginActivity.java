package com.jinjunhuang.notebook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;
import com.jinjunhuang.notebook.contract.LoginContract;
import com.jinjunhuang.notebook.presenter.impl.LoginPresenter;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    private TextInputEditText usrEdt;
    private TextInputEditText pwdEdt;
    private TextInputEditText surePwdEdt;
    private TextInputLayout usrLayout;
    private TextInputLayout pwdLayout;
    private TextInputLayout surePwdLayout;
    private FloatingActionButton loginFab;

    private boolean isLoginStatus = true;

    private LoginPresenter loginPresenter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loginPresenter = new LoginPresenter(this);
        loginPresenter.onStart();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        toolbar = findView(R.id.actionbar);
        setSupportActionBar(toolbar);

        usrEdt = findView(R.id.login_input_username_edt);
        pwdEdt = findView(R.id.login_input_pwd_edt);
        loginFab = findView(R.id.login_btn);
        usrLayout = findView(R.id.login_input_username_layout);
        pwdLayout = findView(R.id.login_input_pwd_layout);
        surePwdEdt = findView(R.id.login_input_sure_pwd_edt);
        surePwdLayout = findView(R.id.login_input_sure_pwd_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.register_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (!isLoginStatus) {
            inflater.inflate(R.menu.login_menu, menu);
        } else {
            inflater.inflate(R.menu.register_menu, menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void changeToRegister(boolean tag) {
        if (tag) {
            isLoginStatus = false;
            surePwdLayout.setVisibility(View.VISIBLE);
        } else {
            isLoginStatus = true;
            surePwdLayout.setVisibility(View.GONE);
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_status_menu: {
                changeToRegister(false);
                break;
            }
            case R.id.register_status_menu: {
                changeToRegister(true);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void initListener() {
        loginFab.setOnClickListener(this);
    }

    private static final String TAG = "LoginActivity";
    
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn) {

            String username = usrEdt.getText().toString();
            String password = pwdEdt.getText().toString();
            if (isEmptyOrNull(username)) {
                usrLayout.setError("请输入用户名");
                return;
            }
            if (isEmptyOrNull(password)) {
                pwdLayout.setError("请输入密码");
                return;
            }
            if (isLoginStatus) {
                //TODO login
                loginPresenter.login(username, password);
                Log.i(TAG, "onClick: login");
            } else {
                //TODO register
                String surePwd = surePwdEdt.getText().toString();
                if (isEmptyOrNull(surePwd)) {
                    surePwdLayout.setError("请再次确认密码");
                    return;
                }
                if (!password.equals(surePwd)) {
                    surePwdLayout.setError("两次输入的密码不一样");
                    return;
                }
                loginPresenter.register(username, password);
                Log.i(TAG, "onClick: register");
            }

        }
    }

    @Override
    public void onLoginSuccess() {

        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed(String reason) {
        showShort(reason);
    }

    @Override
    public void onRegisterSuccess() {
        showShort("注册成功");
        changeToRegister(false);
    }

    @Override
    public void onRegisterFailed(String reason) {
        showShort(reason);
    }
}
