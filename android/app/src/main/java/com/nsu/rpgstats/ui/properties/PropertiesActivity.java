package com.nsu.rpgstats.ui.properties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.databinding.ActivityPropertiesBinding;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Property;
import com.nsu.rpgstats.ui.AddItemActivity;
import com.nsu.rpgstats.ui.ItemsAdapter;
import com.nsu.rpgstats.ui.NpcActivity;
import com.nsu.rpgstats.ui.TagsActivity;
import com.nsu.rpgstats.ui.parameters.ParametersActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PropertiesActivity extends AppCompatActivity implements PropertiesAdapter.OnItemClickListener {
    private ActivityPropertiesBinding binding;
    private List<Property> propertyList;
    private PropertiesAdapter adapter;
    private Integer gsId;
    public static ViewModelProvider viewModelProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gsId = ((RpgstatsApplication)getApplication()).appContainer.currentGameSystem.getId();


        if (viewModelProvider == null) {
            viewModelProvider = new ViewModelProvider(this);
        }
        PropertyViewModel propertyViewModel = viewModelProvider.get(PropertyViewModel.class);
        propertyList = propertyViewModel.getItems(gsId).getValue();
        propertyViewModel.getItems(gsId).observe(this, items -> adapter.setPropertyList(propertyList));

        adapter = new PropertiesAdapter(propertyList, this, this);

        PropertyRepository propertyRepository = ((RpgstatsApplication) getApplication()).appContainer.propertyRepository;
        fillPropList(propertyRepository.getProperties(gsId));

        setListeners();
    }

    private void fillPropList(List<Property> props) {
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
            b.putInt("id", props.get(position).getId());
            i.putExtras(b);
            startActivity(i);
        });
    }

    private void setOnClickCreateActivity(View button, Class<?> activityClass) {
        button.setOnClickListener(view -> startActivity(activityClass));
    }

    private void setListeners() {
        setOnClickCreateActivity(binding.BottomBar.tagsButton, TagsActivity.class);
        setOnClickCreateActivity(binding.BottomBar.npcButton, NpcActivity.class);
        setOnClickCreateActivity(binding.BottomBar.parametersButton, ParametersActivity.class);
        setOnClickCreateActivity(binding.BottomBar.propertiesButton, PropertiesActivity.class);
        binding.addPropertyButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PropertyManageActivity.class);
            intent.putExtra("gsId", gsId.toString());
            intent.putExtra("Mode", PropertyManageActivity.MODE_ADD);
            startActivity(intent);
        });
    }

    public void startActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {

    }
}
