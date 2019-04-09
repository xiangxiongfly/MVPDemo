package com.example.administrator.mvpdemo.network;

import com.example.administrator.mvpdemo.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("user/login")
    @FormUrlEncoded
    Observable<LoginBean> userLogin(@Field("username") String username, @Field("password") String password);

}
