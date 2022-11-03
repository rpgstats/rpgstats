package com.nsu.rpgstats;

import com.nsu.rpgstats.data.GameSystemsRepository;
import com.nsu.rpgstats.data.PlugGameSystemsRepository;
import com.nsu.rpgstats.entities.GameSystem;

public class AppContainer {

    public GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
    public GameSystem currentGameSystem = null;
}
