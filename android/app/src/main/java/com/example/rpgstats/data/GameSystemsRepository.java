package com.example.rpgstats.data;

import com.example.rpgstats.entities.GameSystem;

import java.util.List;

public interface GameSystemsRepository {

    List<GameSystem> getGameSystems();

    GameSystem getGameSystem(int id);
}
