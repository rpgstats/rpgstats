package com.nsu.rpgstats.data.user;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.entities.user.AuthToken;
import com.nsu.rpgstats.entities.user.SignInUserInfo;
import com.nsu.rpgstats.entities.user.SignUpUserInfo;

public class PlugUserRepository implements UserRepository {
    @Override
    public void signInUser(SignInUserInfo signInUserInfo, RepositoryCallback<AuthToken> callback) {
        if (signInUserInfo.getUsername().equals("1337")) {
            callback.onComplete(new Result.Success<>(new AuthToken("12345678")));
        } else {
            callback.onComplete(new Result.Error<>(new Throwable("username != 1337")));
        }
    }

    @Override
    public void signUpUser(SignUpUserInfo signUpUserInfo, RepositoryCallback<String> callback) {
        if (signUpUserInfo.getUsername().equals("1337")) {
            callback.onComplete(new Result.Success<>("success sign up"));
        } else {
            callback.onComplete(new Result.Error<>(new Throwable("username = 1337")));
        }
    }
}
