package com.nsu.rpgstats.ui.gamesystems;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.nsu.rpgstats.databinding.ActivityMainBinding;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AddGameActivityResultCallback implements ActivityResultCallback<ActivityResult> {
    public final static String GAME_SYSTEM_NAME_EXTRA = "new_game_system_name";
    public final static String GAME_SYSTEM_DESCRIPTION_EXTRA = "new_game_system_description";

    private final GameSystemsViewModel gameSystemsViewModel;
    private final ActivityMainBinding binding;
    private final AppCompatActivity activity;

    public AddGameActivityResultCallback(GameSystemsViewModel gameSystemsViewModel, AppCompatActivity activity, ActivityMainBinding binding) {
        this.gameSystemsViewModel = gameSystemsViewModel;
        this.binding = binding;
        this.activity = activity;
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            assert data != null;
            String gameSystemName = data.getStringExtra(GAME_SYSTEM_NAME_EXTRA);
            String gameSystemDescription = data.getStringExtra(GAME_SYSTEM_DESCRIPTION_EXTRA);

            gameSystemsViewModel.getCreationResult().observe(activity,
                    creationGameResult -> handleCreatingGameResult(creationGameResult.getErrorText()));
            gameSystemsViewModel.addGameSystem(gameSystemName, gameSystemDescription);

        }
    }

    private void handleCreatingGameResult(String reason) {
        Snackbar errorSnackbar = Snackbar.make(binding.mainLayout, reason, Snackbar.LENGTH_SHORT);
        errorSnackbar.show();
    }
}
