package com.nsu.rpgstats.data.items;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

public interface ItemRepository {
    void getItems(int gameSystemId, RepositoryCallback<List<Item>> callback);

    void getItem(int gameSystemId, int id, RepositoryCallback<Item> callback);

    void addItem(int gameSystemId, Item item, RepositoryCallback<Item> callback);

    void editItem(int gameSystemId, int id, Item item, RepositoryCallback<Item> callback);

    void getItemTags(int gameSystemId, int itemId, RepositoryCallback<List<Tag>> callback);

    void addItemTags(int gameSystemId, int itemId, List<Tag> tags, RepositoryCallback<List<Tag>> callback);

    void deleteItemTag(int gameSystemId, int itemId, Tag tag, RepositoryCallback<Tag> callback);

    void getItemModifiers(int gameSystemId, int itemId, RepositoryCallback<List<Modifier>> callback);

    void addItemModifiers(int gameSystemId, int itemId, List<Modifier> modifiers, RepositoryCallback<List<Modifier>> callback);

    void deleteItemModifier(int gameSystemId, int itemId, Modifier modifier, RepositoryCallback<Modifier> callback);

}
