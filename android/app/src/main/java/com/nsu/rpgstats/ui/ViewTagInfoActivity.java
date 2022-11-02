package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityAddTagsBinding;
import com.nsu.rpgstats.databinding.ActivityViewTagInfoBinding;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.viewmodel.TagInfoViewModel;
import com.nsu.rpgstats.viewmodel.TagViewModel;

public class ViewTagInfoActivity extends AppCompatActivity {
    private ActivityViewTagInfoBinding binding;
    private Integer gameSystemId;
    private Integer tagId;
    private Tag tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewTagInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));
        tagId = Integer.parseInt(getIntent().getStringExtra("id"));
        TagInfoViewModel tagInfoViewModel = new TagInfoViewModel(tagId, ((RpgstatsApplication)getApplication()).appContainer.tagRepository);
        tag = tagInfoViewModel.getItemInfo().getValue();
        binding.ViewTagHeader.setText(tag.getName());
        binding.CreationTagDate.setText(tag.getCreationDate());

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
        setOnClickListener(binding.ViewTagInfoEditButton, view -> {});
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
        setOnClickListener(binding.ViewTagInfoEditButton, view -> {
            Intent intent = new Intent(this, EditTagsActivity.class);
            intent.putExtra("id", tagId.toString());
            intent.putExtra("game_system_id", gameSystemId.toString());
            startActivity(intent);
        });
        setOnClickListener(binding.ViewTagInfoCopyButton, view -> {
            //TODO copy tag
        });
        setOnClickListener(binding.ViewTagDeleteWarning.DeleteYesButton, view -> {
            //TODO check tag entry
            TagsActivity.viewModelProvider.get(TagViewModel.class).deleteTag(tagId, gameSystemId);

            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        setOnClickListener(binding.ViewTagDeleteWarning.DeleteNoButton, view -> {
            binding.ViewTagDeleteWarning.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.ViewTagInfoDeleteButton, view -> {
            binding.ViewTagDeleteWarning.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}