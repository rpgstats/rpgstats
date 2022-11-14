package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.items.ItemRepository;
import com.nsu.rpgstats.data.items.PlugItemRepository;
import com.nsu.rpgstats.entities.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemViewModel extends AndroidViewModel {
    private Map<Integer, MutableLiveData<List<Item>>> systemItems;
    private final ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        systemItems = new HashMap<>();
        itemRepository = ((RpgstatsApplication)getApplication()).appContainer.itemRepository;
    }

    public LiveData<List<Item>> getItems(int gameSystemId) {
        if (!systemItems.containsKey(gameSystemId)) {
            MutableLiveData<List<Item>> items = new MutableLiveData<>();
            systemItems.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        return systemItems.get(gameSystemId);
    }

    public void addItem(Item item, int gameSystemId) {
        if (!systemItems.containsKey(gameSystemId)) {
            MutableLiveData<List<Item>> items = new MutableLiveData<>();
            systemItems.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems.get(gameSystemId);
        // todo: find better approach

        int itemId = itemRepository.addItem(gameSystemId, item);
        Item addedItem = itemRepository.getItem(gameSystemId, itemId);
        items.getValue().add(addedItem);
        items.setValue(items.getValue());
        Log.e("ADD ITEM", "successfully add item");
    }

    public void editItem(Item item, int itemId, int gameSystemId) {
        if (!systemItems.containsKey(gameSystemId)) {
            MutableLiveData<List<Item>> items = new MutableLiveData<>();
            systemItems.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems.get(gameSystemId);
        // todo: find better approach
        itemRepository.editItem(gameSystemId, itemId, item);
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
        if (!systemItems.containsKey(gameSystemId)) {
            MutableLiveData<List<Item>> items = new MutableLiveData<>();
            systemItems.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems.get(gameSystemId);
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



    private void loadItems(int gameSystemId) {
        // suppose getting from server in future
        ItemRepository itemRepository = new PlugItemRepository();
        systemItems.get(gameSystemId).setValue(itemRepository.getItems(gameSystemId));
    }
}
