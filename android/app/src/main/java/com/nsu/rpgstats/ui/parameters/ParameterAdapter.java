package com.nsu.rpgstats.ui.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.databinding.ParamsItemBinding;
import com.nsu.rpgstats.entities.Parameter;

import java.util.Collections;
import java.util.List;


public class ParameterAdapter extends RecyclerView.Adapter<ParameterAdapter.ParametersViewHolder> {

    List<Parameter> mParams = Collections.emptyList();

    void setParams(List<Parameter> params){
        Log.i("TAG", "params changed locally");
        mParams = params;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParametersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ParamsItemBinding binding = ParamsItemBinding.inflate(inflater, parent, false);

        return new ParametersViewHolder(binding, inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ParametersViewHolder holder, int position) {
        Parameter param = mParams.get(position);
        holder.bindData(param);
        holder.setClick(position, paramToBundle(param));
    }

    @Override
    public int getItemCount() {
        if(mParams == null){
            return 0;
        }
        return mParams.size();
    }

    public static class ParametersViewHolder extends RecyclerView.ViewHolder {
        private final ParamsItemBinding binding;
        private final LayoutInflater inflater;

        public ParametersViewHolder(ParamsItemBinding bind, LayoutInflater inflater) {
            super(bind.getRoot());
            binding = bind;
            this.inflater = inflater;
        }


        public void bindData(Parameter param) {
            binding.paramCreated.setText(param.getCreatedAt().toString());
            binding.paramName.setText(param.getName());
        }

        public void setClick(int position, Bundle bundle){
            binding.Background.setOnClickListener(
                    event -> {
                Intent i = new Intent(binding.getRoot().getContext(), ParameterDetailsActivity.class);
                i.putExtras(bundle);
                binding.getRoot().getContext().startActivity(i);
            });
        }
    }

    private Bundle paramToBundle(Parameter param){
        Bundle b = new Bundle();
        b.putInt("id", param.getId() == null ? 0 : param.getId());
        b.putString("name", param.getName());
        b.putString("date", param.getCreatedAt().toString());
        b.putInt("min", param.getMin() == null ? 0 : param.getMin());
        b.putInt("max", param.getMax() == null ? 0 : param.getMax());
        return b;
    }

}
