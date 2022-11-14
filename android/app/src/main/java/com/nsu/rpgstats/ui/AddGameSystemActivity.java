package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.databinding.ActivityAddGameSystemBinding;
import com.nsu.rpgstats.ui.gamesystems.AddGameActivityResultCallback;

public class AddGameSystemActivity extends Activity {
    private ActivityAddGameSystemBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddGameSystemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText nameEdit = binding.newGameSystemNameEdit;
        EditText descriptionEdit = binding.newGameSystemDescriptionEdit;
        Button addGameButton = binding.addGameSystemButton;
        addGameButton.setOnClickListener(view -> {
            String gameName = nameEdit.getText().toString();
            String description = descriptionEdit.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(AddGameActivityResultCallback.GAME_SYSTEM_NAME_EXTRA, gameName);
            intent.putExtra(AddGameActivityResultCallback.GAME_SYSTEM_DESCRIPTION_EXTRA, description);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
