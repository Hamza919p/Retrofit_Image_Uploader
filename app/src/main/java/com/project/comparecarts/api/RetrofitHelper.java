package com.project.comparecarts.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static Retrofit getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder().baseUrl(ApiEndPoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
    }
}
