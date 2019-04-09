package com.example.administrator.mvpdemo.base.interfaces;

public interface IPresenter<V extends IView> {
    /**
     * 建立联系
     */
    void attachView(V view);

    /**
     * 取消联系
     */
    void detachView();

    /**
     * 判断View是否销毁
     */
    boolean isViewAttached();
}
