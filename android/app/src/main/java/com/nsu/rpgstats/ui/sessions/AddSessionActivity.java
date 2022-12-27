package com.nsu.rpgstats.ui.sessions;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddSessionActivity extends AppCompatActivity {
    private static final String TAG = AddSessionActivity.class.getSimpleName();

    private ActivityAddSessionBinding binding;

    private AddSessionViewModel viewModel;
    private Map<String, Integer> gameSystemsMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddSessionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AddSessionViewModel.class);



        Spinner gsSpinner = binding.dropdownGamesystems;
        gameSystemsMap = new HashMap<>();
        viewModel.gameSystems.observe(this, gs -> {
            for (GameSystem i : gs) gameSystemsMap.put(i.getName(), i.getId());
            gsSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                    gameSystemsMap.keySet().toArray()));
        });
        viewModel.loadGameSystems();

        EditText name = binding.newSessionNameEdit;
        EditText maximumPlayers = binding.newSessionMaxPlayersNumberEdit;
        binding.addSessionButton.setOnClickListener(view -> {
            String gsName = gsSpinner.getSelectedItem().toString();
            Integer gsId = gameSystemsMap.get(gsName);
            Log.d(TAG, "gs id for new session: " + gsId);
            viewModel.onAddSessionButtonClick(name.getText().toString(),
                    maximumPlayers.getText().toString(),gsId);
            finish();

        });

    }
}
