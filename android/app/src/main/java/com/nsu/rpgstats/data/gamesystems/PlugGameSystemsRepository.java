package com.nsu.rpgstats.data.gamesystems;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.GameSystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlugGameSystemsRepository implements GameSystemsRepository{

    private final HashMap<Integer, GameSystem> gameSystems;
    private int currentId;

    public PlugGameSystemsRepository() {
        gameSystems = new HashMap<>();
        generateGameSystemsList();
        currentId = 35006;
    }

    private void generateGameSystemsList() {
        for (int i = 35000; i < 35005; i++) {
            gameSystems.put(i, new GameSystem(i, "warhammer " + i,
                    new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()),
                    "username" + i, "description", 666 , 1337, 228, i));
        }
    }

    @Override
    public void getGameSystems(int ownerId, RepositoryCallback<List<GameSystem>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(gameSystems.values())));
    }

    @Override
    public void getGameSystem(int id, RepositoryCallback<GameSystem> callback) {
        callback.onComplete(new Result.Success<>(gameSystems.get(id)));
    }

    @Override
    public void addGameSystem(String gameSystemName, String description, RepositoryCallback<GameSystem> callback) {
        gameSystems.put(currentId,  new GameSystem(currentId, gameSystemName,
                new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()),
                "", "description", 1, 1, 1, 1));
        callback.onComplete(new Result.Success<>(gameSystems.get(currentId)));
        currentId++;
    }
}
