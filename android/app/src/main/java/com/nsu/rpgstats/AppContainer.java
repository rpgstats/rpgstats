package com.nsu.rpgstats;

import com.nsu.rpgstats.data.GameSystemsRepository;
import com.nsu.rpgstats.data.ItemRepository;
import com.nsu.rpgstats.data.PlugGameSystemsRepository;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.data.PlugItemRepository;
import com.nsu.rpgstats.data.PlugTagRepository;
import com.nsu.rpgstats.data.TagRepository;

public class AppContainer {

    public GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
    public GameSystem currentGameSystem = null;
    public ItemRepository itemRepository = new PlugItemRepository();
    public TagRepository tagRepository = new PlugTagRepository();
}
