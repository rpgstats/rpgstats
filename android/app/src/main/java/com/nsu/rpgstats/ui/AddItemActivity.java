package com.nsu.rpgstats.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityAddItemBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: tags and modifiers

public class AddItemActivity extends AppCompatActivity {
    private ActivityAddItemBinding binding;
    private Integer gameSystemId;
    private List<Tag> tags;
    private List<Modifier> modifiers;
    private List<Tag> menuTags;
    private List<Tag> menuAllTags;
    private List<Modifier> menuModifiers;
    private List<Modifier> menuAllModifiers;
    BadgeAdapter<Tag> menuTagBadgeAdapter;
    BadgeAdapter<Modifier> menuModifierBadgeAdapter;
    BadgeAdapter<Tag> menuAllTagBadgeAdapter;
    BadgeAdapter<Modifier> menuAllModifierBadgeAdapter;
    BadgeAdapter<Tag> tagBadgeAdapter;
    BadgeAdapter<Modifier> modifierBadgeAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));
        tags = new ArrayList<>();
        modifiers = new ArrayList<>();
        menuAllTags = ((RpgstatsApplication)getApplication()).appContainer.tagRepository.getTags(gameSystemId);
        menuAllModifiers = ((RpgstatsApplication)getApplication()).appContainer.modifierRepository.getModifiers(gameSystemId);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        menuTags = new ArrayList<>();
        menuModifiers = new ArrayList<>();

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


        menuTagBadgeAdapter = new BadgeAdapter<>(menuTags, position -> {
            menuTags.remove(position);
            menuTagBadgeAdapter.setBadgesList(menuTags);
        }, true, AppCompatResources.getDrawable(this, R.drawable.rounded_card));

        menuModifierBadgeAdapter = new BadgeAdapter<>(menuModifiers, position -> {
            menuModifiers.remove(position);
            menuModifierBadgeAdapter.setBadgesList(menuModifiers);
        }, true, AppCompatResources.getDrawable(this, R.drawable.rounded_card));

        menuAllTagBadgeAdapter = new BadgeAdapter<>(menuAllTags, position -> {
            Tag tag = menuAllTags.get(position);
            if (!menuTags.contains(tag)) {
                menuTags.add(tag);
            }
            menuTagBadgeAdapter.setBadgesList(menuTags);
            binding.AddItemTagMenu.BackgroundViewTags.setVisibility(View.GONE);
        }, false, AppCompatResources.getDrawable(this, R.drawable.rounded_card));

        menuAllModifierBadgeAdapter = new BadgeAdapter<>(menuAllModifiers, position -> {
            Modifier modifier = menuAllModifiers.get(position);
            if (!menuModifiers.contains(modifier)) {
                menuModifiers.add(modifier);
            }
            menuModifierBadgeAdapter.setBadgesList(menuModifiers);
            binding.AddItemModifierMenu.BackgroundViewModifiers.setVisibility(View.GONE);
        }, false, AppCompatResources.getDrawable(this, R.drawable.rounded_card));
        unsetBackgroundListeners();

        binding.AddItemTagMenu.AddedTags.setAdapter(menuTagBadgeAdapter);
        binding.AddItemTagMenu.AddedTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        binding.AddItemModifierMenu.AddedModifiers.setAdapter(menuModifierBadgeAdapter);
        binding.AddItemModifierMenu.AddedModifiers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.AddItemTagMenu.ViewTags.setAdapter(menuAllTagBadgeAdapter);
        binding.AddItemTagMenu.ViewTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.AddItemModifierMenu.ViewModifiers.setAdapter(menuAllModifierBadgeAdapter);
        binding.AddItemModifierMenu.ViewModifiers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
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
            ItemViewModel itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
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
            HashMap<Integer, Tag> tagHashMap = new HashMap<>();
            for (Tag tag : tags) {
                tagHashMap.put(tag.getId(), tag);
            }
            for (Tag tag : menuTags) {
                tagHashMap.put(tag.getId(), tag);
            }
            tags = new ArrayList<Tag>(tagHashMap.values());
            tagBadgeAdapter.setBadgesList(tags);
            tagBadgeAdapter.notifyDataSetChanged();
            setListeners();
        });


        setOnClickListener(binding.AddItemTagMenu.Background, viewBackground -> {
            binding.AddItemTagMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.AddItemTagMenu.ShowList, viewBackground -> {
            if (binding.AddItemTagMenu.BackgroundViewTags.getVisibility() == View.GONE) {
                binding.AddItemTagMenu.BackgroundViewTags.setVisibility(View.VISIBLE);
                menuAllTagBadgeAdapter.setBadgesList(menuAllTags);
                menuAllTagBadgeAdapter.notifyDataSetChanged();
                return;
            }
            binding.AddItemTagMenu.BackgroundViewTags.setVisibility(View.GONE);
        });

        setOnClickListener(binding.AddItemModifierMenu.ShowList, viewBackground -> {
            if (binding.AddItemModifierMenu.BackgroundViewModifiers.getVisibility() == View.GONE) {
                binding.AddItemModifierMenu.BackgroundViewModifiers.setVisibility(View.VISIBLE);
                menuAllModifierBadgeAdapter.setBadgesList(menuAllModifiers);
                menuAllModifierBadgeAdapter.notifyDataSetChanged();
                return;
            }
            binding.AddItemModifierMenu.BackgroundViewModifiers.setVisibility(View.GONE);
        });

        setOnClickListener(binding.AddItemTagsButton, view -> {
            binding.AddItemTagMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            menuTags = new ArrayList<>();
            menuTagBadgeAdapter.setBadgesList(menuTags);
            menuTagBadgeAdapter.notifyDataSetChanged();
            unsetBackgroundListeners();
        });

        setOnClickListener(binding.AddItemModifierMenu.ModifierMenuBackButton, viewBackButton -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.AddItemModifierMenu.ModifierMenuAddButton, viewAddButton -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            HashMap<Integer, Modifier> modifierHashMap = new HashMap<>();
            for (Modifier modifier : modifiers) {
                modifierHashMap.put(modifier.getId(), modifier);
            }
            for (Modifier modifier : menuModifiers) {
                modifierHashMap.put(modifier.getId(), modifier);
            }
            modifiers = new ArrayList<Modifier>(modifierHashMap.values());
            modifierBadgeAdapter.setBadgesList(modifiers);
            modifierBadgeAdapter.notifyDataSetChanged();
            setListeners();
        });
        setOnClickListener(binding.AddItemModifierMenu.Background, viewBackground -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.AddItemModifierButton, view -> {
            binding.AddItemModifierMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            menuModifiers = new ArrayList<>();
            menuModifierBadgeAdapter.setBadgesList(menuModifiers);
            menuModifierBadgeAdapter.notifyDataSetChanged();
            unsetBackgroundListeners();
        });

    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}