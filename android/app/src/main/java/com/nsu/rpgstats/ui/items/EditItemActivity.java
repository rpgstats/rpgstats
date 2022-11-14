package com.nsu.rpgstats.ui.items;

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
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityEditItemBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.BadgeAdapter;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.tags.TagsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;
import com.nsu.rpgstats.ui.properties.PropertiesActivity;
import com.nsu.rpgstats.viewmodel.ItemInfoViewModel;
import com.nsu.rpgstats.viewmodel.ItemViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditItemActivity extends AppCompatActivity {
    private ActivityEditItemBinding binding;
    private int itemId;
    private int gameSystemId;
    private Item item;
    private List<Tag> tags;
    private List<Modifier> modifiers;
    BadgeAdapter<Tag> tagBadgeAdapter;
    BadgeAdapter<Modifier> modifierBadgeAdapter;
    private List<Tag> menuTags;
    private List<Tag> menuAllTags;
    private List<Modifier> menuModifiers;
    private List<Modifier> menuAllModifiers;
    BadgeAdapter<Tag> menuTagBadgeAdapter;
    BadgeAdapter<Modifier> menuModifierBadgeAdapter;
    BadgeAdapter<Tag> menuAllTagBadgeAdapter;
    BadgeAdapter<Modifier> menuAllModifierBadgeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemId = Integer.parseInt(getIntent().getStringExtra("id"));
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));
        ItemInfoViewModel itemInfoViewModel = new ItemInfoViewModel(gameSystemId ,itemId, ((RpgstatsApplication) getApplication()).appContainer.itemRepository);
        item = itemInfoViewModel.getItemInfo().getValue();

        menuAllTags = ((RpgstatsApplication)getApplication()).appContainer.tagRepository.getTags(gameSystemId);
        menuAllModifiers = ((RpgstatsApplication)getApplication()).appContainer.modifierRepository.getModifiers(gameSystemId);
        menuTags = new ArrayList<>();
        menuModifiers = new ArrayList<>();


        assert item != null;
        binding.EditItemNameInput.setText(item.getName());

        tags = item.getTags();
        modifiers = item.getModifiers();

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

        menuAllTagBadgeAdapter = new BadgeAdapter<>(tags, position -> {
            Tag tag = menuAllTags.get(position);
            if (!menuTags.contains(tag)) {
                menuTags.add(tag);
            }
            menuTagBadgeAdapter.setBadgesList(menuTags);
            binding.EditItemTagsMenu.BackgroundViewTags.setVisibility(View.GONE);
        }, false, AppCompatResources.getDrawable(this, R.drawable.rounded_card));

        menuAllModifierBadgeAdapter = new BadgeAdapter<>(menuAllModifiers, position -> {
            Modifier modifier = menuAllModifiers.get(position);
            if (!menuModifiers.contains(modifier)) {
                menuModifiers.add(modifier);
            }
            menuModifierBadgeAdapter.setBadgesList(menuModifiers);
            binding.EditItemModifierMenu.BackgroundViewModifiers.setVisibility(View.GONE);
        }, false, AppCompatResources.getDrawable(this, R.drawable.rounded_card));
        unsetBackgroundListeners();

        binding.EditItemTagsMenu.AddedTags.setAdapter(menuTagBadgeAdapter);
        binding.EditItemTagsMenu.AddedTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        binding.EditItemModifierMenu.AddedModifiers.setAdapter(menuModifierBadgeAdapter);
        binding.EditItemModifierMenu.AddedModifiers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.EditItemTagsMenu.ViewTags.setAdapter(menuAllTagBadgeAdapter);
        binding.EditItemTagsMenu.ViewTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        binding.EditItemModifierMenu.ViewModifiers.setAdapter(menuAllModifierBadgeAdapter);
        binding.EditItemModifierMenu.ViewModifiers.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

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

        setOnClickListener(binding.EditItemTagsMenu.ShowList, viewBackground -> {
            if (binding.EditItemTagsMenu.BackgroundViewTags.getVisibility() == View.GONE) {
                binding.EditItemTagsMenu.BackgroundViewTags.setVisibility(View.VISIBLE);
                menuAllTagBadgeAdapter.setBadgesList(menuAllTags);
                menuAllTagBadgeAdapter.notifyDataSetChanged();
                return;
            }
            binding.EditItemTagsMenu.BackgroundViewTags.setVisibility(View.GONE);
        });

        setOnClickListener(binding.EditItemModifierMenu.ShowList, viewBackground -> {
            if (binding.EditItemModifierMenu.BackgroundViewModifiers.getVisibility() == View.GONE) {
                binding.EditItemModifierMenu.BackgroundViewModifiers.setVisibility(View.VISIBLE);
                menuAllModifierBadgeAdapter.setBadgesList(menuAllModifiers);
                menuAllModifierBadgeAdapter.notifyDataSetChanged();
                return;
            }
            binding.EditItemModifierMenu.BackgroundViewModifiers.setVisibility(View.GONE);
        });

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
            ItemViewModel itemViewModel = ItemsActivity.viewModelProvider.get(ItemViewModel.class);
            Item newItem = new Item(itemId, item.getPictureId(), binding.EditItemNameInput.getText().toString(), tags, modifiers, item.isDeleted());
            itemViewModel.editItem(newItem, itemId, gameSystemId);

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
        setOnClickListener(binding.EditItemTagsMenu.Background, viewBackground -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.EditItemTagsButton, view -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            menuTags = new ArrayList<>();
            menuTagBadgeAdapter.setBadgesList(menuTags);
            menuTagBadgeAdapter.notifyDataSetChanged();
            unsetBackgroundListeners();
        });

        setOnClickListener(binding.EditItemModifierMenu.ModifierMenuBackButton, viewBackButton -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.EditItemModifierMenu.ModifierMenuAddButton, viewAddButton -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
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
        setOnClickListener(binding.EditItemModifierMenu.Background, viewBackground -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.EditItemModifierButton, view -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
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