package com.nsu.rpgstats.data;

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
                    "username" + i, 666 , 1337, 228, i, ownerId));
        }
    }

    @Override
    public void getGameSystems(RepositoryCallback<List<GameSystem>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(gameSystems.values())));
    }

    @Override
    public void getGameSystem(int id, RepositoryCallback<GameSystem> callback) {
        callback.onComplete(new Result.Success<>(gameSystems.get(id)));
    }

    @Override
    public void addGameSystem(String gameSystemName, RepositoryCallback<GameSystem> callback) {
        gameSystems.put(currentId,  new GameSystem(currentId, gameSystemName,
                new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()),
                "", 1, 1, 1, 1, ownerId));
        callback.onComplete(new Result.Success<>(gameSystems.get(currentId)));
        currentId++;
    }
}
