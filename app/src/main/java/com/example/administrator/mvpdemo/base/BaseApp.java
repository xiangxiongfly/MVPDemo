package com.example.administrator.mvpdemo.base;

import android.app.Application;

public class BaseApp extends Application {
    private static BaseApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static BaseApp getApplication() {
        return mInstance;
    }
}
