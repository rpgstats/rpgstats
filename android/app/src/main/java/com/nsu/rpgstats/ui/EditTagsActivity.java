package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityAddTagsBinding;
import com.nsu.rpgstats.databinding.ActivityEditItemBinding;
import com.nsu.rpgstats.databinding.ActivityEditTagsBinding;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.viewmodel.TagInfoViewModel;
import com.nsu.rpgstats.viewmodel.TagViewModel;

public class EditTagsActivity extends AppCompatActivity {

    private ActivityEditTagsBinding binding;
    private Integer gameSystemId;
    private Integer tagId;
    private Tag tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));
        tagId = Integer.parseInt(getIntent().getStringExtra("id"));

        TagInfoViewModel tagInfoViewModel = new TagInfoViewModel(gameSystemId, tagId, ((RpgstatsApplication)getApplication()).appContainer.tagRepository);
        tag = tagInfoViewModel.getItemInfo().getValue();

        binding.EditTagInputName.setText(tag.getName());

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

        setOnClickListener(binding.EditTagBackButton, view -> {
            Intent intent = new Intent();
            setResult(Activity.RESULT_CANCELED, intent);
            finish();
        });
        setOnClickListener(binding.EditTagEditButton, view -> {
            Tag newTag = new Tag(tagId, binding.EditTagInputName.getText().toString(), tag.getCreationDate(), tag.isDeleted());
            TagsActivity.viewModelProvider.get(TagViewModel.class).editTag(newTag, tagId, gameSystemId);

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