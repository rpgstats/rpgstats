package com.nsu.rpgstats.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.Result;
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

        tagRepository.addTag(gameSystemId, tag, result -> {
            if (result instanceof Result.Success) {
                systemTags.getValue().add(((Result.Success<Tag>) result).data);
                systemTags.setValue(systemTags.getValue());
                Log.e("ADD TAG", "successfully add tag");
            }
            //todo handle error
        });


    }

    public void editTag(Tag tag, int tagId, int gameSystemId) {
        if (systemTags.getValue() == null) {
            systemTags.setValue(new ArrayList<>());
            loadTags(gameSystemId);
        }
        // todo: find better approach
        tagRepository.editTag(gameSystemId, tagId, tag, result -> {
            if (result instanceof Result.Success) {
                for (int i = 0; i < systemTags.getValue().size(); ++i) {
                    if (systemTags.getValue().get(i).getId() == tagId) {
                        systemTags.getValue().remove(i);
                        systemTags.getValue().add(i, ((Result.Success<Tag>) result).data);
                        break;
                    }
                }
                systemTags.setValue(systemTags.getValue());
                Log.e("EDIT TAG", "successfully edit tag");
            }
        });

    }

    public void deleteTag(int tagId, int gameSystemId) {
        if (systemTags.getValue() == null) {
            systemTags.setValue(new ArrayList<>());
            loadTags(gameSystemId);
        }
        MutableLiveData<List<Tag>> tags = systemTags;
        // todo: find better approach
        Tag oldTag = systemTags.getValue().get(tagId);
        tagRepository.editTag(gameSystemId, tagId, new Tag(tagId, oldTag.getName(), oldTag.getCreationDate(), true), result -> {
           if (result instanceof Result.Success) {
               for (int i = 0; i < tags.getValue().size(); ++i) {
                   if (tags.getValue().get(i).getId() == tagId) {
                       tags.getValue().remove(i);
                       tags.getValue().add(i, ((Result.Success<Tag>) result).data);
                       break;
                   }
               }
               tags.setValue(tags.getValue());
               Log.e("DELETE TAG", "successfully deleted tag");
           }
           //todo handle error
        });
    }

    public void loadTags(int gameSystemId) {
        tagRepository.getTags(gameSystemId, result -> {
            if (result instanceof Result.Success) {
                systemTags.setValue(((Result.Success<List<Tag>>) result).data);
            }
            //todo handle error
        });
    }
}
