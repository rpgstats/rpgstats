package com.nsu.rpgstats.ui.tags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityTagsBinding;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.items.ItemsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;
import com.nsu.rpgstats.ui.properties.PropertiesActivity;
import com.nsu.rpgstats.viewmodel.TagViewModel;

import java.util.List;

public class TagsActivity extends AppCompatActivity implements TagsAdapter.OnTagClickListener{
    private ActivityTagsBinding binding;
    private List<Tag> mTagList;
    private TagsAdapter mTagsAdapter;
    private Integer gameSystemId;
    private TagViewModel tagViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gameSystemId = ((RpgstatsApplication)getApplication()).appContainer.currentGameSystem.getId();
        if (savedInstanceState == null) {
            tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
        }
        mTagList = tagViewModel.getTags(gameSystemId).getValue();
        tagViewModel.getTags(gameSystemId).observe(this, tags -> {
            mTagsAdapter.setTagList(tags);
        });

        RecyclerView recyclerView = binding.TagsActivityTags;
        mTagsAdapter = new TagsAdapter(mTagList, this, this);
        recyclerView.setAdapter(mTagsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tagViewModel.loadTags(gameSystemId);
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.itemsButton, ItemsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        binding.TagsActivityAddButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddTagsActivity.class);
            intent.putExtra("game_system_id", gameSystemId.toString());
            startActivity(intent);
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onTagClick(int position) {
        Tag mTag = mTagList.get(position);
        Intent intent = new Intent(this, ViewTagInfoActivity.class);
        intent.putExtra("id", mTag.getId().toString());
        intent.putExtra("game_system_id", gameSystemId.toString());
        startActivity(intent);
    }
}
