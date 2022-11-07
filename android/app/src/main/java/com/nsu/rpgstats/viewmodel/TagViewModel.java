package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.tags.PlugTagRepository;
import com.nsu.rpgstats.data.tags.TagRepository;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Tag>> systemTags;
    private final TagRepository tagRepository;

    public TagViewModel(@NonNull Application application) {
        super(application);
        systemTags = new MutableLiveData<>();
        tagRepository = ((RpgstatsApplication) getApplication()).appContainer.tagRepository;        // suppose getting from server in future
    }

    public LiveData<List<Tag>> getTags(int gameSystemId) {
        if (systemTags.getValue() == null) {
            systemTags.setValue(new ArrayList<>());
            loadTags(gameSystemId);
        }
        return systemTags;
    }

    public LiveData<List<Tag>> getLiveDataTags() {
        systemTags.setValue(new ArrayList<>());
        return systemTags;
    }

    public void addTag(Tag tag, int gameSystemId) {
        if (systemTags.getValue() == null) {
            systemTags.setValue(new ArrayList<>());
            loadTags(gameSystemId);
        }
        MutableLiveData<List<Tag>> tags = systemTags;
        // todo: find better approach


        int tagId = tagRepository.addTag(gameSystemId, tag);
        Tag addedTag = tagRepository.getTag(gameSystemId, tagId);
        tags.getValue().add(addedTag);
        tags.setValue(tags.getValue());
        Log.e("ADD TAG", "successfully add tag");
    }

    public void editTag(Tag tag, int tagId, int gameSystemId) {
        if (systemTags.getValue() == null) {
            systemTags.setValue(new ArrayList<>());
            loadTags(gameSystemId);
        }
        MutableLiveData<List<Tag>> tags = systemTags;
        // todo: find better approach
        tagRepository.editTag(gameSystemId, tagId, tag);
        Tag addedTag = tagRepository.getTag(gameSystemId, tagId);
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
        if (systemTags.getValue() == null) {
            systemTags.setValue(new ArrayList<>());
            loadTags(gameSystemId);
        }
        MutableLiveData<List<Tag>> tags = systemTags;
        // todo: find better approach
        Tag oldTag = tagRepository.getTag(gameSystemId, tagId);
        tagRepository.editTag(gameSystemId, tagId, new Tag(tagId, oldTag.getName(), oldTag.getCreationDate(), true));
        Tag editedTag = tagRepository.getTag(gameSystemId, tagId);

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

    public void loadTags(int gameSystemId) {

        systemTags.setValue(tagRepository.getTags(gameSystemId));
    }
}
