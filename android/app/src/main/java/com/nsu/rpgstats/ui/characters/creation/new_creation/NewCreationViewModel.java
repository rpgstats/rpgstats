package com.nsu.rpgstats.ui.characters.creation.new_creation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewCreationViewModel extends ViewModel {
    private MutableLiveData<String> imageFilename;
    private MutableLiveData<String> backgroundFilename;

    public NewCreationViewModel() {
        reInit();
    }

    public void reInit() {
        imageFilename = new MutableLiveData<>();
        backgroundFilename = new MutableLiveData<>();
    }

    public LiveData<String> getImageFilename() {
        return imageFilename;
    }

    public LiveData<String> getBackgroundFilename() {
        return backgroundFilename;
    }

    public void setImageFilename(String filename) {
        imageFilename.setValue(filename);
    }

    public void setBackgroundFilename(String filename) {
        backgroundFilename.setValue(filename);
    }
}