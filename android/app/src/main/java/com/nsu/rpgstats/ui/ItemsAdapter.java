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
import com.nsu.rpgstats.databinding.ItemsItemBinding;
import com.nsu.rpgstats.entities.Item;

import java.util.List;
import java.util.Objects;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {

    private List<Item> mItemList;
    private final OnItemClickListener mOnItemClickListener;


    public ItemsAdapter(List<Item> itemList, ItemsAdapter.OnItemClickListener mOnItemClickListener) {
        setItemList(itemList);
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
                    return itemList.size();
                }

                @Override
                public int getNewListSize() {
                    return itemList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return itemList.get(oldItemPosition) == itemList.get(newItemPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return Objects.equals(itemList.get(oldItemPosition).getId(), itemList.get(newItemPosition).getId());
                }

            });
            mItemList = itemList;
            Log.e("result", String.valueOf(result));
            result.dispatchUpdatesTo(this);
            // todo: dispatching not working, need to explicity notify adapter
            notifyItemInserted(mItemList.size() - 1);
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

        public ItemsHolder(@NonNull ItemsItemBinding binding, ItemsAdapter.OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onItemClickListener = onItemClickListener;

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
