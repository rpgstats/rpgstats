package com.nsu.rpgstats.viewmodel.user;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.nsu.rpgstats.data.Result;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.entities.user.SignUpUserInfo;
import com.nsu.rpgstats.ui.user.AuthListener;
import com.nsu.rpgstats.viewmodel.user.validators.EmailValidator;
import com.nsu.rpgstats.viewmodel.user.validators.LoginValidator;
import com.nsu.rpgstats.viewmodel.user.validators.PasswordValidator;

public class SignUpModelView extends ViewModel {
    private static final String TAG = SignInModelView.class.getSimpleName();

    private String login;
    private String password;
    private String repeatPassword;
    private String email;

    private UserRepository userRepository;

    private final AuthListener mListener;
    private final LoginValidator loginValidator;
    private final PasswordValidator passwordValidator;
    private final EmailValidator emailValidator;

    public SignUpModelView(UserRepository mUserRepository, AuthListener mListener) {
        this.userRepository = mUserRepository;
        this.mListener = mListener;
        this.loginValidator = new LoginValidator();
        this.passwordValidator = new PasswordValidator();
        this.emailValidator = new EmailValidator();
    }

    public void onSignUpClick() {
        if (!isInputValid()) {
            return;
        }
        userRepository.signUpUser(new SignUpUserInfo(login, password, email),
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
        if (!loginValidator.isValid(login)) {
            mListener.onMessage(loginValidator.getErrorString());
            return false;
        }
        if (!password.equals(repeatPassword)) {
            mListener.onMessage("Passwords are different");
            return false;
        }
        if (!passwordValidator.isValid(password)) {
            mListener.onMessage(passwordValidator.getErrorString());
            return false;
        }
        if (!emailValidator.isValid(email)) {
            mListener.onMessage(emailValidator.getErrorString());
            return false;
        }
        return true;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
