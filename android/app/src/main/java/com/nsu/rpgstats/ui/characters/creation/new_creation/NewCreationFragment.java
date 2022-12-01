package com.nsu.rpgstats.ui.characters.creation.new_creation;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.databinding.FragmentNewCreationBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;

public class NewCreationFragment extends Fragment {

    private NewCreationViewModel mViewModel;
    private FragmentNewCreationBinding binding;
    private SelectionViewModel mSelectionViewModel;
    private GameSystemsViewModel mGameSystemsModel;
    private Integer userId;

    private Integer systemPosition = null;

    public static NewCreationFragment newInstance() {
        return new NewCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentNewCreationBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(NewCreationViewModel.class);
        mViewModel.reInit();
        mSelectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        mGameSystemsModel = new ViewModelProvider(requireActivity()).get(GameSystemsViewModel.class);
        userId = 0; //getUserId;

        mViewModel.getImageFilename().observe(getViewLifecycleOwner(), filename -> {
            binding.imageName.setText(filename != null ? filename: "");
        });

        mViewModel.getBackgroundFilename().observe(getViewLifecycleOwner(), filename -> {
            binding.imageName.setText(filename != null ? filename: "");
        });

        binding.cNextButton.setOnClickListener(view -> {
            if (binding.charName.length() == 0 || systemPosition == null) {
                return;
            }
            //image and background?
            GameSystem gs = mGameSystemsModel.getGameSystems().getValue().get(systemPosition);
            Character character = new Character(0, binding.charName.getText().toString(), "", gs.getId(), gs.getGameSessionNumber(), userId);
            mSelectionViewModel.addCharacter(binding.charName.getText().toString(), character);
        });

        binding.cBackButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectedCharacter);
        });

        binding.imageForm.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            ((RpgstatsApplication)requireActivity().getApplication()).appContainer.iconActivityLauncher.launch(intent);
        });

        binding.backgroundForm.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            ((RpgstatsApplication)requireActivity().getApplication()).appContainer.backgroundActivityLauncher.launch(intent);
        });

        return binding.getRoot();
    }
}