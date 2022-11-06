package com.nsu.rpgstats.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.network.DTO.TagDTO;
import com.nsu.rpgstats.network.TagService;
import com.nsu.rpgstats.network.RestClient;
import com.nsu.rpgstats.network.RpgstatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestTagRepository implements TagRepository{
    private static final String TAG = RestTagRepository.class.getSimpleName();
    private final TagService service;

    public RestTagRepository() {
        service = RestClient.getInstance().getTagService();
    }

    @Override
    public List<Tag> getTags(int gameSystemId) {
        final MutableLiveData<List<Tag>> tags = new MutableLiveData<>();
        service.getTags(gameSystemId)
                .enqueue(new Callback<List<TagDTO>>() {
                    @Override
                    public void onResponse(Call<List<TagDTO>> call, Response<List<TagDTO>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            List<Tag> tagList = new ArrayList<>();
                            for (TagDTO tag :
                                    response.body()) {
                                tagList.add(new Tag(tag));
                            }
                            tags.setValue(tagList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TagDTO>> call, Throwable t) {
                        tags.setValue(null);
                    }
                });
        return tags.getValue();
    }

    @Override
    public Tag getTag(int gameSystemId, int id) {
        final MutableLiveData<Tag> tags = new MutableLiveData<>();
        service.getTag(gameSystemId, id)
                .enqueue(new Callback<TagDTO>() {
                    @Override
                    public void onResponse(Call<TagDTO> call, Response<TagDTO> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            tags.setValue(new Tag(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<TagDTO> call, Throwable t) {
                        tags.setValue(null);
                    }
                });
        return tags.getValue();
    }

    @Override
    public int addTag(int gameSystemId, Tag tag) {
        final MutableLiveData<Tag> tags = new MutableLiveData<>();
        service.addTag(gameSystemId, new TagDTO(tag, gameSystemId))
                .enqueue(new Callback<TagDTO>() {
                    @Override
                    public void onResponse(Call<TagDTO> call, Response<TagDTO> response) {
                        if (response.body() != null) {
                            tags.setValue(new Tag(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<TagDTO> call, Throwable t) {
                        tags.setValue(null);
                    }
                });
        return tags.getValue().getId();
    }

    @Override
    public int editTag(int gameSystemId, int id, Tag tag) {
        final MutableLiveData<Tag> tags = new MutableLiveData<>();
        service.editTag(gameSystemId, id, new TagDTO(tag, gameSystemId))
                .enqueue(new Callback<TagDTO>() {
                    @Override
                    public void onResponse(Call<TagDTO> call, Response<TagDTO> response) {
                        if (response.body() != null) {
                            tags.setValue(new Tag(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<TagDTO> call, Throwable t) {
                        tags.setValue(null);
                    }});
        return tags.getValue() == null ? -1 : tags.getValue().getId();
    }
}
