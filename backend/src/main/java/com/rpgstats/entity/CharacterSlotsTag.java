package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "character_slots__tags")
public class CharacterSlotsTag {
  @EmbeddedId private CharacterSlotsTagId id;

  @MapsId("slotId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "slot_id", nullable = false)
  private CharacterSlot slot;

  @MapsId("tagId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tag_id", nullable = false)
  private SystemTag tag;
}
