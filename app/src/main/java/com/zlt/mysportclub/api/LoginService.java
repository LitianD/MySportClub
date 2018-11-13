package com.zlt.mysportclub.api;

import com.zlt.mysportclub.model.ResultReturn;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("LoginServlet")
    public Call<ResultReturn> login(@Field("username") String name,
                                    @Field("password") String password);
}
