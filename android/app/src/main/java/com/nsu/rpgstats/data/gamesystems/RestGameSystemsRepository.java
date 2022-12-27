package com.nsu.rpgstats.data.gamesystems;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.network.RestClient;
import com.nsu.rpgstats.network.services.GamesystemsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestGameSystemsRepository implements GameSystemsRepository {
    private static final String TAG = RestGameSystemsRepository.class.getSimpleName();
    private final GamesystemsService service;

    public RestGameSystemsRepository(GamesystemsService gamesystemsService) {
        service = gamesystemsService;
    }


    @Override
    public void getGameSystems(RepositoryCallback<List<GameSystem>> callback) {

        service.getGameSystems()
                .enqueue(new Callback<List<GameSystem>>() {
                    @Override
                    public void onResponse(Call<List<GameSystem>> call, Response<List<GameSystem>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GameSystem>> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void getGameSystem(int id, RepositoryCallback<GameSystem> callback) {
        service.getGameSystem(id)
                .enqueue(new Callback<GameSystem>() {
                    @Override
                    public void onResponse(Call<GameSystem> call, Response<GameSystem> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<GameSystem> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(null));
                    }
                });
    }

    @Override
    public void addGameSystem(String gameSystemName, String description, RepositoryCallback<GameSystem> callback) {
        service.addGameSystem(new GameSystem(gameSystemName, description))
                .enqueue(new Callback<GameSystem>() {
                    @Override
                    public void onResponse(Call<GameSystem> call, Response<GameSystem> response) {
                        Log.d(TAG, "Response: " + response + " " + call.request().headers().toString());
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<GameSystem> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }
}
