package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.GameSystemsRepository;
import com.nsu.rpgstats.data.PlugGameSystemsRepository;
import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;

// A ViewModel usually shouldn't reference a view, Lifecycle, or any class that may hold a reference to the activity context.
public class GameSystemsViewModel extends AndroidViewModel {
    private MutableLiveData<List<GameSystem>> gameSystems;
    private final GameSystemsRepository gameSystemsRepository;

    public GameSystemsViewModel(@NonNull Application application) {
        super(application);
        gameSystemsRepository = ((RpgstatsApplication)getApplication()).appContainer.gameSystemsRepository;
    }


    public LiveData<List<GameSystem>> getGameSystems() {
        if (gameSystems == null) {
            gameSystems = new MutableLiveData<>();
            loadGameSystems();
        }
        return gameSystems;
    }

    public void addGameSystem(String gameSystemName) {
        if (gameSystems == null) {
            gameSystems = new MutableLiveData<>();
            loadGameSystems();
        }
        // todo: find better approach

        int gsId = gameSystemsRepository.addGameSystem(gameSystemName);
        GameSystem gameSystem = gameSystemsRepository.getGameSystem(gsId);
        gameSystems.getValue().add(gameSystem);
        gameSystems.setValue(gameSystems.getValue());
        Log.e("ADD GAME SYSTEM", "successfully add gs");
    }

    private void loadGameSystems() {
        // suppose getting from server in future
        GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
        gameSystems.setValue(gameSystemsRepository.getGameSystems());
    }
}
