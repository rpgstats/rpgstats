package com.nsu.rpgstats.data.sessions;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.network.services.SessionService;

import java.util.List;

public class RestSessionRepository implements SessionsRepository {
    private final SessionService sessionService;

    public RestSessionRepository(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void getSessions(RepositoryCallback<List<Session>> callback) {

    }

    @Override
    public void getSession(int sessionId, RepositoryCallback<Session> callback) {

    }

    @Override
    public void addSession(String sessionName, int maximumPlayers, int gameSystemId, RepositoryCallback<Session> callback) {

    }

    @Override
    public void deleteSession(int sessionId, RepositoryCallback<String> callback) {

    }
}
