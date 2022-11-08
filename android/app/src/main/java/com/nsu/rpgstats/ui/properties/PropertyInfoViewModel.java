package com.nsu.rpgstats.ui.properties;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.entities.Property;

public class PropertyInfoViewModel extends ViewModel {
    private Integer propertyId;
    private PropertyRepository repository;
    private MutableLiveData<Property> property;
    private Integer gsId;

    public PropertyInfoViewModel(Integer gsId, Integer propertyId, PropertyRepository repository) {
        this.propertyId = propertyId;
        this.repository = repository;
        this.gsId = gsId;
    }

    public LiveData<Property> getPropertyInfo() {
        if (property == null) {
            property = new MutableLiveData<>();
            loadProperty();
        }
        return property;
    }

    private void loadProperty() {
        property.setValue(repository.getProperty(gsId, propertyId));
    }
}
