package com.nsu.rpgstats.ui.gamesystems;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.AppContainer;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.gamesystems.GameSystemsRepository;
import com.nsu.rpgstats.databinding.ActivityGameSystemInfoBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.items.ItemsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;
import com.nsu.rpgstats.ui.properties.PropertiesActivity;
import com.nsu.rpgstats.ui.tags.TagsActivity;
import com.nsu.rpgstats.viewmodel.GameSystemInfoViewModel;

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

        AppContainer appContainer = ((RpgstatsApplication) getApplication()).appContainer;
        GameSystemsRepository repository = appContainer.gameSystemsRepository;
        this.gameSystem = appContainer.currentGameSystem;
        GameSystemInfoViewModel viewModel = new GameSystemInfoViewModel(gameSystem, repository);
        this.gameSystem = viewModel.getGameSystemInfo().getValue();
        ((RpgstatsApplication) getApplication()).appContainer.currentGameSystem = gameSystem;
        setInfo();
        setListeners();
    }

    private void setInfo() {
        String description = gameSystem.getName() + " " + gameSystem.getCreationDate();
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
