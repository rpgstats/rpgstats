package com.nsu.rpgstats.data.tags;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.network.services.TagService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestTagRepository implements TagRepository{
    private static final String TAG = RestTagRepository.class.getSimpleName();
    private final TagService service;

    public RestTagRepository(TagService service) {
        this.service = service;
    }

    @Override
    public void getTags(int gameSystemId, RepositoryCallback<List<Tag>> callback) {
        service.getTags(gameSystemId)
                .enqueue(new Callback<List<Tag>>() {
                    @Override
                    public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                            return;
                        }
                        callback.onComplete(new Result.Success<>(new ArrayList<>()));
                    }

                    @Override
                    public void onFailure(Call<List<Tag>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void getTag(int gameSystemId, int id, RepositoryCallback<Tag> callback) {
        service.getTag(gameSystemId, id)
                .enqueue(new Callback<Tag>() {
                    @Override
                    public void onResponse(Call<Tag> call, Response<Tag> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Tag> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void addTag(int gameSystemId, Tag tag, RepositoryCallback<Tag> callback) {
        service.addTag(gameSystemId, tag)
                .enqueue(new Callback<Tag>() {
                    @Override
                    public void onResponse(Call<Tag> call, Response<Tag> response) {
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<Tag> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void editTag(int gameSystemId, int id, Tag tag, RepositoryCallback<Tag> callback) {
        service.editTag(gameSystemId, id, tag)
                .enqueue(new Callback<Tag>() {
                    @Override
                    public void onResponse(Call<Tag> call, Response<Tag> response) {
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<Tag> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }});
    }
}
