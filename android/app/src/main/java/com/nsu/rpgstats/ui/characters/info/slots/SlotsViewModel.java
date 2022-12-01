package com.nsu.rpgstats.ui.characters.info.slots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.databinding.SlotItemBinding;
import com.nsu.rpgstats.entities.Slot;

import java.util.ArrayList;
import java.util.List;

public class SlotsViewModel extends ViewModel {
    private MutableLiveData<List<Slot>> slotList;
    private MutableLiveData<Boolean> isChanged;

    public SlotsViewModel() {
        reInit();
    }

    public void reInit() {
        slotList = new MutableLiveData<>();
        slotList.setValue(new ArrayList<>());
        isChanged = new MutableLiveData<>();
        isChanged.setValue(false);
    }

    public LiveData<List<Slot>> getSlotList() {
        return slotList;
    }

    public void setSlotList(List<Slot> slotList) {
        this.slotList.setValue(slotList);
    }

    public LiveData<Boolean> getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(Boolean isChanged) {
        this.isChanged.setValue(isChanged);
    }

    public void loadImage(SlotItemBinding bindingNewItem, String iconUrl) {
        //todo loadImage
    }
}