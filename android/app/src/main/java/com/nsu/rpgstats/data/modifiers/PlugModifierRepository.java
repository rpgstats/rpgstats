package com.nsu.rpgstats.data.modifiers;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
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
            modifiers.put(i, new Modifier(i, "Attack up " + i, i, i, "Attack"));
        }
    }

    @Override
    public void getModifiers(int gameSystem, RepositoryCallback<List<Modifier>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(modifiers.values())));
    }

    @Override
    public void getModifier(int gameSystemId, int id, RepositoryCallback<Modifier> callback) {
        callback.onComplete(new Result.Success<>(modifiers.get(id)));
    }

    @Override
    public void addModifier(int gameSystem, Modifier modifier, RepositoryCallback<Modifier> callback) {
        modifiers.put(currentId, new Modifier(currentId, modifier.getName(),
                modifier.getValue(), modifier.getParameterId(), modifier.getParameterName()));
        callback.onComplete(new Result.Success<>(modifiers.get(currentId++)));
    }

    @Override
    public void editModifier(int gameSystem, int id, Modifier modifier, RepositoryCallback<Modifier> callback) {
        Modifier newModifier = new Modifier(id, modifier.getName(),
                modifier.getValue(), modifier.getParameterId(), modifier.getParameterName());
        modifiers.remove(id);
        modifiers.put(id, newModifier);
        callback.onComplete(new Result.Success<>(modifiers.get(id)));
    }
}
