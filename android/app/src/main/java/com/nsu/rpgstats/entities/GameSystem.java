package com.nsu.rpgstats.entities;


import java.util.Objects;

public class GameSystem {
    private Integer id;
    private String name;
    private String creationDate;
    private String owner;
    private String description;
    private int gameSessionNumber;
    private int childGameSystemNumber;
    private int itemsNumber;
    private int npcNumber;

    public GameSystem(String systemName, String description) {
        this.name = systemName;
        this.description = description;
    }

    public GameSystem(Integer id, String systemName, String creationDate, String owner,
                      String description, int gameSessionNumber, int childGameSystemNumber, int itemsNumber, int npcNumber) {
        this.id = id;
        this.creationDate = creationDate;
        this.name = systemName;
        this.owner = owner;
        this.description = description;
        this.gameSessionNumber = gameSessionNumber;
        this.childGameSystemNumber = childGameSystemNumber;
        this.itemsNumber = itemsNumber;
        this.npcNumber = npcNumber;
    }

    public String getName() {
        return name;
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

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameSystem that = (GameSystem) o;
        return gameSessionNumber == that.gameSessionNumber &&
                childGameSystemNumber == that.childGameSystemNumber &&
                itemsNumber == that.itemsNumber && npcNumber == that.npcNumber &&
                Objects.equals(id, that.id) && Objects.equals(name, that.name) &&
                Objects.equals(creationDate, that.creationDate) && Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, owner, gameSessionNumber, childGameSystemNumber, itemsNumber, npcNumber);
    }


}
