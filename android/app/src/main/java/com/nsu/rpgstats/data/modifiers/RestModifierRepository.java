package com.nsu.rpgstats.data.modifiers;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.network.services.ModifierService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestModifierRepository implements ModifierRepository{
    private static final String TAG = RestModifierRepository.class.getSimpleName();
    private final ModifierService service;

    public RestModifierRepository(ModifierService service) {
        this.service = service;
    }

    @Override
    public void getModifiers(int gameSystemId, RepositoryCallback<List<Modifier>> callback) {
        service.getModifiers(gameSystemId)
                .enqueue(new Callback<List<Modifier>>() {
                    @Override
                    public void onResponse(Call<List<Modifier>> call, Response<List<Modifier>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Modifier>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void getModifier(int gameSystemId, int id, RepositoryCallback<Modifier> callback) {
        service.getModifier(gameSystemId, id)
                .enqueue(new Callback<Modifier>() {
                    @Override
                    public void onResponse(Call<Modifier> call, Response<Modifier> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Modifier> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void addModifier(int gameSystemId, Modifier modifier, RepositoryCallback<Modifier> callback) {
        service.addModifier(gameSystemId, modifier)
                .enqueue(new Callback<Modifier>() {
                    @Override
                    public void onResponse(Call<Modifier> call, Response<Modifier> response) {
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<Modifier> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void editModifier(int gameSystemId, int id, Modifier modifier, RepositoryCallback<Modifier> callback) {
        service.editModifier(gameSystemId, id, modifier)
                .enqueue(new Callback<Modifier>() {
                    @Override
                    public void onResponse(Call<Modifier> call, Response<Modifier> response) {
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<Modifier> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }});
    }
}
