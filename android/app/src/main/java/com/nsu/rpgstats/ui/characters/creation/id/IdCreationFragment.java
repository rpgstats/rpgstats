package com.nsu.rpgstats.ui.characters.creation.id;

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
import com.nsu.rpgstats.databinding.FragmentIdCreationBinding;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;

public class IdCreationFragment extends Fragment {

    private SelectionViewModel mViewModel;
    private FragmentIdCreationBinding binding;

    public static IdCreationFragment newInstance() {
        return new IdCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentIdCreationBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);

        //todo versions;

        binding.cNextButton.setOnClickListener(view -> {
            Character character = mViewModel.getCharacterById(Integer.parseInt(binding.CharId.getText().toString()));
            if (binding.CharName.length() == 0 || character == null) {
                return;
            }
            //todo proverka na oshibku Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.errorNoCharacterInSystemFragment); сделать видимым
            mViewModel.addCharacter(binding.CharName.getText().toString(), character);
        });

        binding.cBackButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectedCharacter);
        });

        return binding.getRoot();
    }



}