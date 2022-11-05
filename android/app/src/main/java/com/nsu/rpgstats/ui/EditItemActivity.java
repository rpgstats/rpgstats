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
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityEditItemBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.viewmodel.ItemInfoViewModel;
import com.nsu.rpgstats.viewmodel.ItemViewModel;

import java.util.List;

//TODO: fix editing and make modifiers and tags

public class EditItemActivity extends AppCompatActivity {
    private ActivityEditItemBinding binding;
    private int itemId;
    private int gameSystemId;
    private Item item;
    private List<Tag> tags;
    private List<Modifier> modifiers;
    BadgeAdapter<Tag> tagBadgeAdapter;
    BadgeAdapter<Modifier> modifierBadgeAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemId = Integer.parseInt(getIntent().getStringExtra("id"));
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));
        ItemInfoViewModel itemInfoViewModel = new ItemInfoViewModel(gameSystemId ,itemId, ((RpgstatsApplication) getApplication()).appContainer.itemRepository);
        item = itemInfoViewModel.getItemInfo().getValue();

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
            Item newItem = new Item(itemId, item.getPictureId(), binding.EditItemNameInput.getText().toString(), item.getTags(), item.getModifiers(), item.isDeleted());
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
            setListeners();
            //TODO some action with add tag
        });
        setOnClickListener(binding.EditItemTagsMenu.Background, viewBackground -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.EditItemTagsButton, view -> {
            binding.EditItemTagsMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });

        setOnClickListener(binding.EditItemModifierMenu.ModifierMenuBackButton, viewBackButton -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });
        setOnClickListener(binding.EditItemModifierMenu.ModifierMenuAddButton, viewAddButton -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
            //TODO some action with add tag
        });
        setOnClickListener(binding.EditItemModifierMenu.Background, viewBackground -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.EditItemModifierButton, view -> {
            binding.EditItemModifierMenu.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}