package com.example.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.rpgstats.R;
import com.example.rpgstats.data.GameSystemsRepository;
import com.example.rpgstats.data.PlugGameSystemsRepository;
import com.example.rpgstats.databinding.ActivityGameSystemInfoBinding;
import com.example.rpgstats.entities.GameSystem;
import com.example.rpgstats.viewmodel.GameSystemInfoViewModel;

public class GameSystemInfoActivity extends Activity {
    private ActivityGameSystemInfoBinding binding;
    private GameSystem gameSystem;

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameSystemInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int id = Integer.parseInt(getIntent().getStringExtra("id"));
        GameSystemInfoViewModel viewModel = new GameSystemInfoViewModel(id, new PlugGameSystemsRepository());
        gameSystem = viewModel.getGameSystemInfo().getValue();

        setInfo();
        setListeners();
    }

    private void setInfo() {
        String description = gameSystem.getSystemName() + " " + gameSystem.getCreationDate();
        binding.descriptionText.setText(description);
        binding.ownerText.setText(gameSystem.getOwner());
        binding.gameSessionsNumberText.setText(String.valueOf(gameSystem.getGameSessionNumber()));
        binding.childGameSystemNumberText.setText(String.valueOf(gameSystem.getChildGameSystemNumber()));
        binding.itemsNumberText.setText(String.valueOf(gameSystem.getItemsNumber()));
        binding.npcNumberText.setText(String.valueOf(gameSystem.getNpcNumber()));
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.buttonPanel.tagsButton, TagsActivity.class);
        setOnClickCreateActivity(binding.buttonPanel.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.buttonPanel.itemsButton, ItemsActivity.class);
        setOnClickCreateActivity(binding.buttonPanel.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.buttonPanel.propertiesButton, PropertiesActivity.class);
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}
