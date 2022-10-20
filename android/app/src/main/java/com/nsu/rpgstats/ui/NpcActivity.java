package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityItemsBinding;
import com.nsu.rpgstats.databinding.ActivityNpcBinding;

public class NpcActivity extends Activity {
    private ActivityNpcBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNpcBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

}
