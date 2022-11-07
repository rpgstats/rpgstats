package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlugModifierRepository implements ModifierRepository{
    private final HashMap<Integer, Modifier> modifiers;
    private int currentId;

    public PlugModifierRepository() {
        modifiers = new HashMap<>();
        generateModifierList();
        currentId = 123;
    }

    private void generateModifierList() {
        for (int i = 117; i < 123; i++) {
            modifiers.put(i, new Modifier(i, "modifier " + i, "+" + i + " k Inte"));
        }
    }

    @Override
    public List<Modifier> getModifiers(int gameSystem) {
        return new ArrayList<>(modifiers.values());
    }

    @Override
    public Modifier getModifier(int gameSystemId, int id) {
        return modifiers.get(id);
    }

    @Override
    public int addModifier(int gameSystem, Modifier modifier) {
        modifiers.put(currentId, new Modifier(currentId, modifier.getName(), modifier.getValue()));
        return currentId++;
    }

    @Override
    public void editModifier(int gameSystem, int id, Modifier modifier) {
        Modifier newModifier = new Modifier(id, modifier.getName(), modifier.getValue());
        modifiers.remove(id);
        modifiers.put(id, newModifier);
    }
}
