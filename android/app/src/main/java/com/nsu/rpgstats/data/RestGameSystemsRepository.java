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
    }


    @Override
    public List<GameSystem> getGameSystems() {
        final MutableLiveData<List<GameSystem>> gameSystems = new MutableLiveData<>();
        service.getGameSystems()
                .enqueue(new Callback<List<GameSystem>>() {
                    @Override
                    public void onResponse(Call<List<GameSystem>> call, Response<List<GameSystem>> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            gameSystems.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GameSystem>> call, Throwable t) {
                        gameSystems.setValue(null);
                    }
                });
        return gameSystems.getValue();
    }

    @Override
    public GameSystem getGameSystem(int id) {
        final MutableLiveData<GameSystem> gameSystem = new MutableLiveData<>();
        service.getGameSystem(id)
                .enqueue(new Callback<GameSystem>() {
                    @Override
                    public void onResponse(Call<GameSystem> call, Response<GameSystem> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            gameSystem.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<GameSystem> call, Throwable t) {
                        gameSystem.setValue(null);
                    }
                });
        return gameSystem.getValue();
    }

    @Override
    public int addGameSystem(String gameSystemName) {
        final MutableLiveData<GameSystem> gameSystem = new MutableLiveData<>();
        service.addGameSystem(new GameSystem(gameSystemName))
                .enqueue(new Callback<GameSystem>() {
                    @Override
                    public void onResponse(Call<GameSystem> call, Response<GameSystem> response) {
                        Log.d(TAG, "Response: " + response);
                    }

                    @Override
                    public void onFailure(Call<GameSystem> call, Throwable t) {
                        gameSystem.setValue(null);
                    }
                });
        return gameSystem.getValue().getId();
    }
}
