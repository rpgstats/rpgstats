package com.nsu.rpgstats.viewmodel.sessions;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.gamesystems.GameSystemsRepository;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;

import java.util.Arrays;
import java.util.List;

public class AddSessionViewModel extends AndroidViewModel {

    private static final String TAG = AddSessionViewModel.class.getSimpleName();

    private SessionsRepository repository;
    private GameSystemsRepository gameSystemsRepository;
    public MutableLiveData<List<GameSystem>> gameSystems;

    public AddSessionViewModel(@NonNull Application application) {
        super(application);
        repository = ((RpgstatsApplication)getApplication()).appContainer.sessionsRepository;
        gameSystemsRepository = ((RpgstatsApplication)getApplication()).appContainer.gameSystemsRepository;

        gameSystems = new MutableLiveData<>();
    }

    public void onAddSessionButtonClick(String sessionName, String maximumPlayers) {
        repository.addSession(sessionName,
                Integer.parseInt(maximumPlayers),
                0,
                (res)-> {

                });
    }

    public void loadGameSystems() {
        gameSystemsRepository.getGameSystems((result) -> {
            if (result instanceof Result.Success) {
                List<GameSystem> gs = ((Result.Success<List<GameSystem>>) result).data;
                Log.d(TAG, "get game systems: " + Arrays.toString(gs.toArray()));
                gameSystems.setValue(gs);
            } else if (result instanceof Result.Error) {
                Log.e(TAG, "can not load game systems, reason: " +
                        ((Result.Error<List<GameSystem>>) result).throwable.getMessage());
            }
        });
    }
}
