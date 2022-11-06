package com.nsu.rpgstats.network;

import com.nsu.rpgstats.network.DTO.ItemDTO;
import com.nsu.rpgstats.network.DTO.ModifierDTO;
import com.nsu.rpgstats.network.DTO.TagDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItemService {
    @GET("/game-systems/{game-system-id}/items/")
    Call<List<ItemDTO>> getItems(@Path("game-system-id") int systemId);

    @GET("/game-systems/{game-system-id}/items/{item-id}")
    Call<ItemDTO> getItem(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @POST("/game-systems/{game-system-id}/items")
    Call<ItemDTO> addItem(@Path("game-system-id") int systemId, @Body ItemDTO item);

    @POST("/game-systems/{game-system-id}/items/{item-id}")
    Call<ItemDTO> editItem(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Body ItemDTO item);

    @DELETE("/game-systems/{game-system-id}/items/{item-id}")
    Call<ItemDTO> deleteItem(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @GET("/game-systems/{game-system-id}/items/{item-id}/tags")
    Call<List<TagDTO>> getItemTags(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @POST("/game-systems/{game-system-id}/items/{item-id}/tags")
    Call<List<TagDTO>> addItemTags(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Body List<TagDTO> tags);

    @DELETE("/game-systems/{game-system-id}/items/{item-id}/tags/{tag-id}")
    Call<TagDTO> deleteItemTag(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Path("tag-id") int tagId);

    @GET("/game-systems/{game-system-id}/items/{item-id}/modifiers")
    Call<List<ModifierDTO>> getItemModifiers(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @POST("/game-systems/{game-system-id}/items/{item-id}/modifiers")
    Call<List<ModifierDTO>> addItemModifiers(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Body List<ModifierDTO> modifiers);

    @DELETE("/game-systems/{game-system-id}/items/{item-id}/modifiers/{modifier-id}")
    Call<ModifierDTO> deleteItemModifier(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Path("tag-id") int modifierId);
}
