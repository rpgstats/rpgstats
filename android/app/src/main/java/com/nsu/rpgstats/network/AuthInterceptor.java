package com.nsu.rpgstats.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String token  = null;

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder();
        if (token != null) {
            request.addHeader("Authorization", "Bearer " + token);
        }
        return chain.proceed(request.build());
    }

    public void setToken(String token) {
        this.token = token;
    }
}
