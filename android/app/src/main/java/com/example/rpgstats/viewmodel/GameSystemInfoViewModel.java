package com.example.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rpgstats.data.GameSystemsRepository;
import com.example.rpgstats.entities.GameSystem;

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
        gameSystem.setValue(repository.getGameSystem(gameId));
    }

}
