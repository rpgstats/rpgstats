package com.nsu.rpgstats.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Attribute implements Serializable {
    private Parameter parameter;
    private List<Modifier> modifierList = new ArrayList<>();
    private int id;
    private String name;
    private boolean isPresent;
    private int SystemId;

    public Attribute(int id, String name, boolean isPresent, int systemId, Parameter parameter) {
        this.id = id;
        this.name = name;
        this.isPresent = isPresent;
        this.parameter = parameter;
        SystemId = systemId;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public List<Modifier> getModifierList() {
        return modifierList;
    }

    public void setModifierList(List<Modifier> modifierList) {
        this.modifierList = modifierList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    public int getSystemId() {
        return SystemId;
    }

    public void setSystemId(int systemId) {
        SystemId = systemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        return id == attribute.id && isPresent == attribute.isPresent && SystemId == attribute.SystemId && Objects.equals(parameter, attribute.parameter) && Objects.equals(modifierList, attribute.modifierList) && Objects.equals(name, attribute.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameter, modifierList, id, name, isPresent, SystemId);
    }
}
