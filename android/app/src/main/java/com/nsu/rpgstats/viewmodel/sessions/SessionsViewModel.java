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
        repository.addSession(sessionName, maxNumbers, 9, res -> {
            if (res instanceof Result.Success) {
                Log.d(TAG, "Successfully add session with name " + sessionName);
            }
        });

        // refresh list
        // after update
        loadSessions();
    }

    public void loadSessions() {
        repository.getSessions((res) -> {
            if (res instanceof Result.Success) {
                sessions.setValue(((Result.Success<List<Session>>) res).data);
            } else if (res instanceof Result.Error){
                Log.e(TAG, "Can not load sessions ", ((Result.Error<List<Session>>) res).throwable);
            }
        });

    }

    public void deleteSession(int sessionId) {
        repository.deleteSession(sessionId, (res) -> {
            if (res instanceof Result.Success) {
                Log.d(TAG, "Successfully delete system with id " + sessionId);
                loadSessions();
            } else if (res instanceof Result.Error) {
                Log.d(TAG, "Can not delete session with id " + sessionId);
            }
        });
    }
}
