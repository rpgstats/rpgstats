package com.nsu.rpgstats.ui.characters.windows.info.slots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.Slot;

public class SlotViewModel extends ViewModel {
    private MutableLiveData<Slot> slot;
    private MutableLiveData<Boolean> isChanged;

    public SlotViewModel() {
        reInit();
    }

    public void reInit() {
        slot = new MutableLiveData<>();
        isChanged = new MutableLiveData<>();
        isChanged.setValue(false);
    }


    public void setSlot(Slot slot){
        this.slot.setValue(slot);
    }

    public LiveData<Slot> getSlot() {
        return slot;
    }

    public void setIsChanged(Boolean isChanged){
        this.isChanged.setValue(isChanged);
    }

    public LiveData<Boolean> getIsChanged() {
        return isChanged;
    }

}
