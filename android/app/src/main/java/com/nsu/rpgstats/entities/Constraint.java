package com.nsu.rpgstats.entities;

import java.util.List;

public class Constraint implements Identifiable {
    private Integer id;
    private Boolean isBlackList;
    private List<Tag> tags;

    public Constraint() {
    }

    public Constraint(Integer id, Boolean isBlackList, List<Tag> tags) {
        this.id = id;
        this.isBlackList = isBlackList;
        this.tags = tags;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getBlackList() {
        return isBlackList;
    }

    public void setBlackList(Boolean blackList) {
        isBlackList = blackList;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
