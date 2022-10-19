package com.example.rpgstats;

import com.example.rpgstats.data.GameSystemsRepository;
import com.example.rpgstats.data.PlugGameSystemsRepository;

public class AppContainer {

    public GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
}
