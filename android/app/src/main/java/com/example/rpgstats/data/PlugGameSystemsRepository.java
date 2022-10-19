package com.example.rpgstats.data;

import com.example.rpgstats.entities.GameSystem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlugGameSystemsRepository implements GameSystemsRepository{

    private final HashMap<Integer, GameSystem> gameSystems;

    public PlugGameSystemsRepository() {
        gameSystems = new HashMap<>();
        generateGameSystemsList();
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
}
