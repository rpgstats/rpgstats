package com.nsu.rpgstats.network.dto;

public class LoginResponse {
    private final String authToken;

    public LoginResponse(String token) {
        this.authToken = token;
    }

    public String getAuthToken() {
        return authToken;
    }
}
