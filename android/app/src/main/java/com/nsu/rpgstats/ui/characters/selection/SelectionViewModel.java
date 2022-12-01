package com.nsu.rpgstats.ui.characters.selection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.entities.Slot;

import java.util.List;

public class SelectionViewModel extends ViewModel {
    private MutableLiveData<List<Character>> characterList;

    public SelectionViewModel() {
        super();
        characterList = new MutableLiveData<>();
    }

    public void addCharacter(String name, Character oldCharacter) {
        characterList.getValue().add(new Character(0, name, oldCharacter.getName(),
                oldCharacter.getGameSystemId(), oldCharacter.getSessionId(), oldCharacter.getUserId()));//todo repo
        characterList.setValue(characterList.getValue());
    }

    public Character getCharacterById(int id) {
        for (Character character: characterList.getValue()) { //todo repo
            if (character.getId() == id) {
                return character;
            }
        }
        return null;
    }

    public void loadData(int UserId) {
        //todo loadData;
    }

    public LiveData<List<Character>> getCharacterList() {
        return characterList;
    }

    public void editCharacter(int id, Character character, int position) {
        //todo
    }

    public void deleteCharacter(int position) {
        //todo
    }

    public void saveSlots(List<Slot> value, int id, int position) {
        //todo
    }
}