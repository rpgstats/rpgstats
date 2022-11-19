package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "systems")
public class GameSystem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "name", nullable = false, unique = true)
  @Type(type = "org.hibernate.type.TextType")
  private String name;

  @Column(name = "description")
  @Type(type = "org.hibernate.type.TextType")
  private String description;

  @NotNull
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "owner_id", nullable = false)
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_system_id")
  private GameSystem parentGameSystem;
}
