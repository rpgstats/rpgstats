package com.nsu.rpgstats.entities;

import java.util.ArrayList;
import java.util.List;

public class Property implements Identifiable {
    private Integer id;
    private String name;
    private Boolean isDeleted;
    private List<Modifier> modifiers;
    private List<Constraint> constraints;

    public Property() {
    }

    public Property(Property p) {
        id = p.id;
        name = p.name;
        isDeleted = p.isDeleted;
        modifiers = new ArrayList<>();
        modifiers.addAll(p.modifiers);
        constraints = new ArrayList<>();
        constraints.addAll(p.constraints);
    }

    public Property(Integer id, String name, Boolean isDeleted, List<Modifier> modifiers, List<Constraint> constraints) {
        this.id = id;
        this.name = name;
        this.isDeleted = isDeleted;
        this.modifiers = modifiers;
        this.constraints = constraints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Modifier> modifiers) {
        this.modifiers = modifiers;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Constraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
