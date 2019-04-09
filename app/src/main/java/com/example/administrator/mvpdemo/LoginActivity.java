package com.example.administrator.mvpdemo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.mvpdemo.base.BaseMVPActivity;
import com.example.administrator.mvpdemo.mvp.LoginContract;
import com.example.administrator.mvpdemo.mvp.LoginPresenter;

/**
 * username:testxx
 * password:123123123
 */
public class LoginActivity extends BaseMVPActivity<LoginPresenter> implements LoginContract.View {
    private EditText et_username;
    private EditText et_password;

    @Override
    protected int createView() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void init() {
        setTitle("MVP");
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    public void login(View v) {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入数据", Toast.LENGTH_SHORT).show();
            return;
        }

        mPresenter.login(username, password);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail(String msg) {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, "登录异常", Toast.LENGTH_SHORT).show();
    }
}
