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
import com.nsu.rpgstats.databinding.TagsTagBinding;
import com.nsu.rpgstats.entities.Tag;

import java.util.List;
import java.util.Objects;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.TagsHolder> {

    private List<Tag> mTagList;
    private final OnTagClickListener mOnTagClickListener;


    public TagsAdapter(List<Tag> tagList, TagsAdapter.OnTagClickListener mOnTagClickListener) {
        setTagList(tagList);
        // for getTagId -- to get unique id of any element in the list
        setHasStableIds(true);
        this.mOnTagClickListener = mOnTagClickListener;
    }

    public void setTagList(final List<Tag> tagList) {
        if (mTagList == null) {
            mTagList = tagList;
            notifyItemRangeInserted(0, tagList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return tagList.size();
                }

                @Override
                public int getNewListSize() {
                    return tagList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldTagPosition, int newTagPosition) {
                    return tagList.get(oldTagPosition) == tagList.get(newTagPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldTagPosition, int newTagPosition) {
                    return Objects.equals(tagList.get(oldTagPosition).getId(), tagList.get(newTagPosition).getId());
                }

            });
            mTagList = tagList;
            Log.e("result", String.valueOf(result));
            result.dispatchUpdatesTo(this);
            // todo: dispatching not working, need to explicity notify adapter
            notifyItemInserted(mTagList.size() - 1);
        }
    }

    @NonNull
    @Override
    public TagsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TagsTagBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.tags_tag, parent, false);

        return new TagsHolder(binding, mOnTagClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TagsHolder holder, int position) {
        holder.binding.setTag(mTagList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTagList.size();
    }

    // This will reduce the blinking effect on dataset notify, where it modifies only tags with changes.
    @Override
    public long getItemId(int position) {
        // ids should be unique in the list!
        return mTagList.get(position).getId();
    }

    public static class TagsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TagsTagBinding binding;
        OnTagClickListener onTagClickListener;

        public TagsHolder(@NonNull TagsTagBinding binding, TagsAdapter.OnTagClickListener onTagClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onTagClickListener = onTagClickListener;

            binding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTagClickListener.onTagClick(getAdapterPosition());
        }
    }

    public interface OnTagClickListener {
        void onTagClick(int position);
    }
}
