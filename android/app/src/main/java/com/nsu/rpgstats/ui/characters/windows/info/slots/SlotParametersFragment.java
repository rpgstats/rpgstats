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
import com.nsu.rpgstats.databinding.FragmentInfoBinding;
import com.nsu.rpgstats.databinding.FragmentSlotInfoBinding;
import com.nsu.rpgstats.databinding.FragmentSlotParametersBinding;
import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.entities.Tag;
import com.nsu.rpgstats.ui.BadgeAdapter;
import com.nsu.rpgstats.ui.characters.info.InfoViewModel;
import com.nsu.rpgstats.viewmodel.TagViewModel;

import java.util.ArrayList;
import java.util.List;

public class SlotParametersFragment extends Fragment {
    private FragmentSlotParametersBinding binding;
    private SlotViewModel mViewModel;
    private List<Tag> tags;
    private List<Tag> allTags;
    private BadgeAdapter<Tag> adapter;
    private BadgeAdapter<Tag> chooseAdapter;
    private boolean toShow = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSlotParametersBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SlotViewModel.class);
        tags = new ArrayList<>(mViewModel.getSlot().getValue().getTags());
        adapter = new BadgeAdapter<>(tags, (pos)->{
            tags.remove(pos);
            adapter.setBadgesList(tags);
        }, true, AppCompatResources.getDrawable(requireActivity(), R.drawable.rounded_card));

        binding.tags.setAdapter(adapter);
        binding.tags.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false));
        allTags = new ViewModelProvider(requireActivity()).get(TagViewModel.class).getTags(new ViewModelProvider(requireActivity()).get(InfoViewModel.class).getGSID()).getValue();
        new ViewModelProvider(requireActivity()).get(TagViewModel.class).getTags(new ViewModelProvider(requireActivity()).get(InfoViewModel.class).getGSID()).observe(getViewLifecycleOwner(), tags1 -> {
            chooseAdapter.setBadgesList(tags1);
            allTags = tags1;
        });

        chooseAdapter = new BadgeAdapter<>(allTags , (pos)->{
            tags.add(allTags.get(pos));
            adapter.setBadgesList(tags);
            toShow = !toShow;
            binding.tagsListC.setVisibility(toShow ? View.VISIBLE : View.GONE);
        }, false, AppCompatResources.getDrawable(requireActivity(), R.drawable.rounded_card));

        binding.buttonShowList.setOnClickListener(view -> {
            toShow = !toShow;
            binding.tagsListC.setVisibility(toShow ? View.VISIBLE : View.GONE);
            if (toShow) {
                binding.tagsChoose.setAdapter(chooseAdapter);
                binding.tagsChoose.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
            }
        });

        binding.Back.setOnClickListener(view -> {
            mViewModel.setIsChanged(true);
            mViewModel.getSlot().getValue().setTags(tags);
            mViewModel.getSlot().getValue().setWhitelisted(!binding.switch1.isChecked());
            mViewModel.setSlot(mViewModel.getSlot().getValue());
            getArguments().putBoolean("editedTags", true);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.slotInfoFragment, getArguments());
        });

        binding.switch1.setChecked(!Boolean.TRUE.equals(mViewModel.getSlot().getValue().isWhitelisted()));


        binding.tagsChoose.setAdapter(chooseAdapter);
        binding.tagsChoose.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}