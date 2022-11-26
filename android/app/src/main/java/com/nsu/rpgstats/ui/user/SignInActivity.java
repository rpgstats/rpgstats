package com.nsu.rpgstats.ui.user;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.databinding.ActivitySigninBinding;
import com.nsu.rpgstats.viewmodel.user.SignInModelView;

public class SignInActivity extends AuthActivity{
    private static final String TAG = SignInActivity.class.getSimpleName();
    private ActivitySigninBinding binding;

    private SignInModelView signInModelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserRepository userRepository = ((RpgstatsApplication)getApplication()).appContainer.userRepository;
        signInModelView = new SignInModelView(userRepository, this);

        EditText loginEdit = binding.signinLoginEditText;
        EditText passwordEdit = binding.signinPasswordEditText;
        binding.signinButton.setOnClickListener(view -> {
            String login = loginEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            signInModelView.setLogin(login);
            signInModelView.setPassword(password);
            signInModelView.onSignInClick();
        });
    }


}
