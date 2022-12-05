package com.nsu.rpgstats.ui.characters;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BackgroundViewModel extends ViewModel {
    MutableLiveData<Bitmap> icon;
    MutableLiveData<Bitmap> background;
    MutableLiveData<String> name;

    public BackgroundViewModel() {
        reInit();
    }

    private void reInit() {
        icon = new MutableLiveData<>();
        background = new MutableLiveData<>();
        name = new MutableLiveData<>();
    }

    public LiveData<Bitmap> getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon.setValue(icon);
    }

    public LiveData<Bitmap> getBackground() {
        return background;
    }

    public void setBackground(Bitmap background) {
        this.background.setValue(background);
    }

    public LiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
}
