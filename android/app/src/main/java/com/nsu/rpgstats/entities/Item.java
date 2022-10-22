package com.nsu.rpgstats.entities;

import java.util.List;
import java.util.Objects;

public class Item {
    private final Integer id;

    private Integer pictureId;
    private String name;
    private List<String> tags;
    private List<String> modifiers;

    public Item(Integer id, Integer pictureId, String name, List<String> tags, List<String> modifiers) {
        this.id = id;
        this.pictureId = pictureId;
        this.name = name;
        this.tags = tags;
        this.modifiers = modifiers;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPictureId() {
        return pictureId;
    }

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getModifiers() {
        return modifiers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id) && pictureId.equals(item.pictureId) && name.equals(item.name) && tags.equals(item.tags) && modifiers.equals(item.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pictureId, name, tags, modifiers);
    }
}
