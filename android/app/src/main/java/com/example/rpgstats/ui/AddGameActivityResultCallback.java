package com.example.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.example.rpgstats.entities.GameSystem;
import com.example.rpgstats.viewmodel.GameSystemsViewModel;

public class AddGameActivityResultCallback implements ActivityResultCallback<ActivityResult> {
    public final static String GAME_SYSTEM_EXTRA = "new_game_system_name";
    private final GameSystemsViewModel gameSystemsViewModel;

    public AddGameActivityResultCallback(GameSystemsViewModel gameSystemsViewModel) {
        this.gameSystemsViewModel = gameSystemsViewModel;
    }

    @Override
    public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                assert data != null;
                String gameSystemName = data.getStringExtra(GAME_SYSTEM_EXTRA);
                Log.e("ON CREATION RESULT","gameSystemName = ");
                gameSystemsViewModel.addGameSystem(new GameSystem(1, gameSystemName, "1.1.2001"));
                Log.e("ON CREATION RESULT","add game system to view model");
                // message: successfully created
            }
    }
}
