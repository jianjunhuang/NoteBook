package com.jinjunhuang.notebook.view.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.SPUtils;
import com.jinjunhuang.notebook.R;
import com.jinjunhuang.notebook.common.AppConfig;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextInputEditText usrEdt;
    private TextInputEditText pwdEdt;
    private TextInputLayout usrLayout;
    private TextInputLayout pwdLayout;
    private FloatingActionButton loginFab;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        usrEdt = findView(R.id.login_input_username_edt);
        pwdEdt = findView(R.id.login_input_pwd_edt);
        loginFab = findView(R.id.login_btn);
        usrLayout = findView(R.id.login_input_username_layout);
        pwdLayout = findView(R.id.login_input_pwd_layout);
    }

    @Override
    protected void initListener() {
        loginFab.setOnClickListener(this);
    }

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
            SPUtils sp = SPUtils.instance(AppConfig.SP_USR_INFO_FILE_NAME);
            sp.store("username", AppConfig.SP_USR_KEY);
            sp.store("password", AppConfig.SP_PWD_KEY);
            Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
            startActivity(intent);
        }
    }
}
