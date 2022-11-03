package com.nsu.rpgstats.ui.properties;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityPropertyManageBinding;
import com.nsu.rpgstats.entities.Property;

public class PropertyManageActivity extends AppCompatActivity {
    public static Integer MODE_ADD = 1;
    public static Integer MODE_EDIT = 2;
    ActivityPropertyManageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyManageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_property_manage);

        Button confirmButton = findViewById(R.id.confirmAddPropertyButton);
        EditText nameInput = findViewById(R.id.newPropName);

        Bundle b = getIntent().getExtras();
        Integer mode = b.getInt("Mode");

        if (mode.equals(PropertyManageActivity.MODE_EDIT)) {
            // TODO: send Property as the changed one
            TextView head = findViewById(R.id.propHead);
            head.setText(R.string.edit_property);
            nameInput.setText(b.getString("name"));

        }

        confirmButton.setOnClickListener(view -> {
            String name = nameInput.getText().toString();

            Property newProp = new Property(name, false);

            if (mode.equals(PropertyManageActivity.MODE_ADD)) {
                // TODO: send Property as a new one
            }
            if (mode.equals(PropertyManageActivity.MODE_EDIT)) {
                // TODO: send Property as the changed one
            }
        });
    }
}