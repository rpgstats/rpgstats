package com.nsu.rpgstats.ui.characters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WindowViewModel extends ViewModel {
    private MutableLiveData<Boolean> isShow;

    public WindowViewModel() {
        this.isShow = new MutableLiveData<>();
        this.isShow.setValue(false);
    }

    public void setIsShow(boolean isShow) {
        this.isShow.setValue(isShow);
    }

    public LiveData<Boolean> getIsShow(){
        return isShow;
    }
}
