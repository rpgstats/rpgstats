package com.nsu.rpgstats.network;

import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.network.DTO.TagDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TagService {
    @GET("/game-systems/{game-system-id}/tags/")
    Call<List<TagDTO>> getTags(@Path("game-system-id") int systemId);

    @GET("/game-systems/{game-system-id}/tags/{tag-id}")
    Call<TagDTO> getTag(@Path("game-system-id") int systemId, @Path("tag-id") int tagId);

    @POST("/game-systems/{game-system-id}/tags")
    Call<TagDTO> addTag(@Path("game-system-id") int systemId, TagDTO tag);

    @POST("/game-systems/{game-system-id}/tags/{tag-id}")
    Call<TagDTO> editTag(@Path("game-system-id") int systemId, @Path("tag-id") int tagId, TagDTO tag);

    @DELETE("/game-systems/{game-system-id}/tags/{tag-id}")
    Call<TagDTO> deleteTag(@Path("game-system-id") int systemId, @Path("tag-id") int tagId);
}
