package com.nsu.rpgstats.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.tags.TagRepository;
import com.nsu.rpgstats.entities.Tag;

public class TagInfoViewModel extends ViewModel {
    private final int tagId;
    private final TagRepository repository;
    private MutableLiveData<Tag> tag;
    private int gameSystemId;

    public TagInfoViewModel(int gameSystemId, int tagId, TagRepository repository) {
        this.gameSystemId = gameSystemId;
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
        tag.setValue(repository.getTag(gameSystemId, tagId));
    }
}
