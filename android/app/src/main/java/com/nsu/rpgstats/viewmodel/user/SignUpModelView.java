package com.nsu.rpgstats.viewmodel.user;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.entities.user.SignUpUserInfo;
import com.nsu.rpgstats.ui.user.AuthListener;

public class SignUpModelView extends ViewModel {
    private static final String TAG = SignInModelView.class.getSimpleName();

    private String mLogin;
    private String mPassword;
    private String mEmail;

    private UserRepository mUserRepository;

    private AuthListener mListener;

    public SignUpModelView(AuthListener mListener) {
        this.mListener = mListener;
    }

    public void onSignInClick() {
        if (!isInputValid()) {
            return;
        }
        mUserRepository.signUpUser(new SignUpUserInfo(mLogin, mPassword, mEmail),
                result -> {
                    if (result instanceof Result.Success) {
                        String answer = ((Result.Success<String>) result).data;
                        Log.d(TAG, "get answer: " + answer);
                        mListener.onSuccessAuth();
                    } else if (result instanceof Result.Error) {
                        Log.d(TAG, "can not get token, reason: "
                                + ((Result.Error<String>) result).throwable.getMessage());
                        mListener.onMessage("Wrong password or no network? :0");
                    }
                });
    }

    private boolean isInputValid() {
        return false;
    }
}
