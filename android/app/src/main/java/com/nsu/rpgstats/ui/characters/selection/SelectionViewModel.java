package com.nsu.rpgstats.ui.characters.selection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.AppContainer;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.Attribute;
import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.entities.Version;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class SelectionViewModel extends ViewModel {
    private MutableLiveData<List<Character>> characterList;
    private Character character;
    private Context context;
    private int userId;

    public SelectionViewModel() {
        super();
        context = null;
        Log.d("kok", "");
        userId = -1;
        characterList = new MutableLiveData<>();
    }

    public void addCharacter(String name, Character oldCharacter) {
        int charId = 0;
        for (Character character : characterList.getValue()) {
            if (character.getId() >= charId) {
                charId = character.getId() + 1;
            }
        }

        Character newCharacter = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(oldCharacter);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            newCharacter = (Character) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        newCharacter.setBackground(oldCharacter.getBackground());
        newCharacter.setIcon(oldCharacter.getIcon());
        newCharacter.setName(name);
        newCharacter.setId(charId);

        characterList.getValue().add(newCharacter);//todo repo
        characterList.setValue(characterList.getValue());
        saveCharacters(context);
    }

    public Character getCharacterById(int id) {
        for (Character character: characterList.getValue()) { //todo repo
            if (character.getId() == id) {
                return character;
            }
        }
        return null;
    }

    public void loadData(int UserId, Context context) {
        this.context = context;
        userId = UserId;
        if (characterList.getValue() == null) {
            try (ObjectInputStream file = new ObjectInputStream(new FileInputStream(new File(context.getExternalFilesDir(null), "Characters" + userId + ".obj")))) {
                characterList.setValue((List<Character>) file.readObject());
            } catch (IOException | ClassNotFoundException e) {
                characterList.setValue(new ArrayList<>());
                e.printStackTrace();
            }
        }
    }

    public LiveData<List<Character>> getCharacterList() {
        return characterList;
    }

    public void editCharacter(int id, Character oldCharacter, int position) {
        characterList.getValue().remove(position);
        Character newCharacter = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ObjectOutputStream ous = new ObjectOutputStream(baos);
            ous.writeObject(oldCharacter);
            ous.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            newCharacter = (Character) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        newCharacter.setId(id);
        newCharacter.setBackground(oldCharacter.getBackground());
        newCharacter.setIcon(oldCharacter.getIcon());
        characterList.getValue().add(position, newCharacter);//todo repo
        characterList.setValue(characterList.getValue());
        saveCharacters(context);
    }

    public void deleteCharacter(int position) {
        characterList.getValue().remove(position);//todo repo
        characterList.setValue(characterList.getValue());
        saveCharacters(context);
    }

    public void saveSlots(List<Slot> value, int id, int position) {
        characterList.getValue().get(position).setSlotList(value);//todo repo
        characterList.setValue(characterList.getValue());
        saveCharacters(context);
    }

    public void downloadCharacter(int position, Context context) {
        Character character = characterList.getValue().get(position);
        try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(new File(context.getExternalFilesDir(null), "Character_" + character.getName() + "_" + character.hashCode() + ".obj")))) {
            file.writeObject(character);
            Toast.makeText(context,"file created: " + "Character_" + character.getName() + "_" + character.hashCode() + ".obj", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCharacters(Context context) {
        try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(new File(context.getExternalFilesDir(null), "Characters" + userId + ".obj")))) {
            file.writeObject(characterList.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onCleared() {
        try (ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(new File(context.getExternalFilesDir(null), "Characters" + userId + ".obj")))) {
            file.writeObject(characterList.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onCleared();
    }
}