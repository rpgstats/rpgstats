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
public class CharacterAttributeId implements Serializable {
  private static final long serialVersionUID = 2942640169516869614L;

  @NotNull
  @Column(name = "character_id", nullable = false)
  private Integer characterId;

  @NotNull
  @Column(name = "attribute_id", nullable = false)
  private Integer attributeId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    CharacterAttributeId entity = (CharacterAttributeId) o;
    return Objects.equals(this.attributeId, entity.attributeId)
        && Objects.equals(this.characterId, entity.characterId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(attributeId, characterId);
  }
}
