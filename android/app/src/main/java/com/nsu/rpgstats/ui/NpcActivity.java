package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityItemsBinding;
import com.nsu.rpgstats.databinding.ActivityNpcBinding;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.ui.parameters.ParameterAdapter;

import java.util.List;

public class NpcActivity extends AppCompatActivity {
    private ActivityNpcBinding binding;
    private Parameter parameter = null;
    private List<Parameter> parameters;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNpcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int gameSystem = ((RpgstatsApplication)getApplication()).appContainer.currentGameSystem.getId();
        parameters = ((RpgstatsApplication)getApplication()).appContainer.parameterRepository.getParameters(gameSystem).getValue();
        ((RpgstatsApplication)getApplication()).appContainer.parameterRepository.getParameters(gameSystem).observe(this, parameters1 -> {
            parameters = parameters1;
        });
        binding.constraintLayout4.setOnClickListener(view -> {
            binding.allParameters.setVisibility(View.VISIBLE);
            BadgeAdapter<Parameter> adapter = new BadgeAdapter<>(parameters, position -> {
                parameter = parameters.get(position);
                binding.parameterName.setText(parameter.getName());
                binding.allParameters.setVisibility(View.GONE);
            }, false, AppCompatResources.getDrawable(this, R.drawable.rounded_card));
            binding.parameters.setAdapter(adapter);
            binding.parameters.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        });
        binding.button.setOnClickListener(view -> {
            if (parameter == null ||
                    binding.inputValue.getText().toString().equals("") ||
                    Integer.parseInt(binding.inputValue.getText().toString()) == 0 ||
                    Integer.parseInt(binding.inputValue.getText().toString()) >= 1000 ||
                    Integer.parseInt(binding.inputValue.getText().toString()) <= -1000) {
                Toast.makeText(this, "Select parameter or value must be not 0 and < 1000 and > -1000", Toast.LENGTH_SHORT).show();
                return;
            }
            int value = Integer.parseInt(binding.inputValue.getText().toString());
            if (binding.inputName.getText().toString().equals("")) {
                ((RpgstatsApplication) getApplication()).appContainer.modifierRepository.addModifier(gameSystem,
                        new Modifier(0, value > 0 ? parameter.getName() + " up" : parameter.getName() + " down", value, parameter));
            } else {
                ((RpgstatsApplication) getApplication()).appContainer.modifierRepository.addModifier(gameSystem,
                        new Modifier(0, binding.inputName.getText().toString(), value, parameter));
            }
            Toast.makeText(this, "Modifier created", Toast.LENGTH_SHORT).show();
            finish();
        });
        binding.getRoot().setOnClickListener(view -> {
            binding.allParameters.setVisibility(View.GONE);
        });
    }

}
