package com.example.rpgstats.entities;


public class GameSystem {
    private final Integer id;
    private final String systemName;
    private final String creationDate;

    private String owner;
    private int gameSessionNumber;
    private int childGameSystemNumber;
    private int itemsNumber;
    private int npcNumber;

    public GameSystem(Integer id, String systemName, String creationDate, String owner,
                      int gameSessionNumber, int childGameSystemNumber, int itemsNumber, int npcNumber) {
        this.id = id;
        this.creationDate = creationDate;
        this.systemName = systemName;
        this.owner = owner;
        this.gameSessionNumber = gameSessionNumber;
        this.childGameSystemNumber = childGameSystemNumber;
        this.itemsNumber = itemsNumber;
        this.npcNumber = npcNumber;
    }

    public GameSystem(int id, String gameSystemName, String creationDate) {
        this.id = id;
        this.creationDate = creationDate;
        this.systemName = gameSystemName;
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

    public int getNpcNumber() {
        return npcNumber;
    }

    public int getItemsNumber() {
        return itemsNumber;
    }

    public int getChildGameSystemNumber() {
        return childGameSystemNumber;
    }

    public int getGameSessionNumber() {
        return gameSessionNumber;
    }

    public String getOwner() {
        return owner;
    }
}
