package com.nsu.rpgstats.entities;

import java.util.Objects;

public class Tag implements Identifiable{
    private final Integer id;
    private String name;
    private String creationDate;
    private boolean isDeleted;

    public Tag(Integer id, String name, String creationDate, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.isDeleted = isDeleted;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
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
        Tag tag = (Tag) o;
        return isDeleted == tag.isDeleted && Objects.equals(id, tag.id) && Objects.equals(name, tag.name) && Objects.equals(creationDate, tag.creationDate);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, isDeleted);
    }
}
