package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.gamesystems.GameSystemsRepository;
import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.ui.gamesystems.CreationGameResult;

import java.util.ArrayList;
import java.util.List;

// A ViewModel usually shouldn't reference a view, Lifecycle, or any class that may hold a reference to the activity context.
public class GameSystemsViewModel extends AndroidViewModel {
    private MutableLiveData<List<GameSystem>> gameSystems;
    private MutableLiveData<CreationGameResult> creationGameResult;
    private final GameSystemsRepository gameSystemsRepository;

    public GameSystemsViewModel(@NonNull Application application) {
        super(application);
        gameSystemsRepository = ((RpgstatsApplication)getApplication()).appContainer.gameSystemsRepository;
        creationGameResult = new MutableLiveData<>(new CreationGameResult(""));
    }


    public LiveData<List<GameSystem>> getGameSystems() {
        if (gameSystems == null) {
            gameSystems = new MutableLiveData<>();
            loadGameSystems();
        }
        return gameSystems;
    }

    public void addGameSystem(String gameSystemName, String description) {
        if ("".equals(gameSystemName)) {
            onGameCreationText("Can not create game system, error: Empty name");
            return;
        }

        if (gameSystems == null) {
            gameSystems = new MutableLiveData<>();
            loadGameSystems();
        }
        gameSystemsRepository.addGameSystem(gameSystemName, description, new RepositoryCallback<GameSystem>() {
            @Override
            public void onComplete(Result<GameSystem> result) {
                if (result instanceof  Result.Success) {
                    gameSystems.getValue().add(((Result.Success<GameSystem>) result).data);
                    gameSystems.setValue(gameSystems.getValue());
                    //gameSystems.setValue(((Result.Success<List<GameSystem>>) result).data);
                    Log.e("ADD GAME SYSTEM", "successfully add gs");
                    onGameCreationText("Successfully add game system");
                } else if (result instanceof Result.Error) {
                    String err = ((Result.Error<GameSystem>) result).throwable.getMessage();
                    Log.e("CAN NOT ADD GS", err);
                    onGameCreationText("Can not create game system, error: " + err);
                }
            }
        });
    }

    private void onGameCreationText(String reason) {
        if (creationGameResult == null) {
            creationGameResult = new MutableLiveData<>();
        }
        creationGameResult.setValue(new CreationGameResult(reason));
    }

    private void loadGameSystems() {
        gameSystems.setValue(new ArrayList<>());
        // suppose getting from server in future
        gameSystemsRepository.getGameSystems(new RepositoryCallback<List<GameSystem>>() {

            @Override
            public void onComplete(Result<List<GameSystem>> result) {
                if (result instanceof  Result.Success) {
                    gameSystems.setValue(((Result.Success<List<GameSystem>>) result).data);
                }
            }
        });
    }

    public MutableLiveData<CreationGameResult> getCreationResult() {
        return creationGameResult;
    }
}
