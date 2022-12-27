package com.nsu.rpgstats.network.services;
import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GamesystemsService {
    @GET("user/game-systems")
    Call<List<GameSystem>> getGameSystems();

    @GET("user/game-systems/{id}")
    Call<GameSystem> getGameSystem(@Path("id") int id);

    @POST("user/game-systems")
    Call<GameSystem> addGameSystem(@Body GameSystem GameSystem);
}
