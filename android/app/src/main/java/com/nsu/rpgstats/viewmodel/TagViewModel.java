package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.PlugTagRepository;
import com.nsu.rpgstats.data.TagRepository;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private MutableLiveData<List<Tag>> tags;
    private final TagRepository tagRepository;

    public TagViewModel(@NonNull Application application) {
        super(application);
        tagRepository = ((RpgstatsApplication) getApplication()).appContainer.tagRepository;
    }

    public LiveData<List<Tag>> getTags(int gameSystemId) {
        if (tags == null) {
            tags = new MutableLiveData<>();
            loadTags(gameSystemId);
        }
        return tags;
    }

    public void addTag(Tag tag, int gameSystemId) {
        if (tags == null) {
            tags = new MutableLiveData<>();
            loadTags(gameSystemId);
        }
        // todo: find better approach

        int gsId = tagRepository.addTag(tag);
        Tag addedTag = tagRepository.getTag(gsId);
        tags.getValue().add(addedTag);
        tags.setValue(tags.getValue());
        Log.e("ADD TAG", "successfully add tag");
    }

    public void editTag(Tag tag, int tagId, int gameSystemId) {
        if (tags == null) {
            tags = new MutableLiveData<>();
            loadTags(gameSystemId);
        }
        // todo: find better approach
        tagRepository.editTag(tagId, tag);
        Tag addedTag = tagRepository.getTag(tagId);
        for (int i = 0; i < tags.getValue().size(); ++i) {
            if (tags.getValue().get(i).getId() == tagId) {
                tags.getValue().remove(i);
                tags.getValue().add(i, addedTag);
                break;
            }
        }
        tags.setValue(tags.getValue());
        Log.e("EDIT TAG", "successfully edit tag");
    }

    public void deleteTag(int tagId, int gameSystemId) {
        if (tags == null) {
            tags = new MutableLiveData<>();
            loadTags(gameSystemId);
        }
        // todo: find better approach
        Tag oldTag = tagRepository.getTag(tagId);
        tagRepository.editTag(tagId, new Tag(tagId, oldTag.getName(), oldTag.getCreationDate(), true));
        Tag editedTag = tagRepository.getTag(tagId);

        for (int i = 0; i < tags.getValue().size(); ++i) {
            if (tags.getValue().get(i).getId() == tagId) {
                tags.getValue().remove(i);
                tags.getValue().add(i, editedTag);
                break;
            }
        }
        tags.setValue(tags.getValue());
        Log.e("DELETE TAG", "successfully deleted tag");
    }

    private void loadTags(int gameSystemId) {
        // suppose getting from server in future
        TagRepository tagRepository = new PlugTagRepository();
        tags.setValue(tagRepository.getTags());
    }
}
