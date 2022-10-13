package com.example.rpgstats.data;

import com.example.rpgstats.gamesystems.GameSystem;

import java.util.ArrayList;
import java.util.List;

public class PlugGameSystemsRepository implements GameSystemsRepository{
    @Override
    public List<GameSystem> getGameSystems() {
        List<GameSystem> gameSystems = new ArrayList<>();
        for (int i = 35000; i < 40000; i++) {
            gameSystems.add(new GameSystem(i, "warhammer " + i, "1.1.2001"));
        }
        return gameSystems;
    }
}
