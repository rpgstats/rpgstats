package com.nsu.rpgstats.data;

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
        currentId = 123;
    }

    private void generateItemList() {
        for (int i = 117; i < 123; i++) {
            tags.put(i, new Tag(i, "tag " + i, new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()), false));
        }
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
