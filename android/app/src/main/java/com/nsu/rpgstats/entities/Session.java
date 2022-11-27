package com.nsu.rpgstats.entities;

import java.util.List;

public class Session {
    private Integer id;
    private String name;
    private String author;
    private String description;
    private int playersNumber;
    private int maximumPlayers;
    private String gameSystem;
    private List<Character> characters;

    private String creationDate;

    public Session(Integer id, String name, String creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
    }

    public Session(Integer id, String name, String author, String description, int playersNumber, int maximumPlayers, String gameSystem, List<Character> characters, String creationDate) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.description = description;
        this.playersNumber = playersNumber;
        this.maximumPlayers = maximumPlayers;
        this.gameSystem = gameSystem;
        this.characters = characters;
        this.creationDate = creationDate;
    }

    public Session() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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

    public int getMaximumPlayers() {
        return maximumPlayers;
    }

    public String getGameSystem() {
        return gameSystem;
    }

    public List<Character> getCharacters() {
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

    public void setMaximumPlayers(int maximumPlayers) {
        this.maximumPlayers = maximumPlayers;
    }

    public void setGameSystem(String gameSystem) {
        this.gameSystem = gameSystem;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
