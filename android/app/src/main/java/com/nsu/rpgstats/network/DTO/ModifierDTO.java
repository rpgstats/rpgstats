package com.nsu.rpgstats.network.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nsu.rpgstats.entities.Modifier;

public class ModifierDTO {

    public ModifierDTO(Modifier modifier, int systemId) {
        this.id = modifier.getId();
        this.name = modifier.getName();
        this.systemId = systemId;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("parameter_id")
    @Expose
    private Integer parameterId;
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

}