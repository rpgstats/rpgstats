package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.ItemRepository;
import com.nsu.rpgstats.data.PlugItemRepository;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemViewModel extends AndroidViewModel {
    private MutableLiveData<List<Item>> systemItems;
    private final ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        systemItems = new MutableLiveData<>();
        itemRepository = ((RpgstatsApplication)getApplication()).appContainer.itemRepository;        // suppose getting from server in future
        Log.e("ItemViewModel", "new instance");
    }

    public LiveData<List<Item>> getItems(int gameSystemId) {
        if (systemItems.getValue() == null) {
            systemItems.setValue(new ArrayList<>());
            loadItems(gameSystemId);
        }
        return systemItems;
    }

    public LiveData<List<Item>> getLiveDataItems() {
        systemItems.setValue(new ArrayList<>());
        return systemItems;
    }

    public void addItem(Item item, int gameSystemId) {
        if (systemItems.getValue() == null) {
            systemItems.setValue(new ArrayList<>());
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems;
        // todo: find better approach

        int itemId = itemRepository.addItem(gameSystemId, item);
        itemRepository.addItemTags(gameSystemId, itemId, item.getTags());
        itemRepository.addItemModifiers(gameSystemId, itemId,item.getModifiers());
        Item addedItem = itemRepository.getItem(gameSystemId, itemId);
        items.getValue().add(addedItem);
        items.setValue(items.getValue());
        Log.e("ADD ITEM", "successfully add item");
    }

    public void editItem(Item item, int itemId, int gameSystemId) {
        if (systemItems.getValue() == null) {
            systemItems.setValue(new ArrayList<>());
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems;
        // todo: find better approach

        Item oldItem = itemRepository.getItem(gameSystemId ,itemId);
        List<Tag> addedTags = new ArrayList<>(item.getTags());
        addedTags.removeAll(oldItem.getTags());

        List<Tag> deletedTags = new ArrayList<>(oldItem.getTags());
        deletedTags.removeAll(item.getTags());


        List<Modifier> addedModifier = new ArrayList<>(item.getModifiers());
        addedModifier.removeAll(oldItem.getModifiers());
        List<Modifier> deletedModifier = new ArrayList<>(oldItem.getModifiers());
        deletedModifier.retainAll(item.getModifiers());
        itemRepository.editItem(gameSystemId, itemId, item);

        itemRepository.addItemTags(gameSystemId, itemId, addedTags);
        for (Tag tag: deletedTags) {
            itemRepository.deleteItemTag(gameSystemId, itemId, tag);
        }

        itemRepository.addItemModifiers(gameSystemId, itemId, addedModifier);
        for (Modifier modifier: deletedModifier) {
            itemRepository.deleteItemModifier(gameSystemId, itemId, modifier);
        }

        Item addedItem = itemRepository.getItem(gameSystemId ,itemId);

        for (int i = 0; i < items.getValue().size(); ++i) {
            if (items.getValue().get(i).getId() == itemId) {
                items.getValue().remove(i);
                items.getValue().add(i, addedItem);
                break;
            }
        }
        items.setValue(items.getValue());
        Log.e("EDIT ITEM", "successfully edit item");
    }

    public void deleteItem(int itemId, int gameSystemId) {
        if (systemItems.getValue() == null) {
            systemItems.setValue(new ArrayList<>());
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems;
        // todo: find better approach
        Item oldItem = itemRepository.getItem(gameSystemId ,itemId);
        itemRepository.editItem(gameSystemId, itemId, new Item(oldItem.getId(), oldItem.getPictureId(), oldItem.getName(), oldItem.getTags(), oldItem.getModifiers(), true));
        Item editedItem = itemRepository.getItem(gameSystemId, itemId);

        for (int i = 0; i < items.getValue().size(); ++i) {
            if (items.getValue().get(i).getId() == itemId) {
                items.getValue().remove(i);
                items.getValue().add(i, editedItem);
                break;
            }
        }
        items.setValue(items.getValue());
        Log.e("DELETE ITEM", "successfully deleted item");
    }



    public void loadItems(int gameSystemId) {
        Log.e("ItemViewModel", "loading data");
        systemItems.setValue(itemRepository.getItems(gameSystemId));
    }
}
