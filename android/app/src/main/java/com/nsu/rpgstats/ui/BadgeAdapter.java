package com.nsu.rpgstats.ui;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.GameSystemItemBinding;
import com.nsu.rpgstats.databinding.ItemsBadgeBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Identifiable;

import java.util.List;
import java.util.Objects;

public class BadgeAdapter<T extends Identifiable> extends RecyclerView.Adapter<BadgeAdapter.BadgeHolder> {
    private List<T> mBadges;
    private boolean showDeleteImage;
    private final BadgeAdapter.OnBadgeClickListener mOnBadgeClickListener;
    private Drawable background;

    public BadgeAdapter(List<T> badges, BadgeAdapter.OnBadgeClickListener mOnBadgeClickListener, boolean showDeleteImage, Drawable background) {
        setBadgesList(badges);
        // for getItemId -- to get unique id of any element in the list
        setHasStableIds(true);
        this.showDeleteImage = showDeleteImage;
        this.background = background;
        this.mOnBadgeClickListener = mOnBadgeClickListener;
    }

    public void setBadgesList(final List<T> badges) {
        if (mBadges == null) {
            mBadges = badges;
            notifyItemRangeInserted(0, badges.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mBadges.size();
                }

                @Override
                public int getNewListSize() {
                    return badges.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mBadges.get(oldItemPosition) == badges.get(newItemPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mBadges.get(oldItemPosition).getId(), badges.get(newItemPosition).getId());
                }

            });
            mBadges = badges;
            Log.e("result", String.valueOf(result));
            result.dispatchUpdatesTo(this);
            // todo: dispatching not working, need to explicity notify adapter
            notifyDataSetChanged();
        }
    }

    // This will reduce the blinking effect on dataset notify, where it modifies only items with changes.
    @Override
    public long getItemId(int position) {
        // ids should be unique in the list!
        return mBadges.get(position).getId();
    }

    @NonNull
    @Override
    public BadgeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsBadgeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_badge, parent, false);
        return new BadgeHolder(binding, mOnBadgeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BadgeHolder holder, int position) {
        holder.binding.Text.setText(mBadges.get(position).toString());
        if (!showDeleteImage) {
            holder.binding.DeleteImage.setVisibility(ImageView.GONE);
        }
        holder.binding.Background.setBackground(background);
    }

    @Override
    public int getItemCount() {
        return mBadges.size();
    }

    public static class BadgeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        BadgeAdapter.OnBadgeClickListener onBadgeClickListener;
        ItemsBadgeBinding binding;

        public BadgeHolder(ItemsBadgeBinding binding, BadgeAdapter.OnBadgeClickListener onBadgeClickListener) {
            super(binding.getRoot());
            this.onBadgeClickListener = onBadgeClickListener;
            this.binding = binding;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onBadgeClickListener.onBadgeClick(getAdapterPosition());
        }
    }


    public interface OnBadgeClickListener {
        void onBadgeClick(int position);
    }
}
