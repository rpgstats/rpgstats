package com.nsu.rpgstats.entities;

public class SessionCharacter {
    private String characterName;
    private String userName;

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SessionCharacter(String characterName, String userName) {
        this.characterName = characterName;
        this.userName = userName;
    }
}
