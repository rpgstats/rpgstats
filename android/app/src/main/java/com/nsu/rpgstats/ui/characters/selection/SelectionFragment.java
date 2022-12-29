package com.nsu.rpgstats.ui.characters.selection;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.FragmentSelectionBinding;
import com.nsu.rpgstats.ui.characters.BackgroundViewModel;
import com.nsu.rpgstats.ui.characters.WindowViewModel;

public class SelectionFragment extends Fragment {

    private SelectionViewModel mViewModel;
    private FragmentSelectionBinding binding;
    private CharacterListAdapter adapter;

    public static SelectionFragment newInstance() {
        return new SelectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSelectionBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);

        mViewModel.getCharacterList().observe(getViewLifecycleOwner(), characterList -> {
            adapter.setCharacterList(characterList);
            mViewModel.saveCharacters(requireActivity());
        });
        mViewModel.loadData( ((RpgstatsApplication)requireActivity().getApplication()).appContainer.currentUser.getId() , getContext());

        adapter = new CharacterListAdapter(mViewModel.getCharacterList().getValue(), position -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", mViewModel.getCharacterList().getValue().get(position).getId());
            bundle.putInt("position", position);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment, bundle);
        });
        binding.characterList.setAdapter(adapter);
        binding.characterList.setLayoutManager(new LinearLayoutManager(getContext()));



        binding.ButtonFromFragment.setOnClickListener(view -> {
            new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.addCharacterFragment);
        });

        BackgroundViewModel model = new ViewModelProvider(requireActivity()).get(BackgroundViewModel.class);
        model.setIcon(null);
        model.setBackground(BitmapFactory.decodeResource(getResources(), R.color.black));
        model.setName("");

        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        mViewModel.saveCharacters(requireActivity());
        super.onDestroy();
    }
}