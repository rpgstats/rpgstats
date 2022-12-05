package com.nsu.rpgstats.entities;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Character implements Serializable {
    private int id;
    private String name;
    private String description;
    private int gameSystemId;
    private int sessionId;
    private int userId;
    private transient Bitmap icon;
    private transient Bitmap background;
    private transient Version version;
    private List<Attribute> attributeList = new ArrayList<>();
    private List<Slot> slotList = new ArrayList<>();

    public Character(int id, String name, String description, int gameSystemId, int sessionId, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.gameSystemId = gameSystemId;
        this.sessionId = sessionId;
        this.userId = userId;
    }

    public Character(int id, String name, String description, int gameSystemId, int sessionId, int userId, Version version, List<Attribute> attributeList, List<Slot> slotList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.gameSystemId = gameSystemId;
        this.sessionId = sessionId;
        this.userId = userId;
        this.version = version;
        this.attributeList = attributeList;
        this.slotList = slotList;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public Bitmap getBackground() {
        return background;
    }

    public void setBackground(Bitmap background) {
        this.background = background;
    }

    public List<Slot> getSlotList() {
        return slotList;
    }

    public void setSlotList(List<Slot> slotList) {
        this.slotList = slotList;
    }

    public Version getVersion() {
        return version;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGameSystemId() {
        return gameSystemId;
    }

    public void setGameSystemId(int gameSystemId) {
        this.gameSystemId = gameSystemId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
