package com.nsu.rpgstats.ui.characters.windows.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentDeleteCharacterWarningBinding;
import com.nsu.rpgstats.databinding.FragmentSlotWarningUnsaveBinding;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;

public class DeleteCharacterWarningFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDeleteCharacterWarningBinding binding = FragmentDeleteCharacterWarningBinding.inflate(inflater, container, false);
        binding.No.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.emptyFragment);
        });

        binding.Yes.setOnClickListener(view -> {
            SelectionViewModel model = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
            assert getArguments() != null;
            model.deleteCharacter(getArguments().getInt("position"));
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.emptyFragment);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}