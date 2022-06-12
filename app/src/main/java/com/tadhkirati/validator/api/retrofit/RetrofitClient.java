package com.tadhkirati.validator.api.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.103.164:8000/";

    private static OkHttpClient getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(chain -> {
            var request = chain.request();
            var response = chain.proceed(request);
            Log.i("Repsonse", response.body().string());
            Log.i("REQUEST", request.toString());
            return response;
        });

        return builder.build();
    }

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final ApiService apiService = retrofit.create(ApiService.class);


}
