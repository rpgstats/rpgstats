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
@Table(name = "sessions")
public class Session {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "name", nullable = false)
  @Type(type = "org.hibernate.type.TextType")
  private String name;

  @Column(name = "description")
  @Type(type = "org.hibernate.type.TextType")
  private String description;

  @NotNull
  @Column(name = "max_number_of_players", nullable = false)
  private Integer maxNumberOfPlayers;

  @NotNull
  @Column(name = "creator_as_player", nullable = false)
  private Boolean creatorAsPlayer = false;

  @NotNull
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "system_id", nullable = false)
  private GameSystem gameSystem;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "creator_id", nullable = false)
  private User creator;
}
