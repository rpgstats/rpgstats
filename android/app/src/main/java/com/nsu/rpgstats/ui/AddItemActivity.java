package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityAddItemBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.List;

//TODO: tags and modifiers

public class AddItemActivity extends AppCompatActivity {
    private ActivityAddItemBinding binding;
    private Integer gameSystemId;
    private List<Tag> tags;
    private List<Modifier> modifiers;
    BadgeAdapter<Tag> tagBadgeAdapter;
    BadgeAdapter<Modifier> modifierBadgeAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tags = new ArrayList<>();
        modifiers = new ArrayList<>();
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));

        tagBadgeAdapter = new BadgeAdapter<>(tags, position -> {
            tags.remove(position);
            tagBadgeAdapter.setBadgesList(tags);
        }, true, AppCompatResources.getDrawable(this, R.drawable.rounded_card));

        modifierBadgeAdapter = new BadgeAdapter<>(modifiers, position -> {
            modifiers.remove(position);
            modifierBadgeAdapter.setBadgesList(modifiers);
        }, true, AppCompatResources.getDrawable(this, R.drawable.rounded_card));

        binding.Tags.setAdapter(tagBadgeAdapter);
        binding.Modifiers.setAdapter(modifierBadgeAdapter);
        binding.Tags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.Modifiers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

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
            Item item = new Item(0, 1337, binding.AddItemNameInput.getText().toString(), tags, modifiers, false);
            ItemViewModel itemViewModel = ItemsActivity.viewModelProvider.get(ItemViewModel.class);
            itemViewModel.addItem(item, gameSystemId);

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