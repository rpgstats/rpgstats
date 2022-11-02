package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.databinding.ActivityTagsBinding;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagsActivity extends AppCompatActivity implements TagsAdapter.OnTagClickListener{
    private ActivityTagsBinding binding;
    private List<Tag> mTagList;
    private TagsAdapter mTagsAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTagsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO remove this code and get tags from outside
        mTagList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            mTagList.add(new Tag(i, "tag " + i, false));
        }

        RecyclerView recyclerView = binding.TagsActivityTags;
        mTagsAdapter = new TagsAdapter(mTagList, this);
        recyclerView.setAdapter(mTagsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setListeners();
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.itemsButton, ItemsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        setOnClickCreateActivity(binding.TagsActivityAddButton, AddTagsActivity.class);
        //TODO recycler item listener
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
        startActivity(intent);
    }
}
