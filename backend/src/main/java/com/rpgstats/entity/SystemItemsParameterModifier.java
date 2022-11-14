package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "system_items__parameter_modifiers")
@Getter
@Setter
public class SystemItemsParameterModifier {
  @EmbeddedId private SystemItemsParameterModifierId id;

  @MapsId("systemItemId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "system_item_id", nullable = false)
  private SystemItem systemItem;

  @MapsId("parameterModifierId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parameter_modifier_id", nullable = false)
  private ParameterModifier parameterModifier;
}
