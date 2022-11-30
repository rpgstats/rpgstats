package com.nsu.rpgstats.data.sessions;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Session;

import java.util.List;

public interface SessionsRepository {
    void getSessions(RepositoryCallback<List<Session>> callback);
    void getSession(int sessionId, RepositoryCallback<Session> callback);
    void addSession(String sessionName, int maximumPlayers, int gameSystemId, RepositoryCallback<Session> callback);
    void deleteSession(int sessionId, RepositoryCallback<String> callback);
}
