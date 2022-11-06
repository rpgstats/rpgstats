package com.nsu.rpgstats.network;

import com.nsu.rpgstats.network.DTO.ModifierDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ModifierService {
    @GET("/game-systems/{game-system-id}/modifiers/")
    Call<List<ModifierDTO>> getModifiers(@Path("game-system-id") int systemId);

    @GET("/game-systems/{game-system-id}/modifiers/{modifier-id}")
    Call<ModifierDTO> getModifier(@Path("game-system-id") int systemId, @Path("modifier-id") int modifierId);

    @POST("/game-systems/{game-system-id}/modifiers")
    Call<ModifierDTO> addModifier(@Path("game-system-id") int systemId, @Body ModifierDTO modifier);

    @POST("/game-systems/{game-system-id}/modifiers/{modifier-id}")
    Call<ModifierDTO> editModifier(@Path("game-system-id") int systemId, @Path("modifier-id") int modifierId, @Body ModifierDTO modifier);

    @DELETE("/game-systems/{game-system-id}/modifiers/{modifier-id}")
    Call<ModifierDTO> deleteModifier(@Path("game-system-id") int systemId, @Path("modifier-id") int modifierId);
}
