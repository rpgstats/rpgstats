package com.nsu.rpgstats.data.user;

import android.util.Log;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.gamesystems.RestGameSystemsRepository;
import com.nsu.rpgstats.entities.user.AuthInfo;
import com.nsu.rpgstats.entities.user.SignInUserInfo;
import com.nsu.rpgstats.entities.user.SignUpUserInfo;
import com.nsu.rpgstats.network.dto.LoginRequest;
import com.nsu.rpgstats.network.dto.LoginResponse;
import com.nsu.rpgstats.network.dto.MessageResponse;
import com.nsu.rpgstats.network.dto.SignupRequest;
import com.nsu.rpgstats.network.services.AuthService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestUserRepository implements UserRepository {
    private static final String TAG = RestGameSystemsRepository.class.getSimpleName();
    private final AuthService service;

    public RestUserRepository(AuthService service) {
        this.service = service;
    }

    @Override
    public void signInUser(SignInUserInfo signInUserInfo, RepositoryCallback<AuthInfo> callback) {
        service.login(new LoginRequest(signInUserInfo.getUsername(), signInUserInfo.getPassword()))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.isSuccessful() && response.body() != null) {
                            AuthInfo authToken = new AuthInfo(response.body().getAuthToken(), response.body().getId());
                            callback.onComplete(new Result.Success<>(authToken));
                        } else {
                            callback.onComplete(new Result.Error<>(new Throwable("Wrong email or password")));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }

    @Override
    public void signUpUser(SignUpUserInfo signUpUserInfo, RepositoryCallback<String> callback) {
        service.signup(new SignupRequest(signUpUserInfo.getUsername(), signUpUserInfo.getPassword(),signUpUserInfo.getEmail()))
                .enqueue(new Callback<MessageResponse>() {
                    @Override
                    public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                        Log.d(TAG, "Response: " + response);
                        if (response.body() != null) {
                            callback.onComplete(new Result.Success<>(response.body().toString()));
                        }
                    }

                    @Override
                    public void onFailure(Call<MessageResponse> call, Throwable t) {
                        callback.onComplete(new Result.Error<>(t));
                    }
                });
    }
}
