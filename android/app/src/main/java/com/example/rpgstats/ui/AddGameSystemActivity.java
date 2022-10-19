package com.example.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.rpgstats.R;
import com.example.rpgstats.RpgstatsApplication;

public class AddGameSystemActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_system);

        EditText nameEdit = findViewById(R.id.new_game_system_name_edit);
        Button addGameButton = findViewById(R.id.add_game_system_button);
        addGameButton.setOnClickListener(view -> {
            String gameName = nameEdit.getText().toString();
            Intent intent = new Intent();
            intent.putExtra(AddGameActivityResultCallback.GAME_SYSTEM_EXTRA, gameName);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}
