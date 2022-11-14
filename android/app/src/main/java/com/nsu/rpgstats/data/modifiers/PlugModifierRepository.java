package com.nsu.rpgstats.data.modifiers;

import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;

import java.util.ArrayList;
import java.util.Date;
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
            modifiers.put(i, new Modifier(i, "Attack up " + i, i,
                    new Parameter(i, "Attack", new Date(), 0, 993)));
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
        modifiers.put(currentId, new Modifier(currentId, modifier.getName(),
                modifier.getValue(), modifier.getParameter()));
        return currentId++;
    }

    @Override
    public int editModifier(int gameSystem, int id, Modifier modifier) {
        Modifier newModifier = new Modifier(id, modifier.getName(),
                modifier.getValue(), modifier.getParameter());
        modifiers.remove(id);
        modifiers.put(id, newModifier);
        return id;
    }
}
