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
        currentId = 306;
    }

    private void generateModifierList() {
        modifiers.put(300, new Modifier(300, "Attack up", 10, new Parameter(117, "Attack", new Date(), 0, 993)));
        modifiers.put(301, new Modifier(301, "Defense up", 10, new Parameter(120, "Defense", new Date(), 3, 996)));
        modifiers.put(302, new Modifier(302, "Defense up", 50, new Parameter(120, "Defense", new Date(), 3, 996)));
        modifiers.put(303, new Modifier(303, "Health points up", 50, new Parameter(118, "Health points", new Date(), 1, 994)));
        modifiers.put(304, new Modifier(304, "Attack up", 5, new Parameter(117, "Attack", new Date(), 0, 993)));
        modifiers.put(305, new Modifier(305, "Attack up", 15, new Parameter(117, "Attack", new Date(), 0, 993)));
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
    public void editModifier(int gameSystem, int id, Modifier modifier) {
        Modifier newModifier = new Modifier(id, modifier.getName(),
                modifier.getValue(), modifier.getParameter());
        modifiers.remove(id);
        modifiers.put(id, newModifier);
    }
}
