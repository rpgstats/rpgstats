package com.nsu.rpgstats.network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.Item;

public class ItemDTO {

    public ItemDTO(Item item, int systemId) {
        this.id = item.getId();
        this.name = item.getName();
        this.isPresent = !item.isDeleted();
        this.systemId = systemId;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_present")
    @Expose
    private Boolean isPresent;
    @SerializedName("system_id")
    @Expose
    private Integer systemId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(Boolean isPresent) {
        this.isPresent = isPresent;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

}