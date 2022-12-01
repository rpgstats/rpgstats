package com.nsu.rpgstats.ui.characters.selection;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.entities.Character;
import com.nsu.rpgstats.databinding.CharacterCardBinding;

import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterHolder> {

    private List<Character> characterList;
    private ClickListener listener;

    public void setCharacterList(List<Character> characterList) {
        if (this.characterList == null) {
            this.characterList = characterList;
            notifyItemRangeInserted(0, characterList.size());
        } else {
            this.characterList = characterList;
            notifyDataSetChanged();
        }
    }


    public CharacterListAdapter(List<Character> characterList, ClickListener listener) {
        setCharacterList(characterList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharacterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CharacterCardBinding binding = CharacterCardBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CharacterHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterHolder holder, int position) {
        holder.binding.CName.setText(characterList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class CharacterHolder extends RecyclerView.ViewHolder {
        public CharacterCardBinding binding;

        public CharacterHolder(@NonNull CharacterCardBinding binding, ClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(view -> {
                listener.onClickListener(getAdapterPosition());
            });
        }
    }

    public static interface ClickListener {
        public void onClickListener(int position);
    }
}
