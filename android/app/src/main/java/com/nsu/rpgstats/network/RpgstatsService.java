package com.nsu.rpgstats.network;
import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RpgstatsService {
    @GET("api/v1//game-systems")
    Call<List<GameSystem>> getGameSystems();

    @GET("api/v1/game-systems/{id}")
    Call<GameSystem> getGameSystem(@Path("id") int id);

    @POST("api/v1/game-systems")
    Call<GameSystem> addGameSystem(@Body GameSystem GameSystem);
}
