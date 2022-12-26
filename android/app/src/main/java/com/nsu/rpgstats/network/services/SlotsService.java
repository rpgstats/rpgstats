package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.network.dto.CreateCharacterSlotPostRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SlotsService {
    @GET("user/characters/{characterId}/slots")
    Call<List<Slot>> getSlots(@Path("characterId") int characterId);

    @POST("user/characters/{characterId}/slots")
    Call<Slot> addSlot(@Path("characterId") int characterId, @Body CreateCharacterSlotPostRequest request);

    @GET("user/characters/{characterId}/slots/{slotId}")
    Call<Slot> getSlot(@Path("characterId") int characterId, @Path("slotId") int slotId);

    @PUT("user/characters/{characterId}/slots/{slotId}")
    Call<Slot> editSlot(@Path("characterId") int characterId, @Path("slotId") int slotId, @Body CreateCharacterSlotPostRequest request);

    @DELETE("user/characters/{characterId}/slots/{slotId}")
    Call<Slot> deleteSlot(@Path("characterId") int characterId, @Path("slotId") int slotId);

    @GET("user/characters/{characterId}/slots/{slotId}/tags")
    Call<List<Tag>> getTags(@Path("characterId") int characterId, @Path("slotId") int slotId);

    @POST("user/characters/{characterId}/slots/{slotId}/tags")
    Call<Tag> addTag(@Path("characterId") int characterId, @Path("slotId") int slotId, @Body Tag tag);
}
