package com.nsu.rpgstats.ui.parameters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityParameterDetailsBinding;
import com.nsu.rpgstats.ui.ConfirmationFragment;
import com.nsu.rpgstats.ui.ManageFormMode;

public class ParameterDetailsActivity extends AppCompatActivity {
    private ActivityParameterDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParameterDetailsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_parameter_details);

        Bundle b = getIntent().getExtras();

        TextView name = findViewById(R.id.paramName);
        String nameIs = "Name: " + b.getString("name");
        name.setText(nameIs);

        TextView date = findViewById(R.id.createdAt);
        String createdAtIs = "Created at " + b.getString("date");
        date.setText(createdAtIs);

        TextView min = findViewById(R.id.minValue);
        String minIs = "Minimum value: " + b.getInt("min");
        min.setText(minIs);

        TextView max = findViewById(R.id.maxValue);
        String maxIs = "Maximum value: " + b.getInt("max");
        max.setText(maxIs);

        Button edit = findViewById(R.id.paramEdit);

        edit.setOnClickListener(view -> {
            Intent i = new Intent(this, ParameterManageActivity.class);
            i.putExtra("Mode", ManageFormMode.EDIT.name());
            i.putExtras(b);
            startActivity(i);
        });

        Button delete = findViewById(R.id.paramDelete);

        delete.setOnClickListener(view -> {
            DialogFragment confirmation = new ConfirmationFragment();
            confirmation.show(getSupportFragmentManager(), "Delete parameter confirmation");
        });
    }
}