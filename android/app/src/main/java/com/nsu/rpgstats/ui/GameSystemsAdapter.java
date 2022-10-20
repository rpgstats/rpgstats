package com.nsu.rpgstats.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.GameSystemItemBinding;
import com.nsu.rpgstats.entities.GameSystem;

import java.util.List;
import java.util.Objects;

public class GameSystemsAdapter extends RecyclerView.Adapter<GameSystemsAdapter.GameSystemHolder> {

    private List<GameSystem> mGameSystems;
    private final OnGameSystemClickListener mOnGameSystemClickListener;

    public GameSystemsAdapter(List<GameSystem> gameSystem, OnGameSystemClickListener mOnGameSystemClickListener) {
        setGameSystemsList(gameSystem);
        // for getItemId -- to get unique id of any element in the list
        setHasStableIds(true);
        this.mOnGameSystemClickListener = mOnGameSystemClickListener;
    }

    public void setGameSystemsList(final List<GameSystem> gameSystems) {
        if (mGameSystems == null) {
            mGameSystems = gameSystems;
            notifyItemRangeInserted(0, gameSystems.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mGameSystems.size();
                }

                @Override
                public int getNewListSize() {
                    return gameSystems.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mGameSystems.get(oldItemPosition) == gameSystems.get(newItemPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mGameSystems.get(oldItemPosition).getId(), gameSystems.get(newItemPosition).getId());
                }

            });
            mGameSystems = gameSystems;
            Log.e("result", String.valueOf(result));
            result.dispatchUpdatesTo(this);
            // todo: dispatching not working, need to explicity notify adapter
            notifyItemInserted(mGameSystems.size() - 1);
        }
    }

    // This will reduce the blinking effect on dataset notify, where it modifies only items with changes.
    @Override
    public long getItemId(int position) {
        // ids should be unique in the list!
        return mGameSystems.get(position).getId();
    }

    public static class GameSystemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        OnGameSystemClickListener onGameSystemListener;
        GameSystemItemBinding binding;

        public GameSystemHolder(@NonNull GameSystemItemBinding binding, OnGameSystemClickListener onGameSystemListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onGameSystemListener = onGameSystemListener;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGameSystemListener.onGameSystemClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public GameSystemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        GameSystemItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.game_system_item, viewGroup, false);
        return new GameSystemHolder(binding, mOnGameSystemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GameSystemHolder holder, int position) {
        holder.binding.setGameSystem(mGameSystems.get(position));
    }

    @Override
    public int getItemCount() {
        return mGameSystems.size();
    }

    public interface OnGameSystemClickListener {
        void onGameSystemClick(int position);

    }

}
