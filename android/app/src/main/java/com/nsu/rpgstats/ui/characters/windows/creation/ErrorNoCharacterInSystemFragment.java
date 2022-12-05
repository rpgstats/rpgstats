package com.nsu.rpgstats.ui.characters.windows.creation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentAddCharacterBinding;
import com.nsu.rpgstats.databinding.FragmentErrorNoCharacterInSystemBinding;

public class ErrorNoCharacterInSystemFragment extends Fragment {
    private FragmentErrorNoCharacterInSystemBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentErrorNoCharacterInSystemBinding.inflate(inflater, container, false);
        binding.getRoot().setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.emptyFragment);
        });

        return binding.getRoot();
    }
}