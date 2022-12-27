package com.nsu.rpgstats.data.sessions;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.network.dto.SessionRequest;
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
                callback.onComplete(new Result.Error<>(t));
            }
        });
    }

    @Override
    public void getSession(int sessionId, RepositoryCallback<com.nsu.rpgstats.entities.Session> callback) {
        sessionService.getSessions().enqueue(new Callback<List<Session>>() {
            @Override
            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
                Log.d(TAG, "Response: " + response);
                if (response.body() != null) {

                    for (Session s : response.body()) {
                        if (s.getId() == sessionId) {
                            callback.onComplete(new Result.Success<>(s));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Session>> call, Throwable t) {
                callback.onComplete(new Result.Error<>(t));
            }
        });
    }

    @Override
    public void addSession(String sessionName, int maximumPlayers, int gameSystemId, RepositoryCallback<com.nsu.rpgstats.entities.Session> callback) {
        sessionService.addSession(new SessionRequest(sessionName, "description", maximumPlayers, gameSystemId, true)).enqueue(
                new Callback<Session>() {
                    @Override
                    public void onResponse(Call<Session> call, Response<Session> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                                    callback.onComplete(new Result.Success<>(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Session> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                }
        );
    }

    @Override
    public void deleteSession(int sessionId, RepositoryCallback<String> callback) {
        sessionService.deleteSession(sessionId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "Response: " + response);
                if (response.body() != null) {
                    callback.onComplete(new Result.Success<>(response.body()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.onComplete(new Result.Error<>(t));
            }
        });
    }
}
