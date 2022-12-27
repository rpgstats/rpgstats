package com.nsu.rpgstats.ui.characters.creation.file;

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
import com.nsu.rpgstats.databinding.FragmentFileCreationBinding;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;

public class FileCreationFragment extends Fragment {

    private FileCreationViewModel mViewModel;
    private FragmentFileCreationBinding binding;
    private SelectionViewModel mSelectionViewModel;


    public static FileCreationFragment newInstance() {
        return new FileCreationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentFileCreationBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(FileCreationViewModel.class);
        mViewModel.reInit();
        mSelectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        mViewModel.getFilename().observe(getViewLifecycleOwner(), filename -> {
            binding.filename.setText(filename != null ? filename: "");
        });


        //todo versions;

        binding.cNextButton.setOnClickListener(view -> {
            Character character = null;
            try {
                character = mViewModel.getCharacter(getContext());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (binding.inputText.length() == 0 || character == null) {
                return;
            }
            //todo proverka na oshibku Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.errorNoCharacterInSystemFragment); сделать видимм
            mSelectionViewModel.addCharacter(binding.inputText.getText().toString(), character);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        binding.cBackButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        binding.FileName.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("file/*");
            ((RpgstatsApplication)requireActivity().getApplication()).appContainer.fileActivityLauncher.launch(intent);
        });

        return binding.getRoot();
    }

}