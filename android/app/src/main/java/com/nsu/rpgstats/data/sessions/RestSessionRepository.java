package com.nsu.rpgstats.data.sessions;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.network.services.SessionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestSessionRepository implements SessionsRepository {
    private static final String TAG = RestSessionRepository.class.getSimpleName();
    private final SessionService sessionService;

    public RestSessionRepository(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void getSessions(RepositoryCallback<List<Session>> callback) {
        sessionService.getSessions().enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                Log.d(TAG, "Response: " + response);
                if (response.body() != null) {
                    callback.onComplete(new Result.Success<>(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<Session>> call, Throwable t) {
                callback.onComplete(new Result.Error<>(null));
            }
        });
    }

    @Override
    public void getSession(int sessionId, RepositoryCallback<com.nsu.rpgstats.entities.Session> callback) {

    }

    @Override
    public void addSession(String sessionName, int maximumPlayers, int gameSystemId, RepositoryCallback<com.nsu.rpgstats.entities.Session> callback) {

    }

    @Override
    public void deleteSession(int sessionId, RepositoryCallback<String> callback) {

    }
}
