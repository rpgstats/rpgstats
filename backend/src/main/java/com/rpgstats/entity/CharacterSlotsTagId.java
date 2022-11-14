package com.rpgstats.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CharacterSlotsTagId implements Serializable {
    private static final long serialVersionUID = -135833032157774557L;
    @NotNull
    @Column(name = "slot_id", nullable = false)
    private Integer slotId;

    @NotNull
    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CharacterSlotsTagId entity = (CharacterSlotsTagId) o;
        return Objects.equals(this.tagId, entity.tagId) &&
                Objects.equals(this.slotId, entity.slotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagId, slotId);
    }

}