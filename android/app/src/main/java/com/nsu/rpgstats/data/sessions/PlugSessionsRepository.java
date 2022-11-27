package com.nsu.rpgstats.data.sessions;

import com.nsu.rpgstats.entities.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlugSessionsRepository implements SessionsRepository {
    @Override
    public List<Session> getSessions() {
        List<Session> sessions = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            sessions.add(new Session(i, "session " + i, new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date())));
        }
        return sessions;
    }

    @Override
    public Session getSession(int sessionId) {
        return new Session(sessionId, "19205 play after krpo", "natasha",
                "just fun", 10, 15, "meme police", new ArrayList<>(), "01.01.01");
    }

}
