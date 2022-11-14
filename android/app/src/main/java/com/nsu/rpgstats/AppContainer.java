package com.nsu.rpgstats;

import com.nsu.rpgstats.data.constraints.ConstraintRepository;
import com.nsu.rpgstats.data.gamesystems.GameSystemsRepository;
import com.nsu.rpgstats.data.items.ItemRepository;
import com.nsu.rpgstats.data.modifiers.ModifierRepository;
import com.nsu.rpgstats.data.parameters.ParameterRepository;
import com.nsu.rpgstats.data.constraints.PlugConstraintRepository;
import com.nsu.rpgstats.data.modifiers.PlugModifierRepository;
import com.nsu.rpgstats.data.parameters.PlugParameterRepository;
import com.nsu.rpgstats.data.properties.PlugPropertyRepository;
import com.nsu.rpgstats.data.properties.PropertyRepository;
import com.nsu.rpgstats.data.gamesystems.RestGameSystemsRepository;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.data.items.PlugItemRepository;
import com.nsu.rpgstats.data.tags.PlugTagRepository;
import com.nsu.rpgstats.data.tags.TagRepository;

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
