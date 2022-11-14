package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.entities.Modifier;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ModifierService {
    @GET("/game-systems/{game-system-id}/modifiers/")
    Call<List<Modifier>> getModifiers(@Path("game-system-id") int systemId);

    @GET("/game-systems/{game-system-id}/modifiers/{modifier-id}")
    Call<Modifier> getModifier(@Path("game-system-id") int systemId, @Path("modifier-id") int modifierId);

    @POST("/game-systems/{game-system-id}/modifiers")
    Call<Modifier> addModifier(@Path("game-system-id") int systemId, @Body Modifier modifier);

    @POST("/game-systems/{game-system-id}/modifiers/{modifier-id}")
    Call<Modifier> editModifier(@Path("game-system-id") int systemId, @Path("modifier-id") int modifierId, @Body Modifier modifier);

    @DELETE("/game-systems/{game-system-id}/modifiers/{modifier-id}")
    Call<Modifier> deleteModifier(@Path("game-system-id") int systemId, @Path("modifier-id") int modifierId);
}
