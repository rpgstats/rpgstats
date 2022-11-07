package com.nsu.rpgstats.ui.properties;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.PlugPropertyRepository;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.entities.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyViewModel extends AndroidViewModel {
    private Map<Integer, MutableLiveData<List<Property>>> systemProps;
    private final PropertyRepository propRepository;

    public PropertyViewModel(@NonNull Application application) {
        super(application);
        systemProps = new HashMap<>();
        propRepository = ((RpgstatsApplication)getApplication()).appContainer.propertyRepository;
    }

    public LiveData<List<Property>> getItems(int gameSystemId) {
        if (!systemProps.containsKey(gameSystemId)) {
            MutableLiveData<List<Property>> items = new MutableLiveData<>();
            systemProps.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        return systemProps.get(gameSystemId);
    }

    public void addProp(Property prop, int gameSystemId) {
        if (!systemProps.containsKey(gameSystemId)) {
            MutableLiveData<List<Property>> props = new MutableLiveData<>();
            systemProps.put(gameSystemId, props);
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Property>> items = systemProps.get(gameSystemId);
        // todo: find better approach

        int propId = propRepository.addProperty(gameSystemId, prop);
        Property addedProp = propRepository.getProperty(gameSystemId, propId);
        items.getValue().add(addedProp);
        items.setValue(items.getValue());
        Log.e("ADD ITEM", "successfully add item");
    }

    public void editItem(Property property, int propId, int gameSystemId) {
        if (!systemProps.containsKey(gameSystemId)) {
            MutableLiveData<List<Property>> items = new MutableLiveData<>();
            systemProps.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Property>> props = systemProps.get(gameSystemId);
        // todo: find better approach
        propRepository.editProperty(gameSystemId, propId, property);
        Property addedItem = propRepository.getProperty(gameSystemId ,propId);
        for (int i = 0; i < props.getValue().size(); ++i) {
            if (props.getValue().get(i).getId() == propId) {
                props.getValue().remove(i);
                props.getValue().add(i, addedItem);
                break;
            }
        }
        props.setValue(props.getValue());
        Log.e("EDIT ITEM", "successfully edit item");
    }

    public void deleteItem(int itemId, int gameSystemId) {
        if (!systemProps.containsKey(gameSystemId)) {
            MutableLiveData<List<Property>> items = new MutableLiveData<>();
            systemProps.put(gameSystemId, items);
            loadItems(gameSystemId);
        }
        MutableLiveData<List<Property>> items = systemProps.get(gameSystemId);
        // todo: find better approach
        Property oldItem = propRepository.getProperty(gameSystemId ,itemId);
        propRepository.editProperty(gameSystemId, itemId, new Property(oldItem.getId(), oldItem.getName(), oldItem.getDeleted(), oldItem.getModifiers(), oldItem.getConstraints()));
        Property editedItem = propRepository.getProperty(gameSystemId, itemId);

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
        PropertyRepository itemRepository = new PlugPropertyRepository();
        systemProps.get(gameSystemId).setValue(itemRepository.getProperties(gameSystemId));
    }
}
