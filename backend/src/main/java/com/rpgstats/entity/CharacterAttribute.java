package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "character__attributes")
public class CharacterAttribute {
  @EmbeddedId private CharacterAttributeId id;

  @MapsId("characterId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "character_id", nullable = false)
  private Character character;

  @MapsId("attributeId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "attribute_id", nullable = false)
  private SystemAttribute attribute;
}
