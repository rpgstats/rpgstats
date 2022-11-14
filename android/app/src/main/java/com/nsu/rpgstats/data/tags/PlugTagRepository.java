package com.nsu.rpgstats.data.tags;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Tag;
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
    public void getTags(int gameSystem, RepositoryCallback<List<Tag>> callback) {
        callback.onComplete(new Result.Success<>(new ArrayList<>(tags.values())));
    }

    @Override
    public void getTag(int gameSystemId, int id, RepositoryCallback<Tag> callback) {
        callback.onComplete(new Result.Success<>(tags.get(id)));
    }

    @Override
    public void addTag(int gameSystem, Tag tag, RepositoryCallback<Tag> callback) {
        tags.put(currentId, new Tag(currentId, tag.getName(), tag.getCreationDate(), tag.isDeleted()));
        callback.onComplete(new Result.Success<>(tags.get(currentId++)));
    }

    @Override
    public void editTag(int gameSystem, int id, Tag tag, RepositoryCallback<Tag> callback) {
        Tag newTag = new Tag(id, tag.getName(), tag.getCreationDate(), tag.isDeleted());
        tags.remove(id);
        tags.put(id, newTag);
        callback.onComplete(new Result.Success<>(tags.get(id)));
    }
}
