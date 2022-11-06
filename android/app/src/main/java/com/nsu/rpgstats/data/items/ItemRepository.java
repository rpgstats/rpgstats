package com.nsu.rpgstats.data.items;

import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

public interface ItemRepository {
    List<Item> getItems(int gameSystemId);

    Item getItem(int gameSystemId, int id);

    int addItem(int gameSystemId, Item item);

    int editItem(int gameSystemId, int id, Item item);

    List<Tag> getItemTags(int gameSystemId, int itemId);

    List<Tag> addItemTags(int gameSystemId, int itemId, List<Tag> tags);

    Tag deleteItemTag(int gameSystemId, int itemId, Tag tag);

    List<Modifier> getItemModifiers(int gameSystemId, int itemId);

    List<Modifier> addItemModifiers(int gameSystemId, int itemId, List<Modifier> modifiers);

    Modifier deleteItemModifier(int gameSystemId, int itemId, Modifier modifier);

}
