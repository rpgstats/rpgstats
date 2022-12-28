package com.nsu.rpgstats.ui.characters.info;

import static android.content.Context.CLIPBOARD_SERVICE;

import androidx.lifecycle.ViewModelProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
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

import com.nsu.rpgstats.AppContainer;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.entities.Attribute;
import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.databinding.FragmentInfoBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.entities.Slot;
import com.nsu.rpgstats.ui.characters.BackgroundViewModel;
import com.nsu.rpgstats.ui.characters.WindowViewModel;
import com.nsu.rpgstats.ui.characters.selection.SelectionViewModel;
import com.nsu.rpgstats.viewmodel.GameSystemInfoViewModel;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoFragment extends Fragment {

    private SelectionViewModel mViewModel;
    private InfoViewModel mInfoViewModel;
    private FragmentInfoBinding binding;
    private Character character;
    private int position;

    private AttributeAdapter adapter;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        position = getArguments().getInt("position");
        binding = FragmentInfoBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);
        mInfoViewModel = new ViewModelProvider(requireActivity()).get(InfoViewModel.class);
        Character character = mViewModel.getCharacterList().getValue().get(position);

        List<Parameter> parameters = ((RpgstatsApplication) requireActivity().getApplication()).appContainer.parameterRepository.getParameters(character.getGameSystemId()).getValue();
        if (parameters != null) {
            character.setAttributeList(new ArrayList<>());
            for (Parameter parameter : parameters) {
                List<Modifier> modifierList = new ArrayList<>();
                for (Slot slot : character.getSlotList()) {
                    if (slot.getItem() != null) {
                        for (Modifier modifier : slot.getItem().getModifiers()) {
                            if (Objects.equals(modifier.getParameter().getId(), parameter.getId()) && Objects.equals(modifier.getParameter().getName(), parameter.getName())) {
                                modifierList.add(modifier);
                            }
                        }
                    }
                }
                Attribute attribute = new Attribute(parameter.getId(), parameter.getName(), true, character.getGameSystemId(), parameter);
                attribute.setModifierList(modifierList);
                character.getAttributeList().add(attribute);
            }
        }



        this.character = new Character(
                character.getId(),
                character.getName(),
                character.getDescription(),
                character.getGameSystemId(),
                character.getSessionId(),
                character.getUserId());

        mInfoViewModel.reInit();
        mInfoViewModel.setGSID(character.getGameSystemId());
        mInfoViewModel.getIsChanged().observe(getViewLifecycleOwner(), isChanged -> {
            if (isChanged) {
                binding.deleteText.setText("Save");
                binding.cDeleteButton.setOnClickListener(view -> {
                    character.setName(binding.charName.getText().toString());
                    character.setDescription(binding.charDesc.getText().toString());
                    mViewModel.editCharacter(character.getId(), character, position);
                    Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
                });

            }
        });
        List<GameSystem> gs = new ViewModelProvider(requireActivity()).get(GameSystemsViewModel.class).getGameSystems().getValue();
        GameSystem currentGS = null;
        for (int i = 0; i < gs.size(); ++i) {
            if (gs.get(i).getId() == character.getGameSystemId()) {
                currentGS = gs.get(i);
                break;
            }
        }
        binding.GSName.setText(currentGS == null ? "": currentGS.getName());
        Log.d("Name", "onCreateView: " + character.getDescription() );

        binding.charName.setText(character.getName());
        binding.charName.setOnClickListener(view -> {
            mInfoViewModel.setIsChanged(true);
        });

        binding.charDesc.setText(character.getDescription());
        binding.charDesc.setOnClickListener(view -> {
            mInfoViewModel.setIsChanged(true);
        });

        adapter = new AttributeAdapter(character.getAttributeList(), pos -> {});
        binding.Attributes.setAdapter(adapter);
        binding.Attributes.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.cBackButton.setOnClickListener(view -> {
            if (mInfoViewModel.getIsChanged().getValue()) {
                new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
                Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.unsaveWarningFragment);
                return;
            }
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.selectionFragment);
        });

        binding.cDeleteButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", character.getId());
            bundle.putInt("position", position);
            new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.deleteCharacterWarningFragment, bundle);
        });

        binding.exportButton.setOnClickListener(view -> {
            ClipboardManager clipboardManager = (ClipboardManager)(requireActivity().getSystemService(CLIPBOARD_SERVICE));
            String text = character.getId() + "";
            ClipData clipData = ClipData.newPlainText("text", text);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(requireActivity(), "Id скопирован в буффер обмена",Toast.LENGTH_SHORT).show();

            new ViewModelProvider(requireActivity()).get(WindowViewModel.class).setIsShow(true);
            Bundle bundle = new Bundle();
            bundle.putInt("id", character.getId());
            bundle.putInt("position", position);
            Navigation.findNavController(requireActivity(), R.id.windowNavHost).navigate(R.id.exportCharacterFragment, bundle);
        });

        binding.SlotButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", character.getId());
            bundle.putInt("position", position);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.slotsFragment, bundle);
        });

        binding.equipmentButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id", character.getId());
            bundle.putInt("position", position);
            Navigation.findNavController(requireActivity(), R.id.mainNavHost).navigate(R.id.characterEquipmentFragment, bundle);
        });

        new ViewModelProvider(requireActivity()).get(GameSystemsViewModel.class);

        BackgroundViewModel model = new ViewModelProvider(requireActivity()).get(BackgroundViewModel.class);
        Log.d("image", "onCreateView: " + character.getIcon());
        model.setIcon(character.getIcon());
        model.setBackground(character.getBackground());
        model.setName(character.getName());

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}