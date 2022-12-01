package com.nsu.rpgstats.ui.characters.info;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.databinding.FragmentInfoBinding;
import com.nsu.rpgstats.ui.characters.WindowViewModel;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;

public class InfoFragment extends Fragment {

    private SelectionViewModel mViewModel;
    private InfoViewModel mInfoViewModel;
    private FragmentInfoBinding binding;
    private Character character;
    private int position;

    private AttributeAdapter adapter;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        mInfoViewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);
        mInfoViewModel.reInit();
        mInfoViewModel.getIsChanged().observe(getViewLifecycleOwner(), isChanged -> {
            if (isChanged) {
                binding.deleteText.setText("Save");
                binding.cDeleteButton.setOnClickListener(view -> {
                    mViewModel.editCharacter(character.getId(), character, position);
                });
            }
        });

        binding.charName.setText(character.getName());
        binding.charName.setOnClickListener(view -> {
            mInfoViewModel.setIsChanged(true);
        });

        binding.charDesc.setText(character.getDescription());
        binding.charDesc.setOnClickListener(view -> {
            mInfoViewModel.setIsChanged(true);
        });

        adapter = new AttributeAdapter(mInfoViewModel.getAttributeList().getValue(), pos -> {
            mInfoViewModel.setIsChanged(true);
        });

        binding.cBackButton.setOnClickListener(view -> {
            if (mInfoViewModel.getIsChanged().getValue()) {
                new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
                Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.unsaveWarningFragment);
                return;
            }
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        binding.cDeleteButton.setOnClickListener(view -> {
            mViewModel.deleteCharacter(position);
        });

        binding.exportButton.setOnClickListener(view -> {
            new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.exportCharacterFragment);
        });

        binding.slotButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", character.getId());
            bundle.putInt("position", position);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        new ViewModelProvider(requireActivity()).get(GameSystemsViewModel.class);
        position = getArguments().getInt("position");
        Character character = mViewModel.getCharacterList().getValue().get(position);
        this.character = new Character(
                character.getId(),
                character.getName(),
                character.getDescription(),
                character.getGameSystemId(),
                character.getSessionId(),
                character.getUserId());

        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}