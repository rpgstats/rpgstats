package com.nsu.rpgstats.ui.characters.creation.new_creation;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.databinding.FragmentNewCreationBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;
import com.nsu.rpgstats.ui.gamesystems.GameSystemsAdapter;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class NewCreationFragment extends Fragment {

    private NewCreationViewModel mViewModel;
    private FragmentNewCreationBinding binding;
    private SelectionViewModel mSelectionViewModel;
    private GameSystemsViewModel mGameSystemsModel;
    private Integer userId;

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
            binding.bacgroundImage.setText(filename != null ? filename: "");
        });

        mViewModel.getGameSystem().observe(getViewLifecycleOwner(), gs -> {
            binding.GSname.setText(gs.getName());
        });

        binding.cNextButton.setOnClickListener(view -> {
            if (binding.charName.getText().length() == 0 || mViewModel.getGameSystem().getValue() == null) {
                return;
            }

            GameSystem gs = mViewModel.getGameSystem().getValue();
            Character character = new Character(0, binding.charName.getText().toString(), "", gs.getId(), gs.getGameSessionNumber(), userId);
            BitmapFactory.Options options = new BitmapFactory.Options();
            if (mViewModel.getPathIcon().getValue() != null) {
                try (InputStream file = getContext().getContentResolver().openInputStream(mViewModel.getPathIcon().getValue())) {
                    Bitmap bitmap = BitmapFactory.decodeStream(file, null, options);
                    character.setIcon(bitmap);
                    if (bitmap != null) {
                        Toast.makeText(getContext(), "Loaded icon", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to load icon", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Failed to load icon", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            options = new BitmapFactory.Options();
            if (mViewModel.getPathBackground().getValue() != null) {
                try (InputStream file = getContext().getContentResolver().openInputStream(mViewModel.getPathBackground().getValue())) {
                    Bitmap bitmap = BitmapFactory.decodeStream(file, null, options);
                    character.setBackground(bitmap);
                    if (bitmap != null) {
                        Toast.makeText(getContext(), "Loaded background", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to load background", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    Toast.makeText(getContext(), "Failed to load background", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            mSelectionViewModel.addCharacter(binding.charName.getText().toString(), character);
            Bundle bundle = new Bundle();
            bundle.putInt("position", mSelectionViewModel.getCharacterList().getValue().size() - 1);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.infoFragment, bundle);
        });

        binding.cBackButton.setOnClickListener(view -> {
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
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

        binding.GameSystemList.setOnClickListener(view -> {
            binding.GSList.setVisibility(View.VISIBLE);
        });

        binding.GSList.setOnClickListener(view -> {
            binding.GSList.setVisibility(View.GONE);
        });

        GameSystemsViewModel viewModel = new ViewModelProvider(requireActivity()).get(GameSystemsViewModel.class);
        GameSystemsAdapter adapter = new GameSystemsAdapter(viewModel.getGameSystems().getValue(), position -> {
            mViewModel.setGameSystem(viewModel.getGameSystems().getValue().get(position));
            binding.GSList.setVisibility(View.GONE);
        });
        binding.GSRecycerView.setAdapter(adapter);
        binding.GSRecycerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return binding.getRoot();
    }
}