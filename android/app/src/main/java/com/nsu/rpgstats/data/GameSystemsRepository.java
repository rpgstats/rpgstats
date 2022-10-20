package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;

public interface GameSystemsRepository {

    List<GameSystem> getGameSystems();

    GameSystem getGameSystem(int id);

    int addGameSystem(String gameSystemName);
}
