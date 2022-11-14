package com.rpgstats.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
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
    @Column(name = "connection_link", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String connectionLink;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public void setMaxNumberOfPlayers(Integer maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    public Boolean getCreatorAsPlayer() {
        return creatorAsPlayer;
    }

    public void setCreatorAsPlayer(Boolean creatorAsPlayer) {
        this.creatorAsPlayer = creatorAsPlayer;
    }

    public String getConnectionLink() {
        return connectionLink;
    }

    public void setConnectionLink(String connectionLink) {
        this.connectionLink = connectionLink;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public GameSystem getSystem() {
        return gameSystem;
    }

    public void setSystem(GameSystem gameSystem) {
        this.gameSystem = gameSystem;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

}