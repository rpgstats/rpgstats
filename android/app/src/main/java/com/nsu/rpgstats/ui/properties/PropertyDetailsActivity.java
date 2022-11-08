package com.nsu.rpgstats.ui.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.PropertyRepository;
import com.nsu.rpgstats.databinding.ActivityPropertyDetailsBinding;
import com.nsu.rpgstats.entities.Property;
import com.nsu.rpgstats.ui.ConfirmationFragment;

public class PropertyDetailsActivity extends AppCompatActivity {
    private ActivityPropertyDetailsBinding binding;
    private Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_property_details);

        // Get property repository
        PropertyRepository propRepo = ((RpgstatsApplication) getApplication()).appContainer.propertyRepository;
        // Get property from repository by id that is in extra of intent
        property = propRepo.getProperty(0, getIntent().getExtras().getInt("id"));

        // Local function to do all work with content of activity
        setAllContent();
    }

    private void setAllContent() {
        setPropertyName();
        setEditButton();
        setDeleteButton();
        setModifiersList();
        setConstraintsList();
    }

    private void setPropertyName() {
        TextView name = findViewById(R.id.propName);
        String nameIs = "Name: " + property.getName();
        name.setText(nameIs);
    }

    private void setEditButton() {
        Button edit = findViewById(R.id.propEdit);
        edit.setOnClickListener(view -> {
            Intent i = new Intent(this, PropertyManageActivity.class);
            i.putExtra("id", property.getId());
            i.putExtra("Mode", PropertyManageActivity.MODE_EDIT);
            startActivity(i);
        });
    }

    private void setDeleteButton() {
        Button delete = findViewById(R.id.propDelete);
        delete.setOnClickListener(view -> {
            DialogFragment confirmation = new ConfirmationFragment();
            confirmation.show(getSupportFragmentManager(), "Delete property confirmation");
        });
    }

    private void setModifiersList() {
        ListView modifs = findViewById(R.id.property_modifiers_list);
        modifs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, property.getModifiers()));
    }

    private void setConstraintsList() {
        ListView constrs = findViewById(R.id.property_constraints_list);
        constrs.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, property.getConstraints()));

        // Useless piece of shit
//        RecyclerView constraintsRecView = findViewById(R.id.property_constraints_list);
//        PropertyConstraintsAdapter adapter = new PropertyConstraintsAdapter(property.getConstraints());
//        constraintsRecView.setAdapter(adapter);
    }
}