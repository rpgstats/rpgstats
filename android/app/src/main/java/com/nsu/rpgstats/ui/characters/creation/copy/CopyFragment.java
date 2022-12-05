package com.nsu.rpgstats.ui.characters.creation.copy;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.FragmentCopyBinding;
import com.nsu.rpgstats.ui.characters.selection.CharacterListAdapter;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;

public class CopyFragment extends Fragment {

    private CopyViewModel mViewModel;
    private SelectionViewModel mSelectionViewModel;
    private CharacterListAdapter adapter;
    private FragmentCopyBinding binding;
    private Integer position = null;

    public static CopyFragment newInstance() {
        return new CopyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCopyBinding.inflate(inflater, container, false);
        mSelectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        mSelectionViewModel.loadData(1, getContext()); //todo getUserId
        mViewModel = new ViewModelProvider(requireActivity()).get(CopyViewModel.class);
        mViewModel.reInit();
        mViewModel.loadData(1); //todo charId
        mSelectionViewModel.getCharacterList().observe(getViewLifecycleOwner(), characterList -> {
            adapter.setCharacterList(characterList);
        });

        adapter = new CharacterListAdapter(mSelectionViewModel.getCharacterList().getValue(), position -> {
            binding.charList.setVisibility(View.GONE);
            this.position = position;
            binding.selectedCharacter.setText(mSelectionViewModel.getCharacterList().getValue().get(position).getName());
        });

        binding.characterList.setAdapter(adapter);
        binding.characterList.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.GameSystemList.setOnClickListener(view -> {
            binding.charList.setVisibility(View.VISIBLE);
        });

        //todo versions;

        binding.cNextButton.setOnClickListener(view -> {
            mSelectionViewModel.addCharacter(binding.InputText.getText().toString() ,mSelectionViewModel.getCharacterList().getValue().get(position));
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        binding.cBackButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        return binding.getRoot();
    }



}