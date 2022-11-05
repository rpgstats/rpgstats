package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;

import java.util.HashMap;
import java.util.List;

public interface ModifierRepository {

    List<Modifier> getModifiers(int gameSystem);

    Modifier getModifier(int gameSystemId, int id);

    int addModifier(int gameSystem, Modifier modifier);

    void editModifier(int gameSystem, int id, Modifier modifier);
}
