package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.databinding.ActivityAddItemBinding;

public class AddItemActivity extends AppCompatActivity {
    private ActivityAddItemBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setOnClickListener(View button, View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    private void unsetBackgroundListeners() {
        setOnClickListener(binding.BottomBar.tagsButton, view -> {});
        setOnClickListener(binding.BottomBar.npcButton, view -> {});
        setOnClickListener(binding.BottomBar.parametersButton, view -> {});
        setOnClickListener(binding.BottomBar.itemsButton, view -> {});
        setOnClickListener(binding.BottomBar.propertiesButton, view -> {});
        setOnClickListener(binding.AddItemBackButton, view -> {});
        setOnClickListener(binding.AddItemAddButton, view -> {});
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.tagsButton, TagsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        setOnClickListener(binding.BottomBar.itemsButton, view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });
        setOnClickListener(binding.AddItemBackButton, view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });
        setOnClickListener(binding.AddItemAddButton, view -> {
            //TODO return result
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        setOnClickListener(binding.AddItemTagMenu.TagMenuBackButton, viewBackButton -> {
            binding.AddItemTagMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.AddItemTagMenu.TagMenuAddButton, viewAddButton -> {
            binding.AddItemTagMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
            //TODO some action with add tag
        });
        setOnClickListener(binding.AddItemTagMenu.Background, viewBackground -> {
            binding.AddItemTagMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.AddItemTagsButton, view -> {
            binding.AddItemTagMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });

        setOnClickListener(binding.AddItemModifierMenu.ModifierMenuBackButton, viewBackButton -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.AddItemModifierMenu.ModifierMenuAddButton, viewAddButton -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
            //TODO some action with add tag
        });
        setOnClickListener(binding.AddItemModifierMenu.Background, viewBackground -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.AddItemModifierButton, view -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });

    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}