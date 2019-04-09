package com.example.administrator.mvpdemo.mvp;

import com.example.administrator.mvpdemo.base.interfaces.IView;
import com.example.administrator.mvpdemo.bean.LoginBean;

import io.reactivex.Observable;

public interface LoginContract {
    interface View extends IView {
        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         */
        void loginFail(String fail);

        /**
         * 登录异常
         */
        void loginError(String error);
    }

    interface Presenter {
        void login(String username, String password);
    }

    interface Model {
        Observable<LoginBean> login(String username, String password);
    }
}
