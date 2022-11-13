package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;

public interface GameSystemsRepository {

    void getGameSystems(RepositoryCallback<List<GameSystem>> callback);

    void getGameSystem(int id, RepositoryCallback<GameSystem> callback);

    void addGameSystem(String gameSystemName, RepositoryCallback<GameSystem> callback);
}
