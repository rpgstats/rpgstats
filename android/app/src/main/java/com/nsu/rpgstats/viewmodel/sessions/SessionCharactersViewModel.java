package com.nsu.rpgstats.viewmodel.sessions;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.entities.SessionCharacter;

import java.util.List;

public class SessionCharactersViewModel extends AndroidViewModel {
    private static final String TAG = SessionCharactersViewModel.class.getSimpleName();
    private final SessionsRepository repository;
    private MutableLiveData<List<SessionCharacter>> sessionCharacters;

    public SessionCharactersViewModel(@NonNull Application application) {
        super(application);
        repository = ((RpgstatsApplication)getApplication()).appContainer.sessionsRepository;
    }
    public LiveData<List<SessionCharacter>> getSessionCharacters(int sessionId) {
        if (sessionCharacters == null) {
            sessionCharacters = new MutableLiveData<>();
        }
        loadSessionCharacters(sessionId);
        return sessionCharacters;
    }

    public void loadSessionCharacters(int sessionId) {
        repository.getSessionCharacters( sessionId, (res) -> {
            if (res instanceof Result.Success) {
                sessionCharacters.setValue(((Result.Success<List<SessionCharacter>>) res).data);
            } else if (res instanceof Result.Error){
                Log.e(TAG, "Can not load sessions ", ((Result.Error<List<SessionCharacter>>) res).throwable);
            }
        });

    }
}
