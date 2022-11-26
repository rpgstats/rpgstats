package com.nsu.rpgstats.ui.sessions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.SessionItemBinding;
import com.nsu.rpgstats.entities.Session;

import java.util.List;
import java.util.Objects;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionHolder> {

    private List<Session> mGameSystems;
    private final OnSessionClickListener onSessionClickListener;

    public SessionsAdapter(List<Session> gameSystem, OnSessionClickListener onSessionClickListener) {
        setGameSystemsList(gameSystem);
        // for getItemId -- to get unique id of any element in the list
        setHasStableIds(true);
        this.onSessionClickListener = onSessionClickListener;
    }

    public void setGameSystemsList(final List<Session> gameSystems) {
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
            notifyItemInserted(mGameSystems.size() - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return mGameSystems.get(position).getId();
    }

    public static class SessionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnSessionClickListener onSessionClickListener;

        SessionItemBinding binding;

        public SessionHolder(@NonNull SessionItemBinding binding, OnSessionClickListener onSessionClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onSessionClickListener = onSessionClickListener;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onSessionClickListener.onSessionClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public SessionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        SessionItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.game_system_item, viewGroup, false);
        return new SessionHolder(binding, onSessionClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionHolder holder, int position) {
        holder.binding.setSession(mGameSystems.get(position));
    }

    @Override
    public int getItemCount() {
        return mGameSystems.size();
    }

    public interface OnSessionClickListener {
        void onSessionClick(int position);

    }

}
