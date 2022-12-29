package com.nsu.rpgstats.ui.character;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.FragmentCharacterEquipmentBinding;
import com.nsu.rpgstats.databinding.SlotItemBinding;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.ItemSlot;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.characters.info.slots.SlotsViewModel;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CharacterEquipmentFragment extends Fragment {
    private List<Slot> slotsFromActivity;
    private List<Item> items;
    private FragmentCharacterEquipmentBinding binding;
    private SelectionViewModel mViewModel;
    private SlotsViewModel mSlotsViewModel;
    private AlertDialog pickItemDialog = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentCharacterEquipmentBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        mSlotsViewModel = new ViewModelProvider(requireActivity()).get(SlotsViewModel.class);
        slotsFromActivity = mViewModel.getCharacterList().getValue().get(getArguments().getInt("position")).getSlotList();
        int systemId = mViewModel.getCharacterList().getValue().get(getArguments().getInt("position")).getGameSystemId();
        items = ((RpgstatsApplication)requireActivity().getApplication()).appContainer.itemRepository.getItems(systemId);
        binding.BackButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment, getArguments());
        });
        for (int i = 0; i < slotsFromActivity.size(); ++i) {
            View view = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.slot_item, binding.Constraint, false);
            view.setId(View.generateViewId());
            binding.Constraint.addView(view);
            binding.flow.addView(view);
            SlotItemBinding bindingNewItem = SlotItemBinding.bind(view);
            mSlotsViewModel.loadImage(bindingNewItem, slotsFromActivity.get(i).getIconUrl());
            int finalI = i;
            bindingNewItem.container.setBackground(slotsFromActivity.get(finalI).getItem() != null ? AppCompatResources.getDrawable(getContext(), R.drawable.rounded_card) :
                    AppCompatResources.getDrawable(getContext(), R.drawable.rounded_border));
            bindingNewItem.getRoot().setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme);
                Item currentItem = slotsFromActivity.get(finalI).getItem();
                StringBuilder title = new StringBuilder();
                title.append(currentItem == null ? "Slot unequipped" : "Slot equipped: ");
                title.append(currentItem == null ? "" : currentItem.getName());

                builder.setTitle(title.toString());

                builder.setNeutralButton("Info", ((dialogInterface, i1) -> {
                    AlertDialog.Builder infoBuilder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme);
                    StringBuilder message = new StringBuilder();

                    if (currentItem != null) {
                        message.append("current item: ").append(currentItem.getName()).append("\n");
                        message.append("tags: ").append("\n");
                        for (Tag tag: currentItem.getTags()) {
                            message.append(tag.getName()).append("\n");
                        }
                        message.append("modifiers: ").append("\n");
                        for (Modifier modifier: currentItem.getModifiers()) {
                            message.append(modifier.getParameter().getName()).append(" ").append(modifier.getValue()).append("\n");
                        }
                    } else {
                        message.append("Constraints: ").append(slotsFromActivity.get(finalI).isWhitelisted() ? "White list" : "Black list").append("\n");
                        for (Tag tag: slotsFromActivity.get(finalI).getTags()) {
                            message.append(tag.getName()).append("\n");
                        }
                    }

                    infoBuilder.setNegativeButton("Cancel", (dialogInterface1, i13) -> {
                        pickItemDialog.show();
                    });
                    infoBuilder.setMessage(message.toString());
                    infoBuilder.create().show();
                }));


                final Integer[] checked = new Integer[1];

                // Filter all items matching clicked slot's constraints
                List<Item> fittingItems = new ArrayList<>();
                for (Item it : items) {
                    if (fits(it, slotsFromActivity.get(finalI).getTags(), slotsFromActivity.get(finalI).isWhitelisted())) {
                        fittingItems.add(it);
                    }
                }

                // Names of all fitting items to show
                List<String> itemNamesList = new ArrayList<>();
                if (slotsFromActivity.get(finalI).getItem() != null) {
                    itemNamesList.add("Remove item");
                }
                for (int j = 0; j < fittingItems.size(); ++j) {
                    itemNamesList.add(fittingItems.get(j).getName());
                }

                String[] itemNames = itemNamesList.toArray(new String[0]);

                // Make this dialog display a list of items that can be placed in slot
                builder.setSingleChoiceItems(itemNames, -1, (dialogInterface, i1) -> {
                    // If there is an item in a slot, then all indexes are moved by the first line 'remove item'
                    checked[0] = i1 - (slotsFromActivity.get(finalI).getItem() == null ? 0 : 1);
                    if (pickItemDialog != null) {
                        pickItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                });

                // TODO: callback: send changed item in slot
                builder.setPositiveButton("Equip", (dialogInterface, i12) -> {
                    // TODO callback: item removed from slot
                    if (checked[0] == -1) {
                        Item item = slotsFromActivity.get(finalI).getItem();
                        slotsFromActivity.get(finalI).setItem(null);
                        bindingNewItem.container.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.rounded_border));
                        return;
                    }

                    // TODO callback: item placed in slot
                    Item checkedItem = fittingItems.get(checked[0]);
                    String type = checkedItem.getTags().get(0).getName();
                    slotsFromActivity.get(finalI).setItem(checkedItem);
                    bindingNewItem.container.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.rounded_card));
                });
                builder.setNegativeButton("Cancel", (dialogInterface, i13) -> {
                    Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                });
                pickItemDialog = builder.create();
                pickItemDialog.setOnShowListener(dialogInterface -> {
                    pickItemDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                });
                pickItemDialog.show();
            });
        }
        return binding.getRoot();
    }

    private boolean fits(Item item, List<Tag> tags, boolean isWhitelisted) {
        if (isWhitelisted) {
            List<Tag> itemTags = item.getTags();
            for (Tag it : itemTags) {
                for (Tag tag : tags) {
                    if (Objects.equals(tag.getId(), it.getId())) {
                        return true;
                    }
                }
            }
            return false;
        }
        List<Tag> itemTags = item.getTags();
        for (Tag it : itemTags) {
            for (Tag tag : tags) {
                if (Objects.equals(tag.getId(), it.getId())) {
                    return false;
                }
            }
        }
        return true;
    }
}