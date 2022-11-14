package com.nsu.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.gamesystems.GameSystemsRepository;
import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.GameSystem;

public class GameSystemInfoViewModel extends ViewModel {
    private final GameSystemsRepository repository;
    private MutableLiveData<GameSystem> gameSystem;

    public GameSystemInfoViewModel(GameSystem gameSystem, GameSystemsRepository repository) {
        this.gameSystem = new MutableLiveData<>(gameSystem);
        this.repository = repository;
    }

    public LiveData<GameSystem> getGameSystemInfo(){
        return gameSystem;
    }

}
