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

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private MutableLiveData<List<Item>> items;
    private final ItemRepository itemRepository;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        itemRepository = ((RpgstatsApplication)getApplication()).appContainer.itemRepository;
    }

    public LiveData<List<Item>> getItems(int gameSystemId) {
        if (items == null) {
            items = new MutableLiveData<>();
            loadItems(gameSystemId);
        }
        return items;
    }

    public void addItem(Item item, int gameSystemId) {
        if (items == null) {
            items = new MutableLiveData<>();
            loadItems(gameSystemId);
        }
        // todo: find better approach

        int gsId = itemRepository.addItem(item);
        Item addedItem = itemRepository.getItem(gsId);
        items.getValue().add(addedItem);
        items.setValue(items.getValue());
        Log.e("ADD ITEM", "successfully add item");
    }

    public void editItem(Item item, int itemId, int gameSystemId) {
        if (items == null) {
            items = new MutableLiveData<>();
            loadItems(gameSystemId);
        }
        // todo: find better approach
        Item oldItem = itemRepository.getItem(itemId);
        itemRepository.editItem(itemId, item);
        Item addedItem = itemRepository.getItem(itemId);
        items.getValue().remove(oldItem);
        items.getValue().add(addedItem);
        items.setValue(items.getValue());
        Log.e("EDIT ITEM", "successfully edit item");
    }

    public void deleteItem(int itemId, int gameSystemId) {
        if (items == null) {
            items = new MutableLiveData<>();
            loadItems(gameSystemId);
        }
        // todo: find better approach
        Item oldItem = itemRepository.getItem(itemId);
        items.getValue().remove(oldItem);
        oldItem.setDeleted(true);
        itemRepository.editItem(itemId, oldItem);
        Item addedItem = itemRepository.getItem(itemId);
        items.getValue().add(addedItem);
        items.setValue(items.getValue());
        Log.e("DELETE ITEM", "successfully deleted item");
    }

    private void loadItems(int gameSystemId) {
        // suppose getting from server in future
        ItemRepository itemRepository = new PlugItemRepository();
        items.setValue(itemRepository.getItems());
    }
}
