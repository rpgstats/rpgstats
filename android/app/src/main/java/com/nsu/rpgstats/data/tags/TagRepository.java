package com.nsu.rpgstats.data.tags;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

public interface TagRepository {
    void getTags(int gameSystemId, RepositoryCallback<List<Tag>> callback);

    void getTag(int gameSystemId ,int id, RepositoryCallback<Tag> callback);

    void addTag(int gameSystemId, Tag tag, RepositoryCallback<Tag> callback);

    void editTag(int gameSystemId, int id, Tag tag, RepositoryCallback<Tag> callback);
}
