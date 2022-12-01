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

import java.util.ArrayList;
import java.util.List;

public class SlotsFragment extends Fragment {

    private SlotsViewModel mViewModel;
    private SelectionViewModel mSelectionViewModel;
    private FragmentSlotsBinding binding;
    private int position;
    private ImageView addSlot;
    private List<ImageView> slotViews;

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

        ImageView newImageView = (ImageView)LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.slot_item, binding.Constraint, false);
        newImageView.setId(View.generateViewId());
        binding.Constraint.addView(newImageView);
        binding.flow.addView(newImageView);
        addSlot = newImageView;
        SlotItemBinding bindingAddItem = SlotItemBinding.bind(newImageView);

        bindingAddItem.getRoot().setOnClickListener(view -> {
            mViewModel.getSlotList().getValue().add(new Slot(-1, "", "", false, mSelectionViewModel.getCharacterList().getValue().get(position).getId(), -1));
            mViewModel.setSlotList(mViewModel.getSlotList().getValue());
        });

        binding.BackButton.setOnClickListener(view -> {
            if (mViewModel.getIsChanged().getValue()) {
                new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
                Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.slotWarningUnsaveFragment);
                return;
            }
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment);
        });

        binding.SaveButton.setOnClickListener(view -> {
            mSelectionViewModel.saveSlots(mViewModel.getSlotList().getValue(), mSelectionViewModel.getCharacterList().getValue().get(position).getId(), position);
        });

        position = getArguments().getInt("position");
        mViewModel.getSlotList().observe(getViewLifecycleOwner(), slots -> {
            for (View view: slotViews) {
                binding.Constraint.removeView(view);
                binding.flow.removeView(view);
            }
            slotViews = new ArrayList<>();
            for (int i = 0; i < mViewModel.getSlotList().getValue().size(); ++i) {
                ImageView view = (ImageView)LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.slot_item, binding.Constraint, false);
                view.setId(View.generateViewId());
                binding.Constraint.addView(view);
                binding.flow.addView(view);
                addSlot = view;
                SlotItemBinding bindingNewItem = SlotItemBinding.bind(view);
                mViewModel.loadImage(bindingNewItem, mViewModel.getSlotList().getValue().get(i).getIconUrl());
                int finalI = i;
                bindingNewItem.getRoot().setOnClickListener(v -> {
                    new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
                    Bundle bundle = new Bundle();
                    bundle.putInt("slotPos", finalI);
                    Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.slotInfoFragment);
                });
            }
        });
        mViewModel.setSlotList(new ArrayList<>(mSelectionViewModel.getCharacterList().getValue().get(position).getSlotList()));


        return binding.getRoot();
    }

}