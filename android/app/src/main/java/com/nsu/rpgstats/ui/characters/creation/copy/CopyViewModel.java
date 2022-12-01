package com.nsu.rpgstats.ui.characters.creation.copy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.entities.Version;

import java.util.List;

public class CopyViewModel extends ViewModel {
    private MutableLiveData<List<Version>> versionList;

    public CopyViewModel() {
        reInit();
    }

    public void reInit() {
        this.versionList = new MutableLiveData<List<Version>>();
    }

    public void loadData(int CharId) {
        //todo loadData
    }

    public LiveData<List<Version>> getVersionList() {
        return versionList;
    }
}