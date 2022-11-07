package com.nsu.rpgstats.ui.properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityPropertiesBinding;
import com.nsu.rpgstats.entities.Property;

import java.util.ArrayList;
import java.util.HashMap;

public class PropertiesActivity extends Activity {
    ActivityPropertiesBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Property[] props = new Property[] {
                new Property("Small attack up", false),
                new Property("Huge health points up", false),
                new Property("Medium agility up", false),
                new Property("Small attack down", true),
                new Property("Medium mana points up", false),
                new Property("Huge attack up", false)
        };

        fillPropList(props);

        ImageButton addProperty = findViewById(R.id.addPropertyButton);
        addProperty.setOnClickListener(view -> {
            Intent i = new Intent(this, PropertyManageActivity.class);
            i.putExtra("Mode", PropertyManageActivity.MODE_ADD);
            startActivity(i);
        });
    }

    private void fillPropList(Property[] props) {
        ListView listView = findViewById(R.id.propListView);
        final ArrayList<HashMap<String, String>> propNameDate = new ArrayList<>();
        HashMap<String, String> map;
        for (Property prop : props) {
            map = new HashMap<>();
            map.put("Name", prop.getName());
            map.put("Deleted", prop.getDeleted().toString());
            propNameDate.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, propNameDate,
                android.R.layout.simple_list_item_2,
                new String[]{"Name", "Deleted"},
                new int[]{android.R.id.text1, android.R.id.text2});

        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, itemClicked, position, id) -> {
            Intent i = new Intent(PropertiesActivity.this, PropertyDetailsActivity.class);
            Bundle b = new Bundle();
            b.putString("name", props[(int) id].getName());
            b.putString("deleted", props[(int) id].getDeleted().toString());
            i.putExtras(b);
            startActivity(i);
        });
    }
}
