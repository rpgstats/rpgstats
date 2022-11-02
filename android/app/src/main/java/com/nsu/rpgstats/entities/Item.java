package com.nsu.rpgstats.entities;

import java.util.List;
import java.util.Objects;

public class Item {
    private final Integer id;

    private Integer pictureId;
    private String name;
    private List<String> tags;
    private List<String> modifiers;

    private boolean isDeleted;

    public Item(Integer id, Integer pictureId, String name, List<String> tags, List<String> modifiers, boolean isDeleted) {
        this.id = id;
        this.pictureId = pictureId;
        this.name = name;
        this.tags = tags;
        this.modifiers = modifiers;
        this.isDeleted = isDeleted;
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return isDeleted == item.isDeleted && Objects.equals(id, item.id) && Objects.equals(pictureId, item.pictureId) && Objects.equals(name, item.name) && Objects.equals(tags, item.tags) && Objects.equals(modifiers, item.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pictureId, name, tags, modifiers, isDeleted);
    }
}
