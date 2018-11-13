package com.zlt.mysportclub.api;

import com.zlt.mysportclub.model.ResultReturn;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterApi {
    @FormUrlEncoded
    @POST("/LoginServer/register.php")
    public Call<ResultReturn> register(@Field("name") String name, @Field("email") String email, @Field("contact") String contact, @Field("password") String password);
}