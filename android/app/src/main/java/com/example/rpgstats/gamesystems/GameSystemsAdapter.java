package com.example.rpgstats.gamesystems;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rpgstats.R;
import com.example.rpgstats.entities.GameSystem;

import java.util.List;

public class GameSystemsAdapter extends RecyclerView.Adapter<GameSystemsAdapter.GameSystemHolder> {
    private final List<GameSystem> mGameSystem;
    private final OnGameSystemClickListener mOnGameSystemClickListener;

    public GameSystemsAdapter(List<GameSystem> mGameSystem, OnGameSystemClickListener mOnGameSystemClickListener) {
        this.mGameSystem = mGameSystem;
        this.mOnGameSystemClickListener = mOnGameSystemClickListener;
    }

    public static class GameSystemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView systemName;
        TextView creationDate;
        OnGameSystemClickListener onGameSystemListener;
        public GameSystemHolder(@NonNull View itemView, OnGameSystemClickListener onGameSystemListener) {
            super(itemView);
            systemName = itemView.findViewById(R.id.systemNameTextView);
            creationDate = itemView.findViewById(R.id.creationDateTextView);
            this.onGameSystemListener = onGameSystemListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGameSystemListener.onGameSystemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public GameSystemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.game_system_item, viewGroup, false);
        return new GameSystemHolder(v, mOnGameSystemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameSystemHolder holder, int position) {
        GameSystem mBindSystem = mGameSystem.get(position);
        holder.systemName.setText(mBindSystem.getSystemName());
        holder.creationDate.setText(mBindSystem.getCreationDate());
    }

    @Override
    public int getItemCount() {
        return mGameSystem.size();
    }
}
