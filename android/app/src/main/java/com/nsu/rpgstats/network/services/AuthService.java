package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.network.dto.LoginRequest;
import com.nsu.rpgstats.network.dto.LoginResponse;
import com.nsu.rpgstats.network.dto.SignupRequest;
import com.nsu.rpgstats.network.dto.MessageResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("auth")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("signup")
    Call<MessageResponse> signup(@Body SignupRequest request);
}
