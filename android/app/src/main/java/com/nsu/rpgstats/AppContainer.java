package com.nsu.rpgstats;

import com.nsu.rpgstats.data.ConstraintRepository;
import com.nsu.rpgstats.data.GameSystemsRepository;
import com.nsu.rpgstats.data.ItemRepository;
import com.nsu.rpgstats.data.ModifierRepository;
import com.nsu.rpgstats.data.ParameterRepository;
import com.nsu.rpgstats.data.PlugConstraintRepository;
import com.nsu.rpgstats.data.PlugGameSystemsRepository;
import com.nsu.rpgstats.data.PlugModifierRepository;
import com.nsu.rpgstats.data.PlugParameterRepository;
import com.nsu.rpgstats.data.PlugPropertyRepository;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.data.RestGameSystemsRepository;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.data.PlugItemRepository;
import com.nsu.rpgstats.data.PlugTagRepository;
import com.nsu.rpgstats.data.TagRepository;

public class AppContainer {
    public GameSystemsRepository gameSystemsRepository = new RestGameSystemsRepository();
    public GameSystem currentGameSystem = null;
    public ItemRepository itemRepository = new PlugItemRepository();
    public TagRepository tagRepository = new PlugTagRepository();
    public ModifierRepository modifierRepository = new PlugModifierRepository();
    public ParameterRepository parameterRepository = new PlugParameterRepository();
    public PropertyRepository propertyRepository = new PlugPropertyRepository();
    public ConstraintRepository constraintRepository = new PlugConstraintRepository();
}
