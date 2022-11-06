package com.nsu.rpgstats.entities;

import com.nsu.rpgstats.network.DTO.ModifierDTO;

import java.util.Objects;

public class Modifier implements Identifiable{
    private Integer id;
    private String name;
    private String value;

    public Modifier(Integer id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public  Modifier(ModifierDTO modifierDTO) {
        this.id = modifierDTO.getId();
        this.name = modifierDTO.getName();
        this.value = modifierDTO.getValue().toString();//Todo change
        //todo parameters
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
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
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
