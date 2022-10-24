package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "system_items__system_tags")
@Getter
@Setter
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

}