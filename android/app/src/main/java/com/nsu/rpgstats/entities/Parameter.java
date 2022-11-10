package com.nsu.rpgstats.entities;

import java.util.Date;

public class Parameter implements Identifiable {
    private Integer id;
    private String name;
    private Date createdAt;
    private Integer min;
    private Integer max;

    public Parameter(Integer id, String name, Date createdAt, Integer min, Integer max) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
