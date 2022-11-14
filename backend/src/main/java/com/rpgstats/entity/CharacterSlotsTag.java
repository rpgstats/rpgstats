package com.rpgstats.entity;

import javax.persistence.*;

@Entity
@Table(name = "character_slots__tags")
public class CharacterSlotsTag {
    @EmbeddedId
    private CharacterSlotsTagId id;

    @MapsId("slotId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "slot_id", nullable = false)
    private CharacterSlot slot;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private SystemTag tag;

    public CharacterSlotsTagId getId() {
        return id;
    }

    public void setId(CharacterSlotsTagId id) {
        this.id = id;
    }

    public CharacterSlot getSlot() {
        return slot;
    }

    public void setSlot(CharacterSlot slot) {
        this.slot = slot;
    }

    public SystemTag getTag() {
        return tag;
    }

    public void setTag(SystemTag tag) {
        this.tag = tag;
    }

}