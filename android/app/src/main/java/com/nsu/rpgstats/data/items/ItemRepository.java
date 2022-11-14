package com.nsu.rpgstats.data.items;

import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getItems(int gameSystem);

    Item getItem(int gameSystemId, int id);

    int addItem(int gameSystem, Item item);

    void editItem(int gameSystem, int id, Item item);
}
