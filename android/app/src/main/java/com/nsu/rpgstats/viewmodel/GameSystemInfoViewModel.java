package com.nsu.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.GameSystemsRepository;
import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.GameSystem;

public class GameSystemInfoViewModel extends ViewModel {
    private final int gameId;
    private final GameSystemsRepository repository;
    private MutableLiveData<GameSystem> gameSystem;

    public GameSystemInfoViewModel(int gameId, GameSystemsRepository repository) {
        this.gameId = gameId;
        this.repository = repository;
    }

    public LiveData<GameSystem> getGameSystemInfo(){
        if (gameSystem == null) {
            gameSystem = new MutableLiveData<>();
            loadGameSystem();
        }
        return gameSystem;
    }

    private void loadGameSystem() {
        repository.getGameSystem(gameId, new RepositoryCallback<GameSystem>() {
            @Override
            public void onComplete(Result<GameSystem> result) {
                if (result instanceof Result.Success) {
                    gameSystem.setValue(((Result.Success<GameSystem>) result).data);
                }
            }
        });
    }

}
