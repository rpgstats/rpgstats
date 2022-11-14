package com.rpgstats.entity;

import javax.persistence.*;

@Entity
@Table(name = "character__attributes")
public class CharacterAttribute {
    @EmbeddedId
    private CharacterAttributeId id;

    @MapsId("characterId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "character_id", nullable = false)
    private Character character;

    @MapsId("attributeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
    private SystemAttribute attribute;

    public CharacterAttributeId getId() {
        return id;
    }

    public void setId(CharacterAttributeId id) {
        this.id = id;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public SystemAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(SystemAttribute attribute) {
        this.attribute = attribute;
    }

}