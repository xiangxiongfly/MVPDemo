package com.example.administrator.mvpdemo.base;

import com.example.administrator.mvpdemo.base.interfaces.IPresenter;
import com.example.administrator.mvpdemo.base.interfaces.IView;

public class BasePresenter<V extends IView> implements IPresenter<V> {
    protected V mView;

    @Override
    public void attachView(V view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }
}
