package com.nsu.rpgstats.ui.properties;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.PropertiesConstraintsRowBinding;
import com.nsu.rpgstats.entities.Constraint;
import com.nsu.rpgstats.entities.Tag;

import java.util.ArrayList;
import java.util.List;

public class PropertyConstraintsAdapter extends RecyclerView.Adapter<PropertyConstraintsAdapter.ViewHolder> {
    // Data to display
    private final List<Constraint> constraints;

    // ViewHolder defines how data will be displayed in each item of RecyclerView list
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        // Components of a single item
        final PropertiesConstraintsRowBinding binding;
        OnItemClickListener onItemClickListener;
        List<View> tags;

        public ViewHolder(@NonNull PropertiesConstraintsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.tags = new ArrayList<>();
        }

        public PropertiesConstraintsRowBinding getBinding() {
            return binding;
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public PropertyConstraintsAdapter(List<Constraint> data) {
        constraints = data;
        notifyItemRangeInserted(0, constraints.size());
    }

    @NonNull
    @Override
    public PropertyConstraintsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PropertiesConstraintsRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.properties_constraints_row, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyConstraintsAdapter.ViewHolder holder, int position) {
        holder.binding.propTagsChipGroup.removeAllViews();

        for (Tag t : constraints.get(position).getTags()) {
            View newTagView = LayoutInflater.from(holder.binding.getRoot().getContext()).inflate(R.layout.properties_constraints_row, holder.binding.propTagsChipGroup, false);
            newTagView.setId(View.generateViewId());
            holder.binding.linL.addView(newTagView);
            holder.binding.propTagsChipGroup.addView(newTagView);
            holder.tags.add(newTagView);

            Chip chip = new Chip(holder.itemView.getContext());
            chip.setText(t.getName());
            chip.setCloseIconVisible(true);
            chip.setTextColor(Color.BLACK);

            holder.binding.propTagsChipGroup.addView(chip);
        }

    }

    @Override
    public int getItemCount() {
        return constraints.size();
    }

    @Override
    public long getItemId(int position) {
        return constraints.get(position).getId();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
