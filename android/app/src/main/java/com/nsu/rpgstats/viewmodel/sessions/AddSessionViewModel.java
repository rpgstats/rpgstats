package com.nsu.rpgstats.viewmodel.sessions;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.sessions.SessionsRepository;

public class AddSessionViewModel extends AndroidViewModel {

    private SessionsRepository repository;

    public AddSessionViewModel(@NonNull Application application) {
        super(application);
        repository = ((RpgstatsApplication)getApplication()).appContainer.sessionsRepository;
    }

    public void onAddSessionButtonClick(String sessionName, String maximumPlayers) {
        repository.addSession(sessionName,
                Integer.parseInt(maximumPlayers),
                0);
    }
}
