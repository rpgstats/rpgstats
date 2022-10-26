package com.nsu.rpgstats.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityItemsBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemsActivity extends Activity implements ItemsAdapter.OnItemClickListener{
    private ActivityItemsBinding binding;

    private List<Item> mItemList;
    private ItemsAdapter mItemsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO remove this code and get items from outside
        mItemList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            mItemList.add(new Item(i, 1337, "Item " + i, null, null));
        }

        RecyclerView recyclerView = binding.ItemList.recyclerVIew;
        mItemsAdapter = new ItemsAdapter(mItemList, this);
        recyclerView.setAdapter(mItemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setListeners();
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.tagsButton, TagsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        setOnClickCreateActivity(binding.ItemActivityAddItemButton, AddItemActivity.class);
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Item mItem = mItemList.get(position);
        Intent intent = new Intent(this, ViewItemInfoActivity.class);
        intent.putExtra("id", mItem.getId().toString());
        startActivity(intent);
    }
}
