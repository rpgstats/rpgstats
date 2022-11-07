package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Property;

import java.util.List;

public interface PropertyRepository {
    List<Property> getProperties(int gameSystem);

    Property getProperty(int gameSystemId, int id);

    int addProperty(int gameSystem, Property property);

    void editProperty(int gameSystem, int id, Property property);
}
