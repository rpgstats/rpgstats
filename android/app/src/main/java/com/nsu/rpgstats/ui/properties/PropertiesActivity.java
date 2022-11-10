package com.nsu.rpgstats.ui.properties;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.databinding.ActivityPropertiesBinding;
import com.nsu.rpgstats.entities.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PropertiesActivity extends AppCompatActivity {
    private ActivityPropertiesBinding binding;
    private List<Property> propertyList;
    private Integer gsId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gsId = ((RpgstatsApplication)getApplication()).appContainer.currentGameSystem.getId();

        PropertyRepository propertyRepository = ((RpgstatsApplication) getApplication()).appContainer.propertyRepository;
        propertyList = propertyRepository.getProperties(gsId);
        fillPropList();

        binding.addPropertyButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PropertyManageActivity.class);
            intent.putExtra("gsId", gsId.toString());
            intent.putExtra("Mode", PropertyManageActivity.MODE_ADD);
            startActivity(intent);
        });
    }

    private void fillPropList() {
        ListView listView = findViewById(R.id.propListView);
        final ArrayList<HashMap<String, String>> propNameDate = new ArrayList<>();
        HashMap<String, String> map;
        for (Property prop : propertyList) {
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
            b.putInt("id", propertyList.get(position).getId());
            i.putExtras(b);
            startActivity(i);
        });
    }
}
