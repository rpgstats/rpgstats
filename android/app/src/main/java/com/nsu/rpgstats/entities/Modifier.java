package com.nsu.rpgstats.entities;

import java.io.Serializable;
import java.util.Objects;

public class Modifier implements Identifiable, Serializable {
    private Integer id;
    private String name;
    private Integer value;
    private Parameter parameter;

    public Modifier(Integer id, String name, Integer value, Parameter parameter) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.parameter = parameter;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modifier modifier = (Modifier) o;
        return Objects.equals(id, modifier.id) && Objects.equals(name, modifier.name) && Objects.equals(value, modifier.value);
    }

    @Override
    public String toString() {
        return name + " " + value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
