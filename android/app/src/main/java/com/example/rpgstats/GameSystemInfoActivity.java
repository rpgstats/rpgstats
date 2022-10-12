package com.example.rpgstats;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class GameSystemInfoActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_system_info);
        String id = getIntent().getStringExtra("id");
        TextView textView = findViewById(R.id.gamesystemid);
        textView.setText(id);
    }
}
