package com.nsu.rpgstats.ui.sessions;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.rpgstats.databinding.ActivityAddSessionBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.viewmodel.sessions.AddSessionViewModel;

import java.util.List;

public class AddSessionActivity extends AppCompatActivity {
    private static final String TAG = AddSessionActivity.class.getSimpleName();

    private ActivityAddSessionBinding binding;

    private AddSessionViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSessionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AddSessionViewModel.class);

        EditText name = binding.newSessionNameEdit;
        EditText maximumPlayers = binding.newSessionMaxPlayersNumberEdit;
        binding.addSessionButton.setOnClickListener(view -> {
            viewModel.onAddSessionButtonClick(name.getText().toString(),
                    maximumPlayers.getText().toString());
            finish();

        });

        Spinner gameSystems = binding.dropdownGamesystems;

        viewModel.loadGameSystems();
        viewModel.gameSystems.observe(this, gs -> {
            gameSystems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                    gs));
        });

    }
}
