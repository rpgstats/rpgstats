package com.nsu.rpgstats.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Modifier implements Identifiable{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("parameterId")
    @Expose
    private Integer parameterId;
    @SerializedName("parameterName")
    @Expose
    private String parameterName;

    public Modifier(Integer id, String name, Integer value, Integer parameterId, String parameterName) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.parameterId = parameterId;
        this.parameterName = parameterName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Modifier modifier = (Modifier) o;
        return Objects.equals(id, modifier.id) && Objects.equals(name, modifier.name) && Objects.equals(value, modifier.value);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
