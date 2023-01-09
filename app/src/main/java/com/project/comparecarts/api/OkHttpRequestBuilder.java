package com.project.comparecarts.api;


import okhttp3.Request;

public class OkHttpRequestBuilder {
    public static Request getRequest(String url) {
        return new Request.Builder().url(url).get().build();
    }
}
