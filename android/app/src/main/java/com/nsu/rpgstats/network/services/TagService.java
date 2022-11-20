package com.nsu.rpgstats.network.services;

import com.nsu.rpgstats.entities.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TagService {
    @GET("game-systems/{game-system-id}/tags")
    Call<List<Tag>> getTags(@Path("game-system-id") int systemId);

    @GET("game-systems/{game-system-id}/tags/{tag-id}")
    Call<Tag> getTag(@Path("game-system-id") int systemId, @Path("tag-id") int tagId);

    @POST("user/game-systems/{game-system-id}/tags")
    Call<Tag> addTag(@Path("game-system-id") int systemId, @Body Tag tag);

    @PUT("user/game-systems/{game-system-id}/tags/{tag-id}")
    Call<Tag> editTag(@Path("game-system-id") int systemId, @Path("tag-id") int tagId, @Body Tag tag);

    @DELETE("user/game-systems/{game-system-id}/tags/{tag-id}")
    Call<Tag> deleteTag(@Path("game-system-id") int systemId, @Path("tag-id") int tagId);
}
