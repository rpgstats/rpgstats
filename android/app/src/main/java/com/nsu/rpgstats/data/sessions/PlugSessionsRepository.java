package com.nsu.rpgstats.data.sessions;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.entities.SessionCharacter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlugSessionsRepository implements SessionsRepository {

    private int i = 5;

    private List<Session> sessions;
    public PlugSessionsRepository() {
        sessions = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            sessions.add(new Session(j, "session " + j, new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date())));
        }
    }

    @Override
    public void getSessions(RepositoryCallback<List<Session>> callback) {
       callback.onComplete(new Result.Success<>(sessions));
    }

    @Override
    public void getSession(int sessionId, RepositoryCallback<Session> callback) {
        List<SessionCharacter> sessionCharacters = new ArrayList<>();
        for (int m = 0; m < 100; m++) {
            sessionCharacters.add(new SessionCharacter("character " + m, "hikkari"));
        }
        callback.onComplete(new Result.Success<>(new Session(sessionId, "19205 play after krpo", "natasha",
                "just fun", 10, 15, "meme police", sessionCharacters, "01.01.01")));
    }

    @Override
    public void addSession(String sessionName, int maximumPlayers, int gameSystemId, RepositoryCallback<Session> callback) {
        i += 1;
        Session s = new Session();
        s.setId(i);
        s.setName(sessionName);
        s.setMaximumPlayers(maximumPlayers);
        sessions.add(s);
        callback.onComplete(new Result.Success<>(s));
    }

    @Override
    public void deleteSession(int sessionId, RepositoryCallback<String> callback) {
        for (Session s : sessions) {
            if (s.getId() == sessionId) {
                callback.onComplete(new Result.Success<>("Successfully deleted"));
                sessions.remove(s);
                return;
            }
        }
        callback.onComplete(new Result.Error<>(new Throwable("Id not found")));
    }
}
