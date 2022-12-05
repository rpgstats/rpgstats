package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class CharacterSlotsTagId implements Serializable {
  private static final long serialVersionUID = -135833032157774557L;

  @NotNull
  @Column(name = "slot_id", nullable = false)
  private Integer slotId;

  @NotNull
  @Column(name = "tag_id", nullable = false)
  private Integer tagId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    CharacterSlotsTagId entity = (CharacterSlotsTagId) o;
    return Objects.equals(this.tagId, entity.tagId) && Objects.equals(this.slotId, entity.slotId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagId, slotId);
  }
}
