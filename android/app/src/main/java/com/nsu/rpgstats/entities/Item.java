package com.nsu.rpgstats.entities;

import com.nsu.rpgstats.data.RestItemRepository;
import com.nsu.rpgstats.network.DTO.ItemDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Item {
    private final Integer id;

    private Integer pictureId;
    private String name;
    private List<Tag> tags;
    private List<Modifier> modifiers;

    private boolean isDeleted;

    public Item(Integer id, Integer pictureId, String name, List<Tag> tags, List<Modifier> modifiers, boolean isDeleted) {
        this.id = id;
        this.pictureId = pictureId;
        this.name = name;
        this.tags = tags;
        this.modifiers = modifiers;
        this.isDeleted = isDeleted;
    }

    public Item(ItemDTO itemDTO) {
        this.id = itemDTO.getId();
        this.name = itemDTO.getName();
        this.isDeleted = !itemDTO.getIsPresent();
        RestItemRepository repository = new RestItemRepository();
        this.tags = repository.getItemTags(itemDTO.getSystemId(), id);
        this.modifiers = repository.getItemModifiers(itemDTO.getSystemId(), id);
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
