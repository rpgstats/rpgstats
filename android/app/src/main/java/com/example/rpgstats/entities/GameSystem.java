package com.example.rpgstats.entities;


public class GameSystem {
    private final Integer id;
    private final String systemName;
    private final String creationDate;

    public GameSystem(Integer id, String systemName, String creationDate) {
        this.id = id;
        this.creationDate = creationDate;
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Integer getId() {
        return id;
    }
}
