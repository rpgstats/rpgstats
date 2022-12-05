package com.nsu.rpgstats.data.constraints;

import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlugConstraintRepository implements ConstraintRepository{
    private final HashMap<Integer, Constraint> constraints;
    private Integer currentId;

    public PlugConstraintRepository() {
        constraints = new HashMap<>();
        generateConstraintList();
        currentId = 123;
    }

    private void generateConstraintList() {
        for (int i = 117; i < 123; ++i) {
            List<Tag> tags = new ArrayList<>();
            tags.add(new Tag(0, "tag 1" + i, "Date", false));
            tags.add(new Tag(1, "tag 2" + i, "Date", false));
            tags.add(new Tag(2, "tag 3" + i, "Date", false));
            tags.add(new Tag(3, "tag 4" + i, "Date", false));
            constraints.put(i, new Constraint(i, "Constraint " + i, (i%2==0), tags));
        }
    }

    @Override
    public List<Constraint> getConstraints(int gameSystem) {
        return new ArrayList<>(constraints.values());
    }

    @Override
    public Constraint getConstraint(int gameSystemId, int id) {
        return constraints.get(id);
    }

    @Override
    public int addConstraint(int gameSystem, Constraint constraint) {
        Constraint c = new Constraint(constraint.getId(), constraint.getName(), constraint.isBlackList(), constraint.getTags());
        constraints.put(currentId, c);
        return currentId++;
    }

    @Override
    public void editConstraint(int gameSystem, int id, Constraint constraint) {
        Constraint c = new Constraint(constraint.getId(), constraint.getName(), constraint.isBlackList(), constraint.getTags());
        constraints.remove(id);
        constraints.put(id, c);
    }
}
