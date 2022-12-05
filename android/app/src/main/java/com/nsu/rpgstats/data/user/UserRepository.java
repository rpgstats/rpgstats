package com.nsu.rpgstats.data.user;

import com.nsu.rpgstats.data.RepositoryCallback;
import com.nsu.rpgstats.entities.user.AuthToken;
import com.nsu.rpgstats.entities.user.SignInUserInfo;
import com.nsu.rpgstats.entities.user.SignUpUserInfo;

public interface UserRepository {

    void signInUser(SignInUserInfo signInUserInfo, RepositoryCallback<AuthToken> callback);

    void signUpUser(SignUpUserInfo signUpUserInfo, RepositoryCallback<String> callback);
}
