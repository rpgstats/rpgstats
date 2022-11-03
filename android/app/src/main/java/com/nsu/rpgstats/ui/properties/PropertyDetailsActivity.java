package com.nsu.rpgstats.ui.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityPropertyDetailsBinding;
import com.nsu.rpgstats.ui.ConfirmationFragment;

public class PropertyDetailsActivity extends AppCompatActivity {
    ActivityPropertyDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPropertyDetailsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_property_details);

        Bundle b = getIntent().getExtras();

        TextView name = findViewById(R.id.propName);
        String nameIs = "Name: " + b.getString("name");
        name.setText(nameIs);

        TextView date = findViewById(R.id.propDeleted);
        String deletedIs = "Deleted: " + b.getString("deleted");
        date.setText(deletedIs);

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