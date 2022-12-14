package com.nsu.rpgstats.ui.items;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityViewItemInfoBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.tags.TagsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;
import com.nsu.rpgstats.ui.properties.PropertiesActivity;
import com.nsu.rpgstats.viewmodel.ItemInfoViewModel;
import com.nsu.rpgstats.viewmodel.ItemViewModel;

public class ViewItemInfoActivity extends AppCompatActivity {

    private ActivityViewItemInfoBinding binding;
    private Integer itemId;
    private Integer gameSystemId;
    private Item item;
    ArrayAdapter<Tag> tagAdapter;
    ArrayAdapter<Modifier> modifierAdapter;
    protected ActivityResultLauncher<Intent> activityLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                ItemInfoViewModel viewModel = new ItemInfoViewModel(gameSystemId ,itemId, ((RpgstatsApplication) getApplication()).appContainer.itemRepository);
                item = viewModel.getItemInfo().getValue();
                setInfo();
            }
        });
        binding = ActivityViewItemInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        itemId = Integer.parseInt(getIntent().getStringExtra("id"));
        gameSystemId = Integer.parseInt(getIntent().getStringExtra("game_system_id"));

        ItemInfoViewModel viewModel = new ItemInfoViewModel(gameSystemId ,itemId, ((RpgstatsApplication) getApplication()).appContainer.itemRepository);
        item = viewModel.getItemInfo().getValue();


        setInfo();
        setListeners();
    }

    private void setInfo() {
        binding.ItemInfoHeader.setText(item.getName());

        tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, item.getTags());
        modifierAdapter = new ArrayAdapter<Modifier>(this, android.R.layout.simple_list_item_1, item.getModifiers());
        binding.Tags.setAdapter(tagAdapter);
        binding.Modifiers.setAdapter(modifierAdapter);
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
        setOnClickListener(binding.ViewItemInfoCopyButton, view -> {});
        setOnClickListener(binding.ViewItemInfoEditButton, view -> {});
        setOnClickListener(binding.ViewItemInfoDeleteButtom, view -> {});
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

        setOnClickListener(binding.ViewItemInfoEditButton, view -> {
            Intent intent = new Intent(this, EditItemActivity.class);
            intent.putExtra("id", itemId.toString());
            intent.putExtra("game_system_id", gameSystemId.toString());
            activityLauncher.launch(intent);
        });

        setOnClickListener(binding.ViewItemInfoCopyButton, view -> {
            //TODO copy item
        });

        setOnClickListener(binding.DeleteOverlay.DeleteNoButton, view -> {
            binding.DeleteOverlay.getRoot().setVisibility(ConstraintLayout.GONE);
            setListeners();
        });

        setOnClickListener(binding.DeleteOverlay.DeleteYesButton, view -> {
            ItemViewModel itemViewModel = ItemsActivity.viewModelProvider.get(ItemViewModel.class);
            itemViewModel.deleteItem(itemId, gameSystemId);
            Intent intent = new Intent();
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        setOnClickListener(binding.ViewItemInfoDeleteButtom, view -> {
            binding.DeleteOverlay.getRoot().setVisibility(ConstraintLayout.VISIBLE);
            unsetBackgroundListeners();
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

}