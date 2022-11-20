package com.nsu.rpgstats.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.items.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("isPresent")
    @Expose
    private Boolean isPresent;

    private Integer pictureId;
    private List<Tag> tags;
    private List<Modifier> modifiers;


    public Item(Integer id, String name, Boolean isPresent) {
        this.id = id;
        this.name = name;
        this.isPresent = isPresent;
        this.pictureId = 0;
        this.tags = new ArrayList<>();
        this.modifiers = new ArrayList<>();
    }

    public Item(Integer id, Integer pictureId, String name, List<Tag> tags, List<Modifier> modifiers, boolean isDeleted) {
        this.id = id;
        this.pictureId = pictureId;
        this.name = name;
        this.tags = tags;
        this.modifiers = modifiers;
        this.isPresent = !isDeleted;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void setModifiers(List<Modifier> modifiers) {
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

    public List<Tag> getTags() {
        return tags;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public boolean isDeleted() {
        return !isPresent;
    }

    public void setDeleted(boolean deleted) {
        isPresent = !deleted;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public void loadTagsAndModifiers(ItemRepository repository, Integer systemId) {
        repository.getItemTags(systemId, id, result -> {
            if (result instanceof Result.Success) {
                setTags(((Result.Success<List<Tag>>) result).data);
            }
            //todo handle error
        });
        repository.getItemModifiers(systemId, id, result -> {
            if (result instanceof Result.Success) {
                setModifiers(((Result.Success<List<Modifier>>) result).data);
            }
            //todo handle error
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return isPresent == item.isPresent && Objects.equals(id, item.id) && Objects.equals(pictureId, item.pictureId) && Objects.equals(name, item.name) && Objects.equals(tags, item.tags) && Objects.equals(modifiers, item.modifiers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pictureId, name, tags, modifiers, isPresent);
    }
}
