package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Constraint;

import java.util.List;

public interface ConstraintRepository {
    List<Constraint> getConstraints(int gameSystem);

    Constraint getConstraint(int gameSystemId, int id);

    int addConstraint(int gameSystem, Constraint constraint);

    void editConstraint(int gameSystem, int id, Constraint constraint);
}
