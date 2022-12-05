package com.nsu.rpgstats.entities.user;

public class AuthToken {
    private final String authToken;

    public AuthToken(String token) {
        authToken = token;
    }

    public String getAuthToken() {
        return authToken;
    }
}
