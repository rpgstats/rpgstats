package com.nsu.rpgstats.data.properties;

import com.nsu.rpgstats.data.properties.PropertyRepository;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Property;
import com.nsu.rpgstats.entities.Tag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlugPropertyRepository implements PropertyRepository {
    private final HashMap<Integer, Property> properties;
    private Integer currentId;

    public PlugPropertyRepository() {
        properties = new HashMap<>();
        generatePropertyList();
        currentId = 123;
    }

    private void generatePropertyList() {
        for (int i = 117; i < 123; ++i) {
            List<Constraint> constraints = new ArrayList<>();
            for (int j = 0; j < 4; ++j) {
                List<Tag> tags = new ArrayList<>();
                tags.add(new Tag(i+1000*j, "tag " + (i+1000*j),
                        new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()), false));
                tags.add(new Tag(i+1000*j+1, "tag " + (i+1000*j+1),
                        new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()), false));
                tags.add(new Tag(i+1000*j+2, "tag " + (i+1000*j+2),
                        new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()), false));
                constraints.add(new Constraint(i*10+j, "Constraint " + i*10+j, (i%2==0), tags));
            }
            List<Modifier> modifiers = new ArrayList<>();
            modifiers.add(new Modifier(1, "Attack up 1_" + i, i,
                    new Parameter(i, "Attack", new Date(), 0, 993)));
            modifiers.add(new Modifier(2, "Attack up 2_" + i, i,
                    new Parameter(i, "Attack", new Date(), 0, 993)));
            modifiers.add(new Modifier(3, "Attack up 3_" + i, i,
                    new Parameter(i,"Attack", new Date(), 0, 993)));
            modifiers.add(new Modifier(4, "Attack up 4_" + i, i,
                    new Parameter(i,"Attack", new Date(), 0, 993)));
            properties.put(i, new Property(i, "Attack up prop", false, modifiers, constraints));
        }
    }

    @Override
    public List<Property> getProperties(int gameSystem) {
        return new ArrayList<>(properties.values());
    }

    @Override
    public Property getProperty(int gameSystemId, int id) {
        return properties.get(id);
    }

    @Override
    public int addProperty(int gameSystem, Property property) {
        Property p = new Property(property.getId(), property.getName(), property.getDeleted(),
                property.getModifiers(), property.getConstraints());
        properties.put(currentId, p);
        return currentId++;
    }

    @Override
    public void editProperty(int gameSystem, int id, Property property) {
        Property p = new Property(property.getId(), property.getName(), property.getDeleted(),
                property.getModifiers(), property.getConstraints());
        properties.remove(id);
        properties.put(id, p);
    }
}
