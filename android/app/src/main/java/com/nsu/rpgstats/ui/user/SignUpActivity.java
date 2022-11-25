package com.nsu.rpgstats.ui.user;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.databinding.ActivitySignupBinding;
import com.nsu.rpgstats.viewmodel.user.SignUpModelView;

public class SignUpActivity extends Activity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private ActivitySignupBinding binding;

    private SignUpModelView signUpModelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupButton.setOnClickListener(view -> {
        });
    }
}
