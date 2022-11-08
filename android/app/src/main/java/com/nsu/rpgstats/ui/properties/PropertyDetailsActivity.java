package com.nsu.rpgstats.ui.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.ParameterRepository;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.databinding.ActivityPropertyDetailsBinding;
import com.nsu.rpgstats.entities.Modifier;
import com.nsu.rpgstats.entities.Property;
import com.nsu.rpgstats.ui.ConfirmationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {
    ActivityPropertyDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_property_details);

        Bundle b = getIntent().getExtras();
        PropertyRepository propRepo = ((RpgstatsApplication) getApplication()).appContainer.propertyRepository;
        Property property = propRepo.getProperty(0, b.getInt("id"));
        List<Property> properties = propRepo.getProperties(0);

        TextView name = findViewById(R.id.propName);
        String nameIs = "Name: " + property.getName();
        name.setText(nameIs);

        ListView modifs = findViewById(R.id.property_modifiers_list);
        modifs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, property.getModifiers()));

        RecyclerView constrs = findViewById(R.id.property_constraints_list);
        PropertyConstraintsAdapter adapter = new PropertyConstraintsAdapter();

        constrs.setAdapter(adapter);
        adapter.setConstraints(property.getConstraints());

        Button edit = findViewById(R.id.propEdit);
        edit.setOnClickListener(view -> {
            Intent i = new Intent(this, PropertyManageActivity.class);
            i.putExtra("Mode", PropertyManageActivity.MODE_EDIT);
            i.putExtras(b);
            startActivity(i);
        });

        Button delete = findViewById(R.id.propDelete);
        delete.setOnClickListener(view -> {
            DialogFragment confirmation = new ConfirmationFragment();
            confirmation.show(getSupportFragmentManager(), "Delete property confirmation");
        });
    }
}