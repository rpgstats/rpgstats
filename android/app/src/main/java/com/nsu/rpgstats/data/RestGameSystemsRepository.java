package com.nsu.rpgstats.data;

import android.net.ConnectivityManager;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.network.RestClient;
import com.nsu.rpgstats.network.RpgstatsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestGameSystemsRepository implements GameSystemsRepository {
    private static final String TAG = RestGameSystemsRepository.class.getSimpleName();
    private final RpgstatsService service;

    public RestGameSystemsRepository() {
        service = RestClient.getInstance().getRpgstatsService();
        addGameSystem("testrest", new RepositoryCallback<GameSystem>() {
            @Override
            public void onComplete(Result<GameSystem> result) {
                if (result instanceof Result.Success) {
                    Log.i(TAG, ((Result.Success<GameSystem>) result).data.getSystemName());
                } else if (result instanceof Result.Error){
                    Log.e(TAG, ((Result.Error<GameSystem>) result).throwable.getMessage());
                }
            }
        });
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
    public void addGameSystem(String gameSystemName, RepositoryCallback<GameSystem> callback) {
        service.addGameSystem(new GameSystem(gameSystemName))
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
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }
}
