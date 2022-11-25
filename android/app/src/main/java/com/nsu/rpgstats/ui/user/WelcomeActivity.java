package com.nsu.rpgstats.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends Activity {
    private static final String TAG = WelcomeActivity.class.getSimpleName();
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signInButton.setOnClickListener(view -> {
            Log.d(TAG, "Sign in button was pressed. Starting sign in activity");
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        });
        binding.signUpButton.setOnClickListener(view -> {
            Log.d(TAG, "Sign up button was pressed. Starting sign up activity");
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
