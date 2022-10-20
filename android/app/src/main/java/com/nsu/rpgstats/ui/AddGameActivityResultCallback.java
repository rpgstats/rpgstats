package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityMainBinding;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;
import com.google.android.material.snackbar.Snackbar;

public class AddGameActivityResultCallback implements ActivityResultCallback<ActivityResult> {
    public final static String GAME_SYSTEM_EXTRA = "new_game_system_name";
    private final GameSystemsViewModel gameSystemsViewModel;
    private final ActivityMainBinding binding;

    public AddGameActivityResultCallback(GameSystemsViewModel gameSystemsViewModel, Activity activity, ActivityMainBinding binding) {
        this.gameSystemsViewModel = gameSystemsViewModel;
        this.binding = binding;
    }

    @Override
    public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                assert data != null;
                String gameSystemName = data.getStringExtra(GAME_SYSTEM_EXTRA);
                // todo: move this logic to viewmodel
                if ("".equals(gameSystemName)) {
                    handleCreatingGameErrror();
                    return;
                }
                gameSystemsViewModel.addGameSystem(gameSystemName);
                // message: successfully created
            }
    }

    private void handleCreatingGameErrror() {
        Snackbar errorSnackbar = Snackbar.make(binding.mainLayout, R.string.cannot_create_game, Snackbar.LENGTH_SHORT);
        errorSnackbar.show();
    }
}
