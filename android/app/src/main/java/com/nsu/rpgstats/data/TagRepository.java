package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

public interface TagRepository {
    List<Tag> getTags();

    Tag getTag(int id);

    int addTag(Tag tag);

    void editTag(int id, Tag tag);
}
