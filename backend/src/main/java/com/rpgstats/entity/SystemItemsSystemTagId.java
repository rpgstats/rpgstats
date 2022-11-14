package com.rpgstats.entity;

import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SystemItemsSystemTagId implements Serializable {
    private static final long serialVersionUID = 158407498404017623L;
    @NotNull
    @Column(name = "system_item_id", nullable = false)
    private Integer systemItemId;

    @NotNull
    @Column(name = "system_tag_id", nullable = false)
    private Integer systemTagId;

    public Integer getSystemItemId() {
        return systemItemId;
    }

    public void setSystemItemId(Integer systemItemId) {
        this.systemItemId = systemItemId;
    }

    public Integer getSystemTagId() {
        return systemTagId;
    }

    public void setSystemTagId(Integer systemTagId) {
        this.systemTagId = systemTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SystemItemsSystemTagId entity = (SystemItemsSystemTagId) o;
        return Objects.equals(this.systemItemId, entity.systemItemId) &&
                Objects.equals(this.systemTagId, entity.systemTagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemItemId, systemTagId);
    }

}