package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "system_attributes__parameter_modifiers")
@Getter
@Setter
public class SystemAttributesParameterModifier {
  @EmbeddedId private SystemAttributesParameterModifierId id;

  @MapsId("systemAttributeId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "system_attribute_id", nullable = false)
  private SystemAttribute systemAttribute;

  @MapsId("parameterModifierId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parameter_modifier_id", nullable = false)
  private ParameterModifier parameterModifier;
}
