package com.rpgstats.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "attribute_constraints")
public class AttributeConstraint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "has_tag", nullable = false)
    private Boolean hasTag = false;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tag_id", nullable = false)
    private SystemTag tag;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attribute_id", nullable = false)
    private SystemAttribute attribute;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getHasTag() {
        return hasTag;
    }

    public void setHasTag(Boolean hasTag) {
        this.hasTag = hasTag;
    }

    public SystemTag getTag() {
        return tag;
    }

    public void setTag(SystemTag tag) {
        this.tag = tag;
    }

    public SystemAttribute getAttribute() {
        return attribute;
    }

    public void setAttribute(SystemAttribute attribute) {
        this.attribute = attribute;
    }

}