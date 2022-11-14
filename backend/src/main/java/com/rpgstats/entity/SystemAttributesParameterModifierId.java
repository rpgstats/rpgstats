package com.rpgstats.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SystemAttributesParameterModifierId implements Serializable {
    private static final long serialVersionUID = 7247693079001547916L;
    @NotNull
    @Column(name = "system_attribute_id", nullable = false)
    private Integer systemAttributeId;

    @NotNull
    @Column(name = "parameter_modifier_id", nullable = false)
    private Integer parameterModifierId;

    public Integer getSystemAttributeId() {
        return systemAttributeId;
    }

    public void setSystemAttributeId(Integer systemAttributeId) {
        this.systemAttributeId = systemAttributeId;
    }

    public Integer getParameterModifierId() {
        return parameterModifierId;
    }

    public void setParameterModifierId(Integer parameterModifierId) {
        this.parameterModifierId = parameterModifierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SystemAttributesParameterModifierId entity = (SystemAttributesParameterModifierId) o;
        return Objects.equals(this.parameterModifierId, entity.parameterModifierId) &&
                Objects.equals(this.systemAttributeId, entity.systemAttributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parameterModifierId, systemAttributeId);
    }

}