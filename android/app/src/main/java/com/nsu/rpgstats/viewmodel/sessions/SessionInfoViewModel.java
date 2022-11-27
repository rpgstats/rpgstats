package com.nsu.rpgstats.viewmodel.sessions;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.entities.Session;

public class SessionInfoViewModel extends AndroidViewModel {
    private final SessionsRepository repository;

    public SessionInfoViewModel(@NonNull Application application) {
        super(application);
        repository = ((RpgstatsApplication)getApplication()).appContainer.sessionsRepository;
    }

    public Session getSession(Integer id) {
        return repository.getSession(id);
    }
}
