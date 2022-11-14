package com.rpgstats.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sessions")
@Getter
@Setter
public class Session {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  @Type(type = "org.hibernate.type.TextType")
  private String name;

  @Column(name = "description")
  @Type(type = "org.hibernate.type.TextType")
  private String description;

  @Column(name = "max_number_of_players", nullable = false)
  private Integer maxNumberOfPlayers;

  @Column(name = "creator_as_player", nullable = false)
  private Boolean creatorAsPlayer = false;

  @Column(name = "connection_link")
  @Type(type = "org.hibernate.type.TextType")
  private String connectionLink;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive = false;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "system_id", nullable = false)
  private GameSystem gameSystem;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "creator_id", nullable = false)
  private User creatorId;
}
