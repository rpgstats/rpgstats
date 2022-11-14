package com.nsu.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.items.ItemRepository;
import com.nsu.rpgstats.entities.Item;

public class ItemInfoViewModel extends ViewModel {
    private final int itemId;
    private final ItemRepository repository;
    private MutableLiveData<Item> item;
    private int gameSystemId;

    public ItemInfoViewModel(int gameSystemId, int itemId, ItemRepository repository) {
        this.gameSystemId = gameSystemId;
        this.itemId = itemId;
        this.repository = repository;
    }

    public LiveData<Item> getItemInfo(){
        if (item == null) {
            item = new MutableLiveData<>();
            loadItem();
        }
        return item;
    }

    public void loadItem() {
        repository.getItem(gameSystemId, itemId, result -> {
            if (result instanceof Result.Success) {
                item.setValue(((Result.Success<Item>) result).data);
            }
        });
    }

}
