package com.nsu.rpgstats.ui.tags;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.databinding.ActivityAddTagsBinding;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.items.ItemsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;
import com.nsu.rpgstats.ui.properties.PropertiesActivity;
import com.nsu.rpgstats.viewmodel.TagViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTagsActivity extends AppCompatActivity {
    private ActivityAddTagsBinding binding;
    private Integer gameSystemId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));

        setListeners();
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setOnClickListener(View button, View.OnClickListener listener) {
        button.setOnClickListener(listener);
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.itemsButton, ItemsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        setOnClickListener(binding.BottomBar.tagsButton, view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });
        setOnClickListener(binding.AddTagBackButton, view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });
        setOnClickListener(binding.AddTagAddButton, view -> {
            Tag newTag = new Tag(0, binding.AddTagInputName.getText().toString(), new SimpleDateFormat("dd.MM.yyyy", Locale.US).format(new Date()), false);
            TagsActivity.viewModelProvider.get(TagViewModel.class).addTag(newTag, gameSystemId);

            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}