package com.nsu.rpgstats.data.items;

import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PlugItemRepository implements ItemRepository{
    private final HashMap<Integer, Item> items;
    private int currentId;

    public PlugItemRepository() {
        items = new HashMap<>();
        generateItemList();
        currentId = 205;
    }

    private void generateItemList() {

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(0, "Sword", "Date", false));
        tags.add(new Tag(1, "Iron", "Date", false));
        tags.add(new Tag(2, "Magical", "Date", false));
        tags.add(new Tag(3, "Big", "Date", false));
        List<Modifier> modifiers = new ArrayList<>();
        modifiers.add(new Modifier(300, "Attack up", 10,
                new Parameter(117, "Attack", new Date(), 0, 993)));
        items.put(200 ,new Item(200, 1337, "Excalibur", tags, modifiers, false));

        tags = new ArrayList<>();
        tags.add(new Tag(4, "Helmet", "Date", false));
        tags.add(new Tag(1, "Iron", "Date", false));
        tags.add(new Tag(2, "Magical", "Date", false));
        tags.add(new Tag(5, "Small", "Date", false));
        modifiers = new ArrayList<>();
        modifiers.add(new Modifier(301, "Defense up", 10,
                new Parameter(120, "Defense", new Date(), 3, 996)));
        items.put(201 ,new Item(201, 1337, "Helmet of Atos", tags, modifiers, false));

        tags = new ArrayList<>();
        tags.add(new Tag(6, "Chest plate", "Date", false));
        tags.add(new Tag(7, "Mithril", "Date", false));
        modifiers = new ArrayList<>();
        modifiers.add(new Modifier(302, "Defense up", 50,
                new Parameter(120, "Defense", new Date(), 3, 996)));
        items.put(202 ,new Item(202, 1337, "Mithril chest plate", tags, modifiers, false));

        tags = new ArrayList<>();
        tags.add(new Tag(8, "Ring", "Date", false));
        tags.add(new Tag(9, "Gold", "Date", false));
        tags.add(new Tag(2, "Magical", "Date", false));
        modifiers = new ArrayList<>();
        modifiers.add(new Modifier(303, "Health points up", 50,
                new Parameter(118, "Health points", new Date(), 1, 994)));
        items.put(203 ,new Item(203, 1337, "Ring of health", tags, modifiers, false));

        tags = new ArrayList<>();
        tags.add(new Tag(10, "Dagger", "Date", false));
        tags.add(new Tag(2, "Magical", "Date", false));
        tags.add(new Tag(11, "Poisoned", "Date", false));
        modifiers = new ArrayList<>();
        modifiers.add(new Modifier(304, "Attack up", 5,
                new Parameter(117, "Attack", new Date(), 0, 993)));
        modifiers.add(new Modifier(305, "Attack up", 15,
                new Parameter(117, "Attack", new Date(), 0, 993)));
        items.put(204 ,new Item(204, 1337, "Poisoned dagger", tags, modifiers, false));
    }

    @Override
    public List<Item> getItems(int gameSystem) {
        return  new ArrayList<>(items.values());
    }

    @Override
    public Item getItem(int gameSystem, int id) {
        return items.get(id);
    }

    @Override
    public int addItem(int gameSystem, Item item) {
        Item newItem = new Item(currentId, item.getPictureId(), item.getName(), item.getTags(), item.getModifiers(), item.isDeleted());
        items.put(currentId, newItem);
        return currentId++;
    }

    @Override
    public void editItem(int gameSystem, int id, Item item) {
        Item newItem = new Item(id, item.getPictureId(), item.getName(), item.getTags(), item.getModifiers(), item.isDeleted());
        items.remove(id);
        items.put(id, newItem);

    }
}
