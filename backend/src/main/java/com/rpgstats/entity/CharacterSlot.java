package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "character_slots")
public class CharacterSlot {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "name", nullable = false)
  @Type(type = "org.hibernate.type.TextType")
  private String name;

  @Column(name = "icon_url")
  @Type(type = "org.hibernate.type.TextType")
  private String iconUrl;

  @NotNull
  @Column(name = "is_whitelisted", nullable = false)
  private Boolean isWhitelisted = false;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "character_id", nullable = false)
  private Character character;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private SystemItem item;
}
