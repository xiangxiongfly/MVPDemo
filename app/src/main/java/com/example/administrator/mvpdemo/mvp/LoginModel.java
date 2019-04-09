package com.example.administrator.mvpdemo.mvp;

import com.example.administrator.mvpdemo.bean.LoginBean;
import com.example.administrator.mvpdemo.network.HttpService;

/**
 * M层实现
 */
public class LoginModel implements LoginContract.Model {
    @Override
    public io.reactivex.Observable<LoginBean> login(String username, String password) {
        return HttpService.getInstance().userLogin(username, password);
    }
}