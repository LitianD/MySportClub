package com.zlt.mysportclub.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    // Trailing slash is needed
    public static final String BASE_URL = "http://172.27.206.57:8080/";
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //GitHubService service = retrofit.create(GitHubService.class);
}