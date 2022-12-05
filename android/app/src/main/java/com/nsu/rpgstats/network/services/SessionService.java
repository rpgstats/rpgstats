package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.network.dto.ErrorResponse;
import com.nsu.rpgstats.network.dto.SessionRequest;
import com.nsu.rpgstats.network.dto.SessionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SessionService {

    @GET("user/sessions")
    Call<List<SessionResponse>> getSessions();

    @PUT("user/sessions/{sessionId}")
    Call<ErrorResponse> updateSession(@Path("sessionId") int id);

    @DELETE("user/sessions/{sessionId}")
    Call<String> deleteSession(@Path("sessionId") int id);

    @POST("/user/sessions")
    Call<SessionResponse> addSession(@Body SessionRequest session);
}
