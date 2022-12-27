package com.nsu.rpgstats.viewmodel.user;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.entities.user.AuthInfo;
import com.nsu.rpgstats.entities.user.SignInUserInfo;
import com.nsu.rpgstats.entities.user.User;
import com.nsu.rpgstats.ui.user.AuthListener;

public class SignInModelView extends AndroidViewModel {
    private static final String TAG = SignInModelView.class.getSimpleName();

    private String mLogin;
    private String mPassword;

    private final UserRepository mUserRepository;

    private final AuthListener mListener;


    public SignInModelView(Application app, UserRepository userRepository, @NonNull AuthListener authListener) {
        super(app);
        this.mUserRepository = userRepository;
        mListener = authListener;
    }

    public void onSignInClick() {
        if (mLogin.length() <= 0) {
            mListener.onMessage("Empty login!");
            return;
        }
        if (mPassword.length() <= 0) {
            mListener.onMessage("Empty password!");
            return;
        }

        mUserRepository.signInUser(new SignInUserInfo(mLogin, mPassword),
                result -> {
                    if (result instanceof Result.Success) {
                        AuthInfo authInfo =  ((Result.Success<AuthInfo>) result).data;
                        String token = authInfo.getAuthToken();
                        Log.d(TAG, "get token: " + token);
                        int ownerId =  authInfo.getOwnerId();
                        ((RpgstatsApplication) getApplication()).appContainer.currentUser =
                                new User(mLogin, ownerId);
                        ((RpgstatsApplication) getApplication()).appContainer.setToken(token);
                        mListener.onSuccessAuth();

                    } else if (result instanceof Result.Error) {
                        Log.d(TAG, "can not get token, reason: "
                                + ((Result.Error<AuthInfo>) result).throwable.getMessage());
                        mListener.onMessage("Error: " + ((Result.Error<AuthInfo>) result).throwable.getMessage());
                    }
                });
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
