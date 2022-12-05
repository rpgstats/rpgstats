package com.nsu.rpgstats.ui.characters.info.slots;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.SlotItemBinding;
import com.nsu.rpgstats.entities.Slot;

import java.util.ArrayList;
import java.util.List;

public class SlotsViewModel extends ViewModel {
    private MutableLiveData<List<Slot>> slotList;
    private MutableLiveData<Boolean> isChanged;
    private MutableLiveData<Boolean> toDelete;

    public SlotsViewModel() {
        reInit();
    }

    public void reInit() {
        slotList = new MutableLiveData<>();
        slotList.setValue(new ArrayList<>());
        isChanged = new MutableLiveData<>();
        isChanged.setValue(false);
        toDelete = new MutableLiveData<>();
        toDelete.setValue(false);
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

    public LiveData<Boolean> getToDelete() {
        return toDelete;
    }

    public void setToDelete(Boolean toDelete) {
        this.toDelete.setValue(toDelete);
    }

    public void loadImage(SlotItemBinding bindingNewItem, String iconUrl) {
        bindingNewItem.itemImage.setImageResource(R.drawable.control_items);//todo: load image
    }
}