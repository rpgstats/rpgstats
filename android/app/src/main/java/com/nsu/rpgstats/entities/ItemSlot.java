package com.nsu.rpgstats.entities;

import java.util.List;

public class ItemSlot {
    private Item item;
    private List<Constraint> constraintList;
    private Boolean isActive;

    public ItemSlot(Item item, List<Constraint> constraintList, Boolean isActive) {
        this.item = item;
        this.constraintList = constraintList;
        this.isActive = isActive;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<Constraint> getConstraintList() {
        return constraintList;
    }

    public void setConstraintList(List<Constraint> constraintList) {
        this.constraintList = constraintList;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
