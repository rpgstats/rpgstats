package com.example.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.rpgstats.R;

public class GameSystemInfoActivity extends Activity {



    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    public GameSystemInfoActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_system_info);
        String id = getIntent().getStringExtra("id");
        TextView textView = findViewById(R.id.gamesystemid);
        textView.setText(id);

        setOnClickCreateActivity(findViewById(R.id.tags_button), TagsActivity.class);
        setOnClickCreateActivity(findViewById(R.id.npc_button), NpcActivity.class);
        setOnClickCreateActivity(findViewById(R.id.items_button), ItemsActivity.class);
        setOnClickCreateActivity(findViewById(R.id.parameters_button), ParametersActivity.class);
        setOnClickCreateActivity(findViewById(R.id.properties_button), PropertiesActivity.class);
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
