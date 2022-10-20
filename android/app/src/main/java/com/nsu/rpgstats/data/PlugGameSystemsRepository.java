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
                    "username" + i, 666 , 1337, 228, i));
        }
    }

    @Override
    public List<GameSystem> getGameSystems() {
        return  new ArrayList<>(gameSystems.values());
    }

    @Override
    public GameSystem getGameSystem(int id) {
        return gameSystems.get(id);
    }

    @Override
    public int addGameSystem(String gameSystemName) {
        gameSystems.put(currentId,  new GameSystem(currentId, gameSystemName,
                new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()),
                "", 1, 1, 1, 1));
        return currentId++;
    }
}
