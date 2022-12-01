package com.nsu.rpgstats.ui.characters.creation.file;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.entities.Version;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class FileCreationViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> filename;

    public LiveData<String> getFilename() {
        return filename;
    }

    public FileCreationViewModel() {
        reInit();
    }

    public void reInit() {
        filename = new MutableLiveData<>();
    }

    public void setFile(String filename) {
        this.filename.setValue(filename);
    }

    public Character getCharacter() throws FileNotFoundException {
        FileInputStream file = new FileInputStream(filename.getValue());
        return new Character(0, "kok", "kok", 0, 0, 0); //todo read from file
    }
}