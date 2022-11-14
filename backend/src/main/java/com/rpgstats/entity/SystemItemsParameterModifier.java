package com.rpgstats.entity;

import javax.persistence.*;

@Entity
@Table(name = "system_items__parameter_modifiers")
public class SystemItemsParameterModifier {
    @EmbeddedId
    private SystemItemsParameterModifierId id;

    @MapsId("systemItemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system_item_id", nullable = false)
    private SystemItem systemItem;

    @MapsId("parameterModifierId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parameter_modifier_id", nullable = false)
    private ParameterModifier parameterModifier;

    public SystemItemsParameterModifierId getId() {
        return id;
    }

    public void setId(SystemItemsParameterModifierId id) {
        this.id = id;
    }

    public SystemItem getSystemItem() {
        return systemItem;
    }

    public void setSystemItem(SystemItem systemItem) {
        this.systemItem = systemItem;
    }

    public ParameterModifier getParameterModifier() {
        return parameterModifier;
    }

    public void setParameterModifier(ParameterModifier parameterModifier) {
        this.parameterModifier = parameterModifier;
    }

}