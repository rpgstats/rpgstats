package com.nsu.rpgstats.data.modifiers;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;

import java.util.HashMap;
import java.util.List;

public interface ModifierRepository {

    void getModifiers(int gameSystem, RepositoryCallback<List<Modifier>> callback);

    void getModifier(int gameSystemId, int id, RepositoryCallback<Modifier> callback);

    void addModifier(int gameSystem, Modifier modifier, RepositoryCallback<Modifier> callback);

    void editModifier(int gameSystem, int id, Modifier modifier, RepositoryCallback<Modifier> callback);
}
