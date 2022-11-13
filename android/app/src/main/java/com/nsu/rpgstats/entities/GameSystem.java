package com.nsu.rpgstats.entities;


import java.util.Objects;

public class GameSystem {
    private Integer id;
    private String systemName;
    private String creationDate;
    private String owner;
    private int gameSessionNumber;
    private int childGameSystemNumber;
    private int itemsNumber;
    private int npcNumber;
    private int ownerId;

    public GameSystem(String systemName) {
        this.systemName = systemName;
    }

    public GameSystem(Integer id, String systemName, String creationDate, String owner,
                      int gameSessionNumber, int childGameSystemNumber, int itemsNumber, int npcNumber, int ownerId) {
        this.id = id;
        this.creationDate = creationDate;
        this.systemName = systemName;
        this.owner = owner;
        this.gameSessionNumber = gameSessionNumber;
        this.childGameSystemNumber = childGameSystemNumber;
        this.itemsNumber = itemsNumber;
        this.npcNumber = npcNumber;
        this.ownerId = ownerId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSystem that = (GameSystem) o;
        return gameSessionNumber == that.gameSessionNumber &&
                childGameSystemNumber == that.childGameSystemNumber &&
                itemsNumber == that.itemsNumber && npcNumber == that.npcNumber &&
                Objects.equals(id, that.id) && Objects.equals(systemName, that.systemName) &&
                Objects.equals(creationDate, that.creationDate) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, systemName, creationDate, owner, gameSessionNumber, childGameSystemNumber, itemsNumber, npcNumber);
    }
}
