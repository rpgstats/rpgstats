package com.nsu.rpgstats.entities;

import java.util.List;

public class Session {
    private Integer id;
    private String name;
    private String author;
    private int creatorId;
    private String description;
    private int playersNumber;
    private int maxNumberOfPlayers;
    private boolean creatorAsPlayer;
    private String gameSystem;
    private int gameSystemId;
    private List<SessionCharacter> characters;

    private String createdAt;

    public Session(Integer id, String name, String creationDate) {
        this.id = id;
        this.name = name;
        this.createdAt = creationDate;
    }

    public Session(Integer id, String name, String author, String description, int playersNumber, int maximumPlayers, String gameSystem, List<SessionCharacter> characters, String creationDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.playersNumber = playersNumber;
        this.maxNumberOfPlayers = maximumPlayers;
        this.gameSystem = gameSystem;
        this.characters = characters;
        this.createdAt = creationDate;
    }

    public Session() {

    }

    public Session(int i, String sessionName, String description, int maximumPlayers, int gameSystemId) {
        id = i;
        name = sessionName;
        this.description = description;
        this.maxNumberOfPlayers = maximumPlayers;
        this.gameSystemId = gameSystemId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public String getGameSystem() {
        return gameSystem;
    }

    public List<SessionCharacter> getCharacters() {
        return characters;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public void setMaxNumberOfPlayers(int maxNumberOfPlayers) {
        this.maxNumberOfPlayers = maxNumberOfPlayers;
    }

    public void setGameSystem(String gameSystem) {
        this.gameSystem = gameSystem;
    }

    public void setCharacters(List<SessionCharacter> characters) {
        this.characters = characters;
    }

    public int getGameSystemId() {
        return gameSystemId;
    }

    public void setGameSystemId(int gameSystemId) {
        this.gameSystemId = gameSystemId;
    }
}
