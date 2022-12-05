package com.nsu.rpgstats.ui.characters.windows;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentEmptyBinding;
import com.nsu.rpgstats.ui.characters.WindowViewModel;

public class EmptyFragment extends Fragment {

    FragmentEmptyBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmptyBinding.inflate(inflater, container, false);
        new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}