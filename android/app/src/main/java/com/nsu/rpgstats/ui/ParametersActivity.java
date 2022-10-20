package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityItemsBinding;
import com.nsu.rpgstats.databinding.ActivityParametersBinding;

public class ParametersActivity extends Activity {
    ActivityParametersBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
