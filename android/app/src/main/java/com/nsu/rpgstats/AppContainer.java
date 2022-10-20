package com.nsu.rpgstats;

import com.nsu.rpgstats.data.GameSystemsRepository;
import com.nsu.rpgstats.data.PlugGameSystemsRepository;

public class AppContainer {

    public GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
}
