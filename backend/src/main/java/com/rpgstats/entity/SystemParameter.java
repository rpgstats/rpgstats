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
@Table(name = "system_parameters")
public class SystemParameter {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @NotNull
  @Column(name = "name", nullable = false)
  @Type(type = "org.hibernate.type.TextType")
  private String name;

  @NotNull
  @Column(name = "min_value", nullable = false)
  private Double minValue;

  @NotNull
  @Column(name = "max_value", nullable = false)
  private Double maxValue;

  @NotNull
  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "system_id", nullable = false)
  private GameSystem gameSystem;
}
