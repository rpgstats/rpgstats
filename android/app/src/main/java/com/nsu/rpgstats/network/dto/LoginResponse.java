package com.nsu.rpgstats.network.dto;

public class LoginResponse {
    private final String authToken;

    private final int id;

    public LoginResponse(String token, int id) {
        this.authToken = token;
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public int getId() {
        return id;
    }
}
