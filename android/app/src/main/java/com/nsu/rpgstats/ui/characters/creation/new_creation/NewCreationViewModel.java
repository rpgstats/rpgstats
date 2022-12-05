package com.nsu.rpgstats.ui.characters.creation.new_creation;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.GameSystem;

public class NewCreationViewModel extends ViewModel {
    private MutableLiveData<String> imageFilename;
    private MutableLiveData<String> backgroundFilename;
    private MutableLiveData<GameSystem> gameSystem;
    private MutableLiveData<Uri> pathBackground;
    private MutableLiveData<Uri> pathIcon;

    public LiveData<Uri> getPathBackground() {
        return pathBackground;
    }

    public void setPathBackground(Uri pathBackground) {
        this.pathBackground.setValue(pathBackground);
    }

    public LiveData<Uri> getPathIcon() {
        return pathIcon;
    }

    public void setPathIcon(Uri pathIcon) {
        this.pathIcon.setValue(pathIcon);
    }


    public NewCreationViewModel() {
        reInit();
    }

    public void reInit() {
        imageFilename = new MutableLiveData<>();
        backgroundFilename = new MutableLiveData<>();
        gameSystem = new MutableLiveData<>();
        pathIcon = new MutableLiveData<>();
        pathBackground = new MutableLiveData<>();
    }

    public LiveData<GameSystem> getGameSystem() {
        return gameSystem;
    }

    public void setGameSystem(GameSystem gs) {
        gameSystem.setValue(gs);
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