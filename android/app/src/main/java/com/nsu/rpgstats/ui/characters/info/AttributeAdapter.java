package com.nsu.rpgstats.ui.characters.info;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.databinding.AttributeCardBinding;
import com.nsu.rpgstats.entities.Attribute;
import com.nsu.rpgstats.entities.Modifier;

import java.util.List;

public class AttributeAdapter extends RecyclerView.Adapter<AttributeAdapter.AttributeHolder> {
    private List<Attribute> attributeList;
    private ClickListener listener;

    public AttributeAdapter(List<Attribute> attributeList, ClickListener listener) {
        this.attributeList = attributeList;
        this.listener = listener;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        if (this.attributeList == null) {
            this.attributeList = attributeList;
            notifyItemRangeChanged(0, attributeList.size());
        } else {
            this.attributeList = attributeList;
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public AttributeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AttributeCardBinding binding = AttributeCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AttributeHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull AttributeHolder holder, int position) {
        holder.binding.attributeName.setText(attributeList.get(position).getName());
        int value = attributeList.get(position).getParameter().getMin();
        for (Modifier modifier : attributeList.get(position).getModifierList()) {
            value += modifier.getValue();
        }
        if (value > attributeList.get(position).getParameter().getMax()) {
            value = attributeList.get(position).getParameter().getMax();
        }
        if (value < attributeList.get(position).getParameter().getMin()) {
            value = attributeList.get(position).getParameter().getMin();
        }
        holder.binding.value.setText(value + "");
    }

    @Override
    public int getItemCount() {
        return attributeList.size();
    }

    public static class AttributeHolder extends RecyclerView.ViewHolder {

        AttributeCardBinding binding;
        public AttributeHolder(AttributeCardBinding binding, ClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }

    public static interface ClickListener {
        public void onClickListener(int position);
    }
}
