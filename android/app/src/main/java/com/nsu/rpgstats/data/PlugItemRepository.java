package com.nsu.rpgstats.data;

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
        currentId = 123;
    }

    private void generateItemList() {
        for (int i = 117; i < 123; i++) {
            List<Tag> tags = new ArrayList<>();
            tags.add(new Tag(0, "tag 1" + i, "Date", false));
            tags.add(new Tag(1, "tag 2" + i, "Date", false));
            tags.add(new Tag(2, "tag 3" + i, "Date", false));
            tags.add(new Tag(3, "tag 4" + i, "Date", false));
            List<Modifier> modifiers = new ArrayList<>();
            modifiers.add(new Modifier(1, "Attack up 1_" + i, i,
                    new Parameter(i * 10 + 1, "Attack", new Date(), 0, 993)));
            modifiers.add(new Modifier(2, "Attack up 2_" + i, i,
                    new Parameter(i * 10 + 2,"Attack", new Date(), 0, 993)));
            modifiers.add(new Modifier(3, "Attack up 3_" + i, i,
                    new Parameter(i * 10 + 3,"Attack", new Date(), 0, 993)));
            modifiers.add(new Modifier(4, "Attack up 4_" + i, i,
                    new Parameter(i * 10 + 4,"Attack", new Date(), 0, 993)));
            items.put(i, new Item(i, 1337, "item " + i, tags, modifiers, false));
        }
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
