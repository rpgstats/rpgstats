package com.nsu.rpgstats.data.sessions;

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
    public List<Session> getSessions() {
       return sessions;
    }

    @Override
    public Session getSession(int sessionId) {
        List<SessionCharacter> sessionCharacters = new ArrayList<>();
        for (int m = 0; m < 100; m++) {
            sessionCharacters.add(new SessionCharacter("character " + m, "hikkari"));
        }
        return new Session(sessionId, "19205 play after krpo", "natasha",
                "just fun", 10, 15, "meme police", sessionCharacters, "01.01.01");
    }

    @Override
    public Session addSession(String sessionName, int maximumPlayers, int gameSystemId) {
        i += 1;
        Session s = new Session();
        s.setId(i);
        s.setName(sessionName);
        s.setMaximumPlayers(maximumPlayers);
        sessions.add(s);
        return s;
    }
}
