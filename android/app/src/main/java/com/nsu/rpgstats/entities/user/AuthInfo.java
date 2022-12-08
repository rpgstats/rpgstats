package com.nsu.rpgstats.entities.user;

public class AuthInfo {
    private final String authToken;
    private final int ownerId;

    public AuthInfo(String token, int ownerId) {
        authToken = token;
        this.ownerId = ownerId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getOwnerId() {
        return ownerId;
    }
}
