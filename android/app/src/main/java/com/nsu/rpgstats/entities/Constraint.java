package com.nsu.rpgstats.entities;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class Constraint implements Identifiable {
    private Integer id;
    private String name;
    private Boolean isBlackList;
    private List<Tag> tags;

    public Constraint(Integer id, String name, Boolean isBlackList, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.isBlackList = isBlackList;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBlackList() {
        return isBlackList;
    }

    public void setBlackList(Boolean blackList) {
        isBlackList = blackList;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constraint that = (Constraint) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(isBlackList, that.isBlackList) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isBlackList, tags);
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
