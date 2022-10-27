package com.nsu.rpgstats.ui.parameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityParametersBinding;
import com.nsu.rpgstats.entities.Parameter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ParametersActivity extends Activity {
    ActivityParametersBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParametersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: get these values from server
        final Parameter[] params = new Parameter[] {
                new Parameter("Attack", new Date(), 0, 993),
                new Parameter("Health points", new Date(), 1, 994),
                new Parameter("Mana points", new Date(), 2, 995),
                new Parameter("Defense", new Date(), 3, 996),
                new Parameter("Strength", new Date(), 4, 997),
                new Parameter("Agility", new Date(), 5, 998),
                new Parameter("Intelligence", new Date(), 6, 999)
        };

        fillParamList(params);

        ImageButton addParameter = findViewById(R.id.addParameterButton);
        addParameter.setOnClickListener(view -> {
            Intent i = new Intent(this, ParameterManageActivity.class);
            i.putExtra("Mode", ParameterManageActivity.MODE_ADD);
            startActivity(i);
        });
    }

    private void fillParamList(Parameter[] params) {
        ListView listView = findViewById(R.id.listView);
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
            b.putString("name", params[(int) id].getName());
            b.putString("date", params[(int) id].getCreatedAt().toString());
            b.putInt("min", params[(int) id].getMin());
            b.putInt("max", params[(int) id].getMax());
            i.putExtras(b);
            startActivity(i);
        });
    }
}
