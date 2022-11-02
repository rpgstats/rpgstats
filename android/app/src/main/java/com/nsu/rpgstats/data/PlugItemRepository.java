package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Item;

import java.util.ArrayList;
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
            List<String> tags = new ArrayList<>();
            tags.add("tag 1 " + i);
            tags.add("tag 2 " + i);
            tags.add("tag 3 " + i);
            tags.add("tag 4 " + i);
            List<String> modifiers = new ArrayList<>();
            modifiers.add("modifier 1 " + i);
            modifiers.add("modifier 2 " + i);
            modifiers.add("modifier 3 " + i);
            modifiers.add("modifier 4 " + i);
            items.put(i, new Item(i, 1337, "item " + i, tags, modifiers, false));
        }
    }

    @Override
    public List<Item> getItems() {
        return  new ArrayList<>(items.values());
    }

    @Override
    public Item getItem(int id) {
        return items.get(id);
    }

    @Override
    public int addItem(Item item) {
        Item newItem = new Item(currentId, item.getPictureId(), item.getName(), item.getTags(), item.getModifiers(), item.isDeleted());
        items.put(currentId, newItem);
        return currentId++;
    }

    @Override
    public void editItem(int id, Item item) {
        Item newItem = new Item(id, item.getPictureId(), item.getName(), item.getTags(), item.getModifiers(), item.isDeleted());
        items.put(currentId, newItem);
    }
}
