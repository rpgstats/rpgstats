package com.nsu.rpgstats.data;

import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getItems();

    Item getItem(int id);

    int addItem(Item item);

    void editItem(int id, Item item);
}
