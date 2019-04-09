package com.example.administrator.mvpdemo.mvp;

import com.example.administrator.mvpdemo.base.BasePresenter;
import com.example.administrator.mvpdemo.bean.LoginBean;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * P层实现
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    private LoginContract.Model model;
    private final CompositeDisposable mDisposable;

    public LoginPresenter() {
        this.model = new LoginModel();
        mDisposable = new CompositeDisposable();
    }

    @Override
    public void login(String username, String password) {
        model.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable.add(d);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (isViewAttached()) {
                            if (loginBean.errorCode == 0) {
                                mView.loginSuccess();
                            } else {
                                mView.loginFail(loginBean.errorMsg);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isViewAttached()) {
                            mView.loginError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        mDisposable.clear();
    }
}