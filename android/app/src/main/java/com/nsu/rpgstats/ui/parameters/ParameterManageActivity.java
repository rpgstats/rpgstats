package com.nsu.rpgstats.ui.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityParameterManageBinding;
import com.nsu.rpgstats.ui.ManageFormMode;

public class ParameterManageActivity extends AppCompatActivity {
    ActivityParameterManageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParameterManageBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_parameter_manage);

        Button confirmButton = findViewById(R.id.confirmAddParameterButton);
        EditText nameInput = findViewById(R.id.newParamName);
        EditText minInput = findViewById(R.id.newParamMin);
        EditText maxInput = findViewById(R.id.newParamMax);

        Bundle b = getIntent().getExtras();
        String mode = b.getString("Mode");

        if (mode.equals(ManageFormMode.EDIT.name())) {
            TextView head = findViewById(R.id.paramHead);
            head.setText(R.string.parameter_edit_header);
            nameInput.setText(b.getString("name"));
            minInput.setText(String.valueOf(b.getInt("min")));
            maxInput.setText(String.valueOf(b.getInt("max")));
        }

        confirmButton.setOnClickListener(view -> {
//            String name = nameInput.getText().toString();
//            Integer min = Integer.valueOf(minInput.getText().toString());
//            Integer max = Integer.valueOf(maxInput.getText().toString());

            //Parameter newParam = new Parameter(name, new Date(), min, max);

            Intent intent=new Intent();

            if (mode.equals(ManageFormMode.ADD.name())) {
                // TODO: send parameter as a new one
                intent.putExtra("RESULT_STRING", "add_ok");
            }
            if (mode.equals(ManageFormMode.EDIT.name())) {
                // TODO: send parameter as the changed one
                intent.putExtra("RESULT_STRING", "edit_ok");
            }
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}