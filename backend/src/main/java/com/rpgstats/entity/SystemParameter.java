package com.rpgstats.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMinValue() {
        return minValue;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public GameSystem getGameSystem() {
        return gameSystem;
    }

    public void setGameSystem(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

}