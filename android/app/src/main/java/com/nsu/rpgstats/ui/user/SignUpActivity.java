package com.nsu.rpgstats.ui.user;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.user.UserRepository;
import com.nsu.rpgstats.databinding.ActivitySignupBinding;
import com.nsu.rpgstats.viewmodel.user.SignInModelView;
import com.nsu.rpgstats.viewmodel.user.SignUpModelView;

public class SignUpActivity extends AuthActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private ActivitySignupBinding binding;

    private SignUpModelView signUpModelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserRepository userRepository = ((RpgstatsApplication)getApplication()).appContainer.userRepository;
        signUpModelView = new SignUpModelView(getApplication(), userRepository, this);

        EditText loginEdit = binding.signupLoginEdit;
        EditText passwordEdit = binding.signupPasswordEdit;
        EditText repeatPasswordEdit = binding.signupRepeatPasswordEdit;
        EditText emailEdit = binding.signupEmailEdit;

        binding.signupButton.setOnClickListener(view -> {
            Log.d(TAG, "Was pressed signup button");
            signUpModelView.setLogin(loginEdit.getText().toString());
            signUpModelView.setPassword(passwordEdit.getText().toString());
            signUpModelView.setRepeatPassword(repeatPasswordEdit.getText().toString());
            signUpModelView.setEmail(emailEdit.getText().toString());
            signUpModelView.onSignUpClick();
        });
    }
}
