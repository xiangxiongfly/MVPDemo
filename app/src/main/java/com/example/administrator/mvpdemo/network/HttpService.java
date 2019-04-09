package com.example.administrator.mvpdemo.network;

import com.example.administrator.mvpdemo.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {
    private static final String BASE_URL = "https://www.wanandroid.com/";
    private ApiService apiService;

    private static class Wrapper {
        static HttpService httpService = new HttpService();
    }

    private HttpService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static HttpService getInstance() {
        return Wrapper.httpService;
    }

    /**
     * 登录
     */
    public Observable<LoginBean> userLogin(String username, String password) {
        return apiService.userLogin(username, password);
    }


}
