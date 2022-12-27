package com.nsu.rpgstats.ui.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.parameters.ParameterRepository;
import com.nsu.rpgstats.databinding.ActivityParametersBinding;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.ui.ManageFormMode;
import com.nsu.rpgstats.viewmodel.parameters.ParameterManageViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParametersActivity extends AppCompatActivity {
    ActivityParametersBinding binding;
    ParameterAdapter adapter;

    ParameterManageViewModel model;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ParameterManageViewModel.class);
        subscribeToViewModel(model.getParams());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recycler.setLayoutManager(layoutManager);
        setRecyclerView();

        binding.addParameterButton.setOnClickListener(view -> {
            Intent i = new Intent(this, ParameterManageActivity.class);
            i.putExtra("Mode", ManageFormMode.ADD.name());
            startActivity(i);
        });
    }

    private void subscribeToViewModel(LiveData<List<Parameter>> liveData) {
        liveData.observe(this, params -> {
            adapter.setParams(params);
        });
    }

    private void setRecyclerView() {
        if(adapter == null){
            adapter = new ParameterAdapter();
        }
        binding.recycler.setAdapter(adapter);
    }

    /*
     * кошмарный код на память
     */

//    private void fillParamList() {
//        List<Parameter> params = model.getParams().getValue(); // replace
//        final ArrayList<HashMap<String, String>> paramNameDate = new ArrayList<>();
//        HashMap<String, String> map;
//        for (Parameter param : params) {
//            map = new HashMap<>();
//            map.put("Name", param.getName());
//            map.put("Created at", param.getCreatedAt().toString());
//            paramNameDate.add(map);
//        }
//
//        adapter = new SimpleAdapter(this, paramNameDate,
//                android.R.layout.simple_list_item_2,
//                new String[]{"Name", "Created at"},
//                new int[]{android.R.id.text1, android.R.id.text2});
//
//        binding.paramListView.setAdapter(adapter);
//
//        binding.paramListView.setOnItemClickListener((parent, itemClicked, position, id) -> {
//            Intent i = new Intent(ParametersActivity.this, ParameterDetailsActivity.class);
//            i.putExtras(paramToBundle(params.get((int) id)));
//            startActivity(i);
//        });
//    }
}
