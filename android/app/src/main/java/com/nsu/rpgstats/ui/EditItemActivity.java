package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.databinding.ActivityEditItemBinding;

public class EditItemActivity extends AppCompatActivity {
    private ActivityEditItemBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
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
        setOnClickListener(binding.EditItemBackButton, view -> {});
        setOnClickListener(binding.EditItemEditButton, view -> {});
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
        setOnClickListener(binding.EditItemBackButton, view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });
        setOnClickListener(binding.EditItemEditButton, view -> {
            //TODO return result
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        setOnClickListener(binding.EditItemTagsMenu.TagMenuBackButton, viewBackButton -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.EditItemTagsMenu.TagMenuAddButton, viewAddButton -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
            //TODO some action with add tag
        });
        setOnClickListener(binding.EditItemTagsMenu.Background, viewBackground -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.EditItemTagsButton, view -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });

        setOnClickListener(binding.EditItemModifierMenu.ModifierMenuBackButton, viewBackButton -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.EditItemModifierMenu.ModifierMenuAddButton, viewAddButton -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
            //TODO some action with add tag
        });
        setOnClickListener(binding.EditItemModifierMenu.Background, viewBackground -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.EditItemModifierButton, view -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}