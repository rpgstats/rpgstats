package com.nsu.rpgstats.data.gamesystems;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;

public interface GameSystemsRepository {

    void getGameSystems(int ownerId, RepositoryCallback<List<GameSystem>> callback);

    void getGameSystem(int id, RepositoryCallback<GameSystem> callback);

    void addGameSystem(String gameSystemName, String description, RepositoryCallback<GameSystem> callback);
}
