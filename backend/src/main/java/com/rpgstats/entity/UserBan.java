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
@Table(name = "user_bans")
public class UserBan {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "admin_id", nullable = false)
  private Admin admin;

  @NotNull
  @Column(name = "blocked_at", nullable = false)
  private Instant blockedAt;

  @Column(name = "unblocked_at")
  private Instant unblockedAt;

  @NotNull
  @Column(name = "reason", nullable = false)
  @Type(type = "org.hibernate.type.TextType")
  private String reason;
}
