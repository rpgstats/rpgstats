package com.nsu.rpgstats.ui.items;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ItemsBadgeBinding;
import com.nsu.rpgstats.databinding.ItemsItemBinding;
import com.nsu.rpgstats.entities.Item;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private List<Item> mItemList;
    private final OnItemClickListener mOnItemClickListener;
    private Context context;


    public ItemsAdapter(List<Item> itemList, ItemsAdapter.OnItemClickListener mOnItemClickListener, Context context) {
        setItemList(itemList);
        this.context = context;
        // for getItemId -- to get unique id of any element in the list
        setHasStableIds(true);
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setItemList(final List<Item> itemList) {
        if (mItemList == null) {
            mItemList = itemList;
            notifyItemRangeInserted(0, itemList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mItemList.size();
                }

                @Override
                public int getNewListSize() {
                    return itemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mItemList.get(oldItemPosition) == itemList.get(newItemPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(mItemList.get(oldItemPosition).getId(), itemList.get(newItemPosition).getId());
                }

            });
            Log.e("adapter",mItemList.toString());
            Log.e("adapter",itemList.toString());
            mItemList = itemList;
            Log.e("result", String.valueOf(result));
            result.dispatchUpdatesTo(this);
            // todo: dispatching not working, need to explicity notify adapter
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_item, parent, false);

        return new ItemsHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        holder.binding.setItem(mItemList.get(position));
        if (mItemList.get(position).isDeleted()){
            holder.binding.Background.setBackground(AppCompatResources.getDrawable(context, R.drawable.deleted_rounded_card));
            holder.binding.DeletedLabel.setVisibility(TextView.VISIBLE);
        }

        for (View view: holder.tags) {
            holder.binding.Constraint.removeView(view);
            holder.binding.flowTags.removeView(view);
        }

        for(Tag tag: mItemList.get(position).getTags()) {
            View newTagView = LayoutInflater.from(holder.binding.getRoot().getContext()).inflate(R.layout.items_badge, holder.binding.Constraint, false);
            newTagView.setId(View.generateViewId());
            holder.binding.Constraint.addView(newTagView);
            holder.binding.flowTags.addView(newTagView);
            holder.tags.add(newTagView);
            ItemsBadgeBinding binding = ItemsBadgeBinding.bind(newTagView);
            binding.Background.setBackground(AppCompatResources.getDrawable(context, R.drawable.rounded_white_card));
            binding.Text.setTextColor(Color.DKGRAY);
            binding.Text.setTextSize(12);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.setMargin(newTagView.getId(), ConstraintSet.START, 0);
            constraintSet.setMargin(newTagView.getId(), ConstraintSet.END, 0);
            constraintSet.setMargin(newTagView.getId(), ConstraintSet.TOP, 0);
            constraintSet.setMargin(newTagView.getId(), ConstraintSet.BOTTOM, 0);
            binding.Background.setConstraintSet(constraintSet);
            binding.DeleteImage.setVisibility(View.GONE);
            binding.Text.setText(tag.getName());
            if (mItemList.get(position).isDeleted()){
                binding.Text.setTextColor(Color.GRAY);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    // This will reduce the blinking effect on dataset notify, where it modifies only items with changes.
    @Override
    public long getItemId(int position) {
        // ids should be unique in the list!
        return mItemList.get(position).getId();
    }

    public static class ItemsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemsItemBinding binding;
        OnItemClickListener onItemClickListener;
        List<View> tags;


        public ItemsHolder(@NonNull ItemsItemBinding binding, ItemsAdapter.OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = onItemClickListener;
            this.tags = new ArrayList<>();

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
