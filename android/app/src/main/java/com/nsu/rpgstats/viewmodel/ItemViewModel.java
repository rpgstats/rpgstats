package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.items.ItemRepository;
import com.nsu.rpgstats.data.items.PlugItemRepository;
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

        itemRepository.addItem(gameSystemId, item, result ->  {
            if (result instanceof Result.Success) {
                systemItems.getValue().add(((Result.Success<Item>) result).data);
                systemItems.setValue(systemItems.getValue());
                Log.e("ADD ITEM", "successfully add item");
            }
            //todo handle error
        });
    }

    public void editItem(Item item, int itemId, int gameSystemId) {
        if (systemItems.getValue() == null) {
            systemItems.setValue(new ArrayList<>());
            loadItems(gameSystemId);
        }

        /*Item oldItem = itemRepository.getItem(gameSystemId ,itemId);
        List<Tag> addedTags = new ArrayList<>(item.getTags());
        addedTags.removeAll(oldItem.getTags());

        List<Tag> deletedTags = new ArrayList<>(oldItem.getTags());
        deletedTags.removeAll(item.getTags());


        List<Modifier> addedModifier = new ArrayList<>(item.getModifiers());
        addedModifier.removeAll(oldItem.getModifiers());
        List<Modifier> deletedModifier = new ArrayList<>(oldItem.getModifiers());
        deletedModifier.removeAll(item.getModifiers());



        itemRepository.addItemTags(gameSystemId, itemId, addedTags);
        for (Tag tag: deletedTags) {
            itemRepository.deleteItemTag(gameSystemId, itemId, tag);
        }

        itemRepository.addItemModifiers(gameSystemId, itemId, addedModifier);
        for (Modifier modifier: deletedModifier) {
            itemRepository.deleteItemModifier(gameSystemId, itemId, modifier);
        }*/

        itemRepository.editItem(gameSystemId, itemId, item, result -> {
            if (result instanceof Result.Success) {
                for (int i = 0; i < systemItems.getValue().size(); ++i) {
                    if (systemItems.getValue().get(i).getId() == itemId) {
                        systemItems.getValue().remove(i);
                        systemItems.getValue().add(i, ((Result.Success<Item>) result).data);
                        break;
                    }
                }
                systemItems.setValue(systemItems.getValue());
                Log.e("EDIT ITEM", "successfully edit item");
            }
        });


    }

    public void deleteItem(int itemId, int gameSystemId) {
        if (systemItems.getValue() == null) {
            systemItems.setValue(new ArrayList<>());
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Item>> items = systemItems;
        // todo: find better approach

        Item oldItem = systemItems.getValue().get(itemId);

        itemRepository.editItem(gameSystemId, itemId, new Item(oldItem.getId(), oldItem.getPictureId(),
                oldItem.getName(), oldItem.getTags(), oldItem.getModifiers(), true), result -> {
            if (result instanceof Result.Success) {
                for (int i = 0; i < systemItems.getValue().size(); ++i) {
                    if (systemItems.getValue().get(i).getId() == itemId) {
                        systemItems.getValue().remove(i);
                        systemItems.getValue().add(i, ((Result.Success<Item>) result).data);
                        break;
                    }
                }
                systemItems.setValue(systemItems.getValue());
                Log.e("DELETE ITEM", "successfully deleted item");
            }
        });
    }



    public void loadItems(int gameSystemId) {
        Log.e("ItemViewModel", "loading data");
        itemRepository.getItems(gameSystemId, result -> {
            if (result instanceof Result.Success) {
                systemItems.setValue(((Result.Success<List<Item>>) result).data);
            }
            //todo handle error;
        });
    }
}
