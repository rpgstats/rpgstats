package com.nsu.rpgstats.data.items;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

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
            modifiers.add(new Modifier(1, "Attack up 1_" + i, i, i, "Attack"));
            modifiers.add(new Modifier(2, "Attack up 2_" + i, i, i, "Attack"));
            modifiers.add(new Modifier(3, "Attack up 3_" + i, i, i, "Attack"));
            modifiers.add(new Modifier(4, "Attack up 4_" + i, i, i, "Attack"));
            items.put(i, new Item(i, 1337, "item " + i, tags, modifiers, false));
        }
    }


    @Override
    public void getItems(int gameSystemId, RepositoryCallback<List<Item>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(items.values())));
    }

    @Override
    public void getItem(int gameSystemId, int id, RepositoryCallback<Item> callback) {
        callback.onComplete(new Result.Success<>(items.get(id)));
    }

    @Override
    public void addItem(int gameSystemId, Item item, RepositoryCallback<Item> callback) {
        Item newItem = new Item(currentId, item.getPictureId(), item.getName(), item.getTags(), item.getModifiers(), item.isDeleted());
        items.put(currentId, newItem);
        callback.onComplete(new Result.Success<>(items.get(currentId)));
        currentId++;
    }

    @Override
    public void editItem(int gameSystemId, int id, Item item, RepositoryCallback<Item> callback) {
        Item newItem = new Item(id, item.getPictureId(), item.getName(), items.get(id).getTags(), items.get(id).getModifiers(), item.isDeleted());
        items.put(id, newItem);
        callback.onComplete(new Result.Success<>(items.get(id)));
    }

    @Override
    public void getItemTags(int gameSystemId, int itemId, RepositoryCallback<List<Tag>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(items.get(itemId).getTags())));
    }

    @Override
    public void addItemTags(int gameSystemId, int itemId, List<Tag> tags, RepositoryCallback<List<Tag>> callback) {
        Item item = items.get(itemId);
        List<Tag> tagList = item.getTags();
        tagList.addAll(tags);
        HashMap<Integer, Tag> set = new HashMap<>();
        for (Tag tag : tagList) {
            set.put(tag.getId(), tag);
        }
        List<Tag> tagSet = new ArrayList<>(set.values());
        items.get(itemId).setTags(tagSet);

        callback.onComplete(new Result.Success<>(tagSet));
    }

    @Override
    public void deleteItemTag(int gameSystemId, int itemId, Tag tag, RepositoryCallback<Tag> callback) {
        items.get(itemId).getTags().remove(tag);
        callback.onComplete(new Result.Success<>(tag));
    }

    @Override
    public void getItemModifiers(int gameSystemId, int itemId, RepositoryCallback<List<Modifier>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(items.get(itemId).getModifiers())));
    }

    @Override
    public void addItemModifiers(int gameSystemId, int itemId, List<Modifier> modifiers, RepositoryCallback<List<Modifier>> callback) {
        Item item = items.get(itemId);
        List<Modifier> modifierList = item.getModifiers();
        modifierList.addAll(modifiers);
        HashMap<Integer, Modifier> set = new HashMap<>();
        for (Modifier modifier : modifierList) {
            set.put(modifier.getId(), modifier);
        }
        List<Modifier> modifierSet = new ArrayList<>(set.values());
        items.get(itemId).setModifiers(modifierSet);

        callback.onComplete(new Result.Success<>(modifierSet));
    }

    @Override
    public void deleteItemModifier(int gameSystemId, int itemId, Modifier modifier, RepositoryCallback<Modifier> callback) {
        items.get(itemId).getModifiers().remove(modifier);
        callback.onComplete(new Result.Success<>(modifier));
    }
}
