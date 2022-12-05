package com.nsu.rpgstats.ui.characters.windows.info.slots;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentSlotsWarningUnsaveBinding;

public class SlotsWarningUnsaveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSlotsWarningUnsaveBinding binding = FragmentSlotsWarningUnsaveBinding.inflate(inflater, container, false);
        binding.No.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.emptyFragment);
        });

        binding.Yes.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment, getArguments());
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.emptyFragment);
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}