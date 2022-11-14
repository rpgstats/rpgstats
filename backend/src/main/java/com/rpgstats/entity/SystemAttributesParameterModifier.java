package com.rpgstats.entity;

import javax.persistence.*;

@Entity
@Table(name = "system_attributes__parameter_modifiers")
public class SystemAttributesParameterModifier {
    @EmbeddedId
    private SystemAttributesParameterModifierId id;

    @MapsId("systemAttributeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system_attribute_id", nullable = false)
    private SystemAttribute systemAttribute;

    @MapsId("parameterModifierId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parameter_modifier_id", nullable = false)
    private ParameterModifier parameterModifier;

    public SystemAttributesParameterModifierId getId() {
        return id;
    }

    public void setId(SystemAttributesParameterModifierId id) {
        this.id = id;
    }

    public SystemAttribute getSystemAttribute() {
        return systemAttribute;
    }

    public void setSystemAttribute(SystemAttribute systemAttribute) {
        this.systemAttribute = systemAttribute;
    }

    public ParameterModifier getParameterModifier() {
        return parameterModifier;
    }

    public void setParameterModifier(ParameterModifier parameterModifier) {
        this.parameterModifier = parameterModifier;
    }

}