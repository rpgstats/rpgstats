package com.nsu.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.ItemRepository;
import com.nsu.rpgstats.data.TagRepository;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Tag;

public class TagInfoViewModel extends ViewModel {
    private final int tagId;
    private final TagRepository repository;
    private MutableLiveData<Tag> tag;

    public TagInfoViewModel(int tagId, TagRepository repository) {
        this.tagId = tagId;
        this.repository = repository;
    }

    public LiveData<Tag> getItemInfo(){
        if (tag == null) {
            tag = new MutableLiveData<>();
            loadItem();
        }
        return tag;
    }

    private void loadItem() {
        tag.setValue(repository.getTag(tagId));
    }
}
