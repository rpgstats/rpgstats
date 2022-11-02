package com.nsu.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.ItemRepository;
import com.nsu.rpgstats.entities.Item;

public class ItemInfoViewModel extends ViewModel {
    private final int itemId;
    private final ItemRepository repository;
    private MutableLiveData<Item> item;

    public ItemInfoViewModel(int itemId, ItemRepository repository) {
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

    private void loadItem() {
        item.setValue(repository.getItem(itemId));
    }

}
