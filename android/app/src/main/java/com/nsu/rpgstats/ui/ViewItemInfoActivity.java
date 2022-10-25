package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.databinding.ActivityViewItemInfoBinding;

public class ViewItemInfoActivity extends AppCompatActivity {

    private ActivityViewItemInfoBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewItemInfoBinding.inflate(getLayoutInflater());
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
        setOnClickListener(binding.ViewItemInfoCopyButton, view -> {});
        setOnClickListener(binding.ViewItemInfoEditButton, view -> {});
        setOnClickListener(binding.ViewItemInfoDeleteButtom, view -> {});
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

        setOnClickCreateActivity(binding.ViewItemInfoEditButton, EditItemActivity.class);

        setOnClickListener(binding.ViewItemInfoCopyButton, view -> {
            //TODO copy item
        });

        setOnClickListener(binding.DeleteOverlay.DeleteNoButton, view -> {
            binding.DeleteOverlay.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.DeleteOverlay.DeleteYesButton, view -> {
            //TODO delete item
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });

        setOnClickListener(binding.ViewItemInfoDeleteButtom, view -> {
            binding.DeleteOverlay.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}