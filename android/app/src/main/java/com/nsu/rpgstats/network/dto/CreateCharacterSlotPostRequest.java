package com.nsu.rpgstats.network.dto;

public class CreateCharacterSlotPostRequest {
    private String name;
    private String iconUrl;
    private Boolean isWhiteListed;
    private Integer itemId;

    public CreateCharacterSlotPostRequest(String name, String iconUrl, Boolean isWhiteListed, Integer itemId) {
        this.name = name;
        this.iconUrl = iconUrl;
        this.isWhiteListed = isWhiteListed;
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Boolean getWhiteListed() {
        return isWhiteListed;
    }

    public void setWhiteListed(Boolean whiteListed) {
        isWhiteListed = whiteListed;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
