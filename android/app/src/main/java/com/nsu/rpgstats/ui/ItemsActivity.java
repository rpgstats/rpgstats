package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityItemsBinding;

public class ItemsActivity extends Activity {
    private ActivityItemsBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
