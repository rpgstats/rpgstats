package com.nsu.rpgstats.ui.characters.info.slots;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentSlotsBinding;
import com.nsu.rpgstats.databinding.ItemsBadgeBinding;
import com.nsu.rpgstats.databinding.SlotItemBinding;
import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.ui.characters.WindowViewModel;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;
import com.nsu.rpgstats.ui.characters.windows.info.slots.SlotViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlotsFragment extends Fragment {

    private SlotsViewModel mViewModel;
    private SelectionViewModel mSelectionViewModel;
    private FragmentSlotsBinding binding;
    private int position;
    private View addSlot;
    private List<View> slotViews;
    private HashMap<Integer, Integer> toDelete = new HashMap<>();


    public static SlotsFragment newInstance() {
        return new SlotsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSlotsBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SlotsViewModel.class);
        mViewModel.reInit();
        mSelectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        slotViews = new ArrayList<>();
        position = getArguments().getInt("position");


        View newImageView = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.slot_item, binding.Constraint, false);
        newImageView.setId(View.generateViewId());
        binding.Constraint.addView(newImageView);
        binding.flow.addView(newImageView);
        addSlot = newImageView;
        SlotItemBinding bindingAddItem = SlotItemBinding.bind(newImageView);

        bindingAddItem.getRoot().setOnClickListener(view -> {
            mViewModel.setIsChanged(true);
            mViewModel.getSlotList().getValue().add(new Slot(0, "", "", false, mSelectionViewModel.getCharacterList().getValue().get(position).getId(), -1));
            mViewModel.setSlotList(mViewModel.getSlotList().getValue());
        });

        binding.BackButton.setOnClickListener(view -> {
            if (mViewModel.getIsChanged().getValue()) {
                new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
                Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.slotsWarningUnsaveFragment, getArguments());
                return;
            }
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment, getArguments());
        });

        binding.SaveButton.setOnClickListener(view -> {
            mSelectionViewModel.saveSlots(mViewModel.getSlotList().getValue(), mSelectionViewModel.getCharacterList().getValue().get(position).getId(), position);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment, getArguments());
        });


        position = getArguments().getInt("position");
        mViewModel.getSlotList().observe(getViewLifecycleOwner(), slots -> {
            for (View view: slotViews) {
                binding.Constraint.removeView(view);
                binding.flow.removeView(view);
            }
            slotViews = new ArrayList<>();
            for (int i = 0; i < mViewModel.getSlotList().getValue().size(); ++i) {
                View view = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.slot_item, binding.Constraint, false);
                view.setId(View.generateViewId());
                binding.Constraint.addView(view);
                binding.flow.addView(view);
                slotViews.add(view);
                SlotItemBinding bindingNewItem = SlotItemBinding.bind(view);
                mViewModel.loadImage(bindingNewItem, mViewModel.getSlotList().getValue().get(i).getIconUrl());
                int finalI = i;
                bindingNewItem.getRoot().setOnClickListener(v -> {
                    if (mViewModel.getToDelete().getValue()) {
                        if (!toDelete.containsKey(finalI)) {
                            toDelete.put(finalI, finalI);
                            bindingNewItem.container.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.rounded_delete_bourder));
                            return;
                        }
                        toDelete.remove(finalI);
                        bindingNewItem.container.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.rounded_border));
                        if (toDelete.size() == 0) {
                            bindingAddItem.itemImage.setRotation(0);
                            toDelete.clear();
                            mViewModel.setToDelete(false);
                        }
                        return;
                    }
                    new ViewModelProvider(requireActivity()).get(SlotViewModel.class).reInit();
                    new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
                    Bundle bundle = new Bundle();
                    bundle.putInt("slotPos", finalI);
                    Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.slotInfoFragment, bundle);
                });
                bindingNewItem.getRoot().setOnLongClickListener(view1 -> {
                    if (Boolean.TRUE.equals(mViewModel.getToDelete().getValue())) {
                        return false;
                    }
                    mViewModel.setToDelete(true);
                    toDelete.put(finalI, finalI);
                    bindingAddItem.itemImage.setRotation(45f);
                    bindingNewItem.container.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.rounded_delete_bourder));
                    bindingAddItem.getRoot().setOnClickListener(view2 -> {
                        for (int j = mViewModel.getSlotList().getValue().size() - 1; j >= 0; --j) {
                            if (toDelete.containsKey(j)) {
                                mViewModel.getSlotList().getValue().remove(j);
                            }
                        }
                        mViewModel.setIsChanged(true);
                        mViewModel.setToDelete(false);
                        toDelete.clear();
                        bindingAddItem.itemImage.setRotation(0);
                        mViewModel.setSlotList(mViewModel.getSlotList().getValue());
                        bindingAddItem.getRoot().setOnClickListener(v -> {
                            mViewModel.setToDelete(true);
                            mViewModel.getSlotList().getValue().add(new Slot(0, "", "", false, mSelectionViewModel.getCharacterList().getValue().get(position).getId(), -1));
                            mViewModel.setSlotList(mViewModel.getSlotList().getValue());
                        });
                    });
                    return true;
                });
            }
        });
        //mSelectionViewModel.getCharacterList().getValue().get(position).getSlotList()
        List<Slot> slots = new ArrayList<>();
        for (Slot slot: mSelectionViewModel.getCharacterList().getValue().get(position).getSlotList()) {
            Slot newSlot = new Slot(slot.getId(),
                    slot.getName(),
                    slot.getIconUrl(),
                    slot.isWhitelisted(),
                    slot.getCharacterId(),
                    slot.getItemId());
            newSlot.setTags(new ArrayList<>(slot.getTags()));
            slots.add(newSlot);
        }
        mViewModel.setSlotList(slots);


        return binding.getRoot();
    }

}