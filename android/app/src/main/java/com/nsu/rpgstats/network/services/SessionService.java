package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.entities.SessionCharacter;
import com.nsu.rpgstats.network.dto.ErrorResponse;
import com.nsu.rpgstats.network.dto.SessionRequest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SessionService {

    @GET("user/sessions")
    Call<List<Session>> getSessions();

    @PUT("user/sessions/{sessionId}")
    Call<ErrorResponse> updateSession(@Path("sessionId") int id);

    @DELETE("user/sessions/{sessionId}")
    Call<String> deleteSession(@Path("sessionId") int id);

    @POST("user/sessions")
    Call<Session> addSession(@Body SessionRequest session);

    @GET("user/sessions/{sessionId}/characters")
    Call<List<SessionCharacter>> getSessionCharacters(@Path("sessionId") int id);

    @GET("user/sessions/{sessionId}/characters/{characterId}")
    Call<Character> getSessionCharacter(@Path("sessionId") int sessionId,
                                        @Path("characterId") int characterId);

    @GET("user/sessions/{sessionId}/characters/{characterId}")
    Call<String> deleteSessionCharacter(@Path("sessionId") int sessionId,
                                        @Path("characterId") int characterId);
}
