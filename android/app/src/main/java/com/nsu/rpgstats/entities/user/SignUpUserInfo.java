package com.nsu.rpgstats.entities.user;

public class SignUpUserInfo {
    private final String username;
    private final String password;
    private final String email;

    public SignUpUserInfo(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
