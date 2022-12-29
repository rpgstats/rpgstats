package com.nsu.rpgstats.ui.characters.windows.info.slots;

import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentSlotInfoBinding;
import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.BadgeAdapter;
import com.nsu.rpgstats.ui.characters.info.slots.SlotsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SlotInfoFragment extends Fragment {
    private FragmentSlotInfoBinding binding;
    private SlotViewModel mViewModel;
    private Slot slot;
    private int pos;
    private BadgeAdapter<Tag> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSlotInfoBinding.inflate(inflater, container, false);
        pos = getArguments().getInt("slotPos");
        Slot slot = new ViewModelProvider(requireActivity()).get(SlotsViewModel.class).getSlotList().getValue().get(pos);
        mViewModel = new ViewModelProvider(requireActivity()).get(SlotViewModel.class);

        Slot newSlot = new Slot(slot.getId(),
                slot.getName(),
                slot.getIconUrl(),
                slot.isWhitelisted(),
                slot.getCharacterId(),
                slot.getItemId());
        if (getArguments().getBoolean("editedTags", false)) {
            new ViewModelProvider(requireActivity()).get(SlotsViewModel.class).setIsChanged(true);
            newSlot.setTags(new ArrayList<>(mViewModel.getSlot().getValue().getTags()));
        } else {
            newSlot.setTags(new ArrayList<>(slot.getTags()));
        }
        mViewModel.setSlot(newSlot);

        mViewModel.getSlot().observe(getViewLifecycleOwner(), slot1 -> {
            this.slot = slot1;
            adapter.setBadgesList(slot1.getTags());
        });

        adapter = new BadgeAdapter<>(slot.getTags(), (pos)-> {}, false, AppCompatResources.getDrawable(requireActivity(), R.drawable.rounded_card));
        binding.Back.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.emptyFragment);
            mViewModel.reInit();
        });

        binding.tags.setAdapter(adapter);
        binding.tags.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));

        binding.params.setOnClickListener(view -> {
            mViewModel.setSlot(slot);
            mViewModel.setIsChanged(true);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.slotParametersFragment, getArguments());
        });


        return binding.getRoot();
    }
}