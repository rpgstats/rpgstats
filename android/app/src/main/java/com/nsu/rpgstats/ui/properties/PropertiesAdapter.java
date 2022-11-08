package com.nsu.rpgstats.ui.properties;

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
import com.nsu.rpgstats.databinding.PropertiesPropertyBinding;
import com.nsu.rpgstats.entities.Property;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.PropertiesHolder> {
    private List<Property> mPropertyList;
    private final PropertiesAdapter.OnItemClickListener mOnItemClickListener;
    private Context context;

    public PropertiesAdapter(List<Property> propList, PropertiesAdapter.OnItemClickListener mOnItemClickListener, Context context) {
        setPropertyList(propList);
        this.context = context;
        setHasStableIds(true);
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setPropertyList(final List<Property> propList) {
        if (mPropertyList == null) {
            mPropertyList = propList;
            notifyItemRangeInserted(0, propList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mPropertyList.size();
                }

                @Override
                public int getNewListSize() {
                    return propList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldPropertyPosition, int newPropertyPosition) {
                    return mPropertyList.get(oldPropertyPosition) == propList.get(newPropertyPosition);
                }

                @Override
                public boolean areContentsTheSame(int oldPropertyPosition, int newPropertyPosition) {
                    return Objects.equals(mPropertyList.get(oldPropertyPosition).getId(), propList.get(newPropertyPosition).getId());
                }

            });
            mPropertyList = propList;
            Log.e("result", String.valueOf(result));
            result.dispatchUpdatesTo(this);
            // todo: dispatching not working, need to explicity notify adapter
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public PropertiesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PropertiesPropertyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.properties_property, parent, false);

        return new PropertiesHolder(binding, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertiesHolder holder, int position) {
        holder.binding.setProperty(mPropertyList.get(position));
        if (mPropertyList.get(position).getDeleted()){
            holder.binding.Background.setBackground(AppCompatResources.getDrawable(context, R.drawable.deleted_rounded_card));
            holder.binding.PropDeletedLabel.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mPropertyList.size();
    }

    // This will reduce the blinking effect on dataset notify, where it modifies only items with changes.
    @Override
    public long getItemId(int position) {
        // ids should be unique in the list!
        return mPropertyList.get(position).getId();
    }

    public static class PropertiesHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PropertiesPropertyBinding binding;
        PropertiesAdapter.OnItemClickListener onItemClickListener;
        List<View> tags;


        public PropertiesHolder(@NonNull PropertiesPropertyBinding binding, PropertiesAdapter.OnItemClickListener onItemClickListener) {
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
