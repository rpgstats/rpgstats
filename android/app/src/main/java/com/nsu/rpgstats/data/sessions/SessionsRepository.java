package com.nsu.rpgstats.data.sessions;

import com.nsu.rpgstats.entities.Session;

import java.util.List;

public interface SessionsRepository {
    List<Session> getSessions();
    Session getSession(int sessionId);
}
