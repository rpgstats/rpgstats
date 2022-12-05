package com.nsu.rpgstats.ui.characters.creation.file;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.entities.Version;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URI;
import java.util.List;

public class FileCreationViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> filename;
    private MutableLiveData<Uri> path;

    public LiveData<Uri> getPath() {
        return path;
    }

    public void setPath(Uri path) {
        this.path.setValue(path);
    }

    public FileCreationViewModel() {
        reInit();
    }

    public void reInit() {
        filename = new MutableLiveData<>();
        path = new MutableLiveData<>();
    }

    public LiveData<String> getFilename() {
        return filename;
    }

    public void setFile(String filename) {
        this.filename.setValue(filename);
    }

    public Character getCharacter(Context context) throws IOException {
        Character character = null;
        try (ObjectInputStream file = new ObjectInputStream(context.getContentResolver().openInputStream(path.getValue()))) {
            character = (Character) file.readObject();
            Toast.makeText(context, "Loaded file", Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(context, "failed to loaded file", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return character;
    }
}