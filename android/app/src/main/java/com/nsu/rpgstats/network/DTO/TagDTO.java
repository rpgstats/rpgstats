package com.nsu.rpgstats.network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nsu.rpgstats.entities.Tag;

public class TagDTO {
    public TagDTO(Tag tag, int systemId) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.systemId = systemId;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
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

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

}