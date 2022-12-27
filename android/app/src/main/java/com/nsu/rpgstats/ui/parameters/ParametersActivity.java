package com.nsu.rpgstats.ui.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.parameters.ParameterRepository;
import com.nsu.rpgstats.databinding.ActivityParametersBinding;
import com.nsu.rpgstats.entities.Parameter;
import com.nsu.rpgstats.ui.ManageFormMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParametersActivity extends Activity {
    ActivityParametersBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ParameterRepository parameterRepository = ((RpgstatsApplication) getApplication()).appContainer.parameterRepository;

        fillParamList(parameterRepository.getParameters(0));

        Button addParameter = findViewById(R.id.addParameterButton);
        addParameter.setOnClickListener(view -> {
            Intent i = new Intent(this, ParameterManageActivity.class);
            i.putExtra("Mode", ManageFormMode.ADD.name());
            startActivity(i);
            Snackbar.make(view, "Parameter created", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    private void fillParamList(List<Parameter> params) {
        ListView listView = findViewById(R.id.paramListView);
        final ArrayList<HashMap<String, String>> paramNameDate = new ArrayList<>();
        HashMap<String, String> map;
        for (Parameter param : params) {
            map = new HashMap<>();
            map.put("Name", param.getName());
            map.put("Created at", param.getCreatedAt().toString());
            paramNameDate.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, paramNameDate,
                android.R.layout.simple_list_item_2,
                new String[]{"Name", "Created at"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            Intent i = new Intent(ParametersActivity.this, ParameterDetailsActivity.class);
            Bundle b = new Bundle();
            b.putString("name", params.get((int) id).getName());
            b.putString("date", params.get((int) id).getCreatedAt().toString());
            b.putInt("min", params.get((int) id).getMin());
            b.putInt("max", params.get((int) id).getMax());
            i.putExtras(b);
            startActivity(i);
        });
    }
}
