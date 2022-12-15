package com.nsu.rpgstats.viewmodel.sessions;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nsu.rpgstats.AppContainer;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.entities.Session;

import java.util.Objects;

public class SessionInfoViewModel extends AndroidViewModel {
    private static final String TAG = SessionInfoViewModel.class.getSimpleName();

    private final SessionsRepository repository;

    private final MutableLiveData<Session> session;

    public SessionInfoViewModel(@NonNull Application application) {
        super(application);
        session = new MutableLiveData<>();
        repository = ((RpgstatsApplication) getApplication()).appContainer.sessionsRepository;
    }

    public void onActivityDidLoad() {
        // get session
        AppContainer appContainer = ((RpgstatsApplication) getApplication()).appContainer;
        repository.getSession(appContainer.currentSession.getId(), res -> {
            if (res instanceof Result.Success) {
                session.setValue(((Result.Success<Session>) res).data);
                appContainer.currentSession = session.getValue();
            }
        });
    }


    public MutableLiveData<Session> getCurrentSession() {
        return session;
    }

    public void onDeleteCurrentSession() {
        if (session.getValue() != null) {
            repository.deleteSession(session.getValue().getId(), (res) -> {
                if (res instanceof Result.Success) {
                    Log.d(TAG, "Successfully deleted");
                    session.setValue(null);
                }
            });
        }
    }

    public String getLinkForCurrentSession() {
        return  "/sessions/" + Objects.requireNonNull(session.getValue()).getId().toString() + "/join";
    }
}
