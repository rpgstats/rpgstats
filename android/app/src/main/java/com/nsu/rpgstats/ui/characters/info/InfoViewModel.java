package com.nsu.rpgstats.ui.characters.info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.Attribute;
import com.nsu.rpgstats.entities.Parameter;

import java.util.ArrayList;
import java.util.List;


public class InfoViewModel extends ViewModel {
    private MutableLiveData<Boolean> isChanged;
    private MutableLiveData<List<Attribute>> attributeList;

    public InfoViewModel() {
        reInit();
    }

    public void reInit() {
        isChanged = new MutableLiveData<>();
        isChanged.setValue(false);
        attributeList = new MutableLiveData<>();
        attributeList.setValue(new ArrayList<>());
    }

    public void loadAttributes(int charId) {
        //todo loadAttributes
    }

    public LiveData<List<Attribute>> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList.setValue(attributeList);
    }

    public LiveData<Boolean> getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(boolean isChanged) {
        this.isChanged.setValue(isChanged);
    }


}
