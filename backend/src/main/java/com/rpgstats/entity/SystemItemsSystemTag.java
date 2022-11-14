package com.rpgstats.entity;

import javax.persistence.*;

@Entity
@Table(name = "system_items__system_tags")
public class SystemItemsSystemTag {
    @EmbeddedId
    private SystemItemsSystemTagId id;

    @MapsId("systemItemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system_item_id", nullable = false)
    private SystemItem systemItem;

    @MapsId("systemTagId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "system_tag_id", nullable = false)
    private SystemTag systemTag;

    public SystemItemsSystemTagId getId() {
        return id;
    }

    public void setId(SystemItemsSystemTagId id) {
        this.id = id;
    }

    public SystemItem getSystemItem() {
        return systemItem;
    }

    public void setSystemItem(SystemItem systemItem) {
        this.systemItem = systemItem;
    }

    public SystemTag getSystemTag() {
        return systemTag;
    }

    public void setSystemTag(SystemTag systemTag) {
        this.systemTag = systemTag;
    }

}