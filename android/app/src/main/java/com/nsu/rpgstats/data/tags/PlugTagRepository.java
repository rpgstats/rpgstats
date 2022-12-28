package com.nsu.rpgstats.data.tags;

import com.nsu.rpgstats.entities.Tag;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PlugTagRepository implements TagRepository{
    private final HashMap<Integer, Tag> tags;
    private int currentId;

    public PlugTagRepository() {
        tags = new HashMap<>();
        generateItemList();
        currentId = 12;
    }

    private void generateItemList() {
        tags.put(0, new Tag(0, "Sword", "Date", false));
        tags.put(1, new Tag(1, "Iron", "Date", false));
        tags.put(2, new Tag(2, "Magical", "Date", false));
        tags.put(3, new Tag(3, "Big", "Date", false));
        tags.put(4, new Tag(4, "Helmet", "Date", false));
        tags.put(5, new Tag(5, "Small", "Date", false));
        tags.put(6, new Tag(6, "Chest plate", "Date", false));
        tags.put(7, new Tag(7, "Mithril", "Date", false));
        tags.put(8, new Tag(8, "Ring", "Date", false));
        tags.put(9, new Tag(9, "Gold", "Date", false));
        tags.put(10, new Tag(10, "Dagger", "Date", false));
        tags.put(11, new Tag(11, "Poisoned", "Date", false));
    }

    @Override
    public List<Tag> getTags(int gameSystemId) {
        return new ArrayList<>(tags.values());
    }

    @Override
    public Tag getTag(int gameSystemId, int id) {
        return tags.get(id);
    }

    @Override
    public int addTag(int gameSystemId, Tag tag) {
        tags.put(currentId, new Tag(currentId, tag.getName(), tag.getCreationDate(), tag.isDeleted()));
        return currentId++;
    }

    @Override
    public void editTag(int gameSystemId, int id, Tag tag) {
        tags.remove(id);
        tags.put(id, new Tag(id, tag.getName(), tag.getCreationDate(), tag.isDeleted()));
    }
}
