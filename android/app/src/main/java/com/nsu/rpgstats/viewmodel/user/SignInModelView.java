package com.nsu.rpgstats.viewmodel.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.entities.user.AuthToken;
import com.nsu.rpgstats.entities.user.SignInUserInfo;
import com.nsu.rpgstats.ui.user.AuthListener;

public class SignInModelView extends ViewModel {
    private static final String TAG = SignInModelView.class.getSimpleName();

    private String mLogin;
    private String mPassword;

    private UserRepository mUserRepository;

    private final AuthListener mListener;


    public SignInModelView(UserRepository userRepository, @NonNull AuthListener authListener) {
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
                        String token = ((Result.Success<AuthToken>) result).data.getAuthToken();
                        Log.d(TAG, "get token: " + token);
                        mListener.onSuccessAuth();
                    } else if (result instanceof Result.Error) {
                        Log.d(TAG, "can not get token, reason: "
                                + ((Result.Error<AuthToken>) result).throwable.getMessage());
                        mListener.onMessage("Error: " + ((Result.Error<AuthToken>) result).throwable.getMessage());
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
