package com.nsu.rpgstats.network.dto;

public class SessionRequest {

    private String name;
    private String description;
    private int maxNumberOfPlayers;
    private int systemId;
    private boolean creatorAsPlayer;

    public SessionRequest(String name, String description, int maximumPlayers, int gameSystemId, boolean creatorAsPlayer) {
        this.name = name;
        this.description = description;
        this.maxNumberOfPlayers = maximumPlayers;
        this.systemId = gameSystemId;
        this.creatorAsPlayer = creatorAsPlayer;
    }

    public int getSystemId() {
        return systemId;
    }

    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public boolean isCreatorAsPlayer() {
        return creatorAsPlayer;
    }
}
