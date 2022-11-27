package com.nsu.rpgstats.viewmodel.sessions;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.sessions.PlugSessionsRepository;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.entities.Session;

import java.util.List;

public class SessionsViewModel extends AndroidViewModel {
    private static final String TAG = SessionsViewModel.class.getSimpleName();
    private MutableLiveData<List<Session>> sessions;
    private SessionsRepository repository;

    public SessionsViewModel(@NonNull Application application) {
        super(application);
        repository = ((RpgstatsApplication)getApplication()).appContainer.sessionsRepository;
    }
    public LiveData<List<Session>> getSessions() {
        if (sessions == null) {
            sessions = new MutableLiveData<>();
        }
        loadSessions();
        return sessions;
    }

    public void addSession(String sessionName, int maxNumbers) {
        repository.addSession(sessionName, maxNumbers, 9);

        // refresh list
        // after update
        loadSessions();
    }

    private void loadSessions() {
        sessions.setValue(repository.getSessions());
    }
}
