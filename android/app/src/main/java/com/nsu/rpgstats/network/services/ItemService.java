package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ItemService {
    @GET("game-systems/{game-system-id}/items")
    Call<List<Item>> getItems(@Path("game-system-id") int systemId);

    @GET("game-systems/{game-system-id}/items/{item-id}")
    Call<Item> getItem(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @POST("user/game-systems/{game-system-id}/items")
    Call<Item> addItem(@Path("game-system-id") int systemId, @Body Item item);

    @PUT("user/game-systems/{game-system-id}/items/{item-id}")
    Call<Item> editItem(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Body Item item);

    @DELETE("user/game-systems/{game-system-id}/items/{item-id}")
    Call<Item> deleteItem(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @GET("game-systems/{game-system-id}/items/{item-id}/tags")
    Call<List<Tag>> getItemTags(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @POST("user/game-systems/{game-system-id}/items/{item-id}/tags")
    Call<List<Tag>> addItemTags(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Body List<Tag> tags);

    @DELETE("user/game-systems/{game-system-id}/items/{item-id}/tags/{tag-id}")
    Call<Tag> deleteItemTag(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Path("tag-id") int tagId);

    @GET("game-systems/{game-system-id}/items/{item-id}/modifiers")
    Call<List<Modifier>> getItemModifiers(@Path("game-system-id") int systemId, @Path("item-id") int itemId);

    @POST("user/game-systems/{game-system-id}/items/{item-id}/modifiers")
    Call<List<Modifier>> addItemModifiers(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Body List<Modifier> modifiers);

    @DELETE("user/game-systems/{game-system-id}/items/{item-id}/modifiers/{modifier-id}")
    Call<Modifier> deleteItemModifier(@Path("game-system-id") int systemId, @Path("item-id") int itemId, @Path("tag-id") int modifierId);
}
