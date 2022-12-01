package com.nsu.rpgstats.entities;

import java.util.Objects;

public class Version {
    private String description;
    private Character character;
    private String date;

    public Version(String description, Character character, String date) {
        this.description = description;
        this.character = character;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return Objects.equals(description, version.description) && Objects.equals(character, version.character) && Objects.equals(date, version.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, character, date);
    }
}
