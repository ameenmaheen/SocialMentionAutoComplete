package com.example.ameen.rxandroidtest.api;

import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {

    private static Api ourInstance = new Api();
    private ApiMethods apiMethods;

    private Api() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(ApiEndPoints.BASE_URL_API)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .serializeNulls()

                        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create()))
                .build();
        apiMethods = retrofit.create(ApiMethods.class);
    }

    public static Api getInstance() {
        return ourInstance;
    }

    public ApiMethods getApiMethods() {
        return apiMethods;
    }

}
