package com.nsu.rpgstats.ui.sessions;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mikepenz.fastadapter.binding.AbstractBindingItem;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.SessionCharacterItemBinding;
import com.nsu.rpgstats.entities.SessionCharacter;

import java.util.List;

public class SessionCharacterItem extends AbstractBindingItem<SessionCharacterItemBinding> {
    private SessionCharacter character;

    public SessionCharacterItem(SessionCharacter sessionCharacter) {
        this.character = sessionCharacter;
    }


    @NonNull
    @Override
    public SessionCharacterItemBinding createBinding(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup) {
        return SessionCharacterItemBinding.inflate(layoutInflater, viewGroup, false);
    }

    @Override
    public void bindView(@NonNull SessionCharacterItemBinding binding, @NonNull List<?> payloads) {
        binding.characterNameTextView.setText(character.getCharacterName());
        binding.usernameNameTextView.setText(character.getUserName());
    }

    @Override
    public int getType() {
        return R.id.simpleRecyclerView;
    }


}
