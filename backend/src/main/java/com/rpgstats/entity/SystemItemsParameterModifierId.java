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
public class SystemItemsParameterModifierId implements Serializable {
  private static final long serialVersionUID = -3480611074432024847L;

  @NotNull
  @Column(name = "system_item_id", nullable = false)
  private Integer systemItemId;

  @NotNull
  @Column(name = "parameter_modifier_id", nullable = false)
  private Integer parameterModifierId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    SystemItemsParameterModifierId entity = (SystemItemsParameterModifierId) o;
    return Objects.equals(this.systemItemId, entity.systemItemId)
        && Objects.equals(this.parameterModifierId, entity.parameterModifierId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(systemItemId, parameterModifierId);
  }
}
