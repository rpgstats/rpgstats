package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> getTags(int gameSystemId);

    Tag getTag(int gameSystemId ,int id);

    int addTag(int gameSystemId, Tag tag);

    int editTag(int gameSystemId, int id, Tag tag);
}
