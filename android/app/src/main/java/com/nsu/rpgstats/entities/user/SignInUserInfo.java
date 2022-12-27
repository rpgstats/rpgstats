package com.nsu.rpgstats.entities.user;

public class SignInUserInfo {
    private final String username;
    private final String password;

    public SignInUserInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
