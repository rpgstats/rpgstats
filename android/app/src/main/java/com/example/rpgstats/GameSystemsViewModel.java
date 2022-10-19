package com.example.rpgstats;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.rpgstats.data.GameSystemsRepository;
import com.example.rpgstats.data.PlugGameSystemsRepository;
import com.example.rpgstats.entities.GameSystem;

import java.util.List;
import java.util.Objects;

// A ViewModel usually shouldn't reference a view, Lifecycle, or any class that may hold a reference to the activity context.
public class GameSystemsViewModel extends ViewModel {
    private MutableLiveData<List<GameSystem>> gameSystems;
    public LiveData<List<GameSystem>> getGameSystems() {
        if (gameSystems == null) {
            gameSystems = new MutableLiveData<>();
            loadGameSystems();
        }
        return gameSystems;
    }

    public void addGameSystem(GameSystem gameSystem) {
        if (gameSystems == null) {
            gameSystems = new MutableLiveData<>();
            loadGameSystems();
        }
        // todo: find better approach
        gameSystems.getValue().add(gameSystem);
        gameSystems.setValue(gameSystems.getValue());;
        Log.e("ADD GAME SYSTEM", "successfully add gs");
    }

    private void loadGameSystems() {
        // suppose getting from server in future
        GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
        gameSystems.setValue(gameSystemsRepository.getGameSystems());
    }
}
