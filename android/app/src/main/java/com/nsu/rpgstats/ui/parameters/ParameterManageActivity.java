package com.nsu.rpgstats.ui.parameters;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityParameterManageBinding;

public class ParameterManageActivity extends AppCompatActivity {
    public static Integer MODE_ADD = 1;
    public static Integer MODE_EDIT = 2;

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
        Integer mode = b.getInt("Mode");

        if (mode.equals(ParameterManageActivity.MODE_EDIT)) {
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

            if (mode.equals(ParameterManageActivity.MODE_ADD)) {
                // TODO: send parameter as a new one
            }
            if (mode.equals(ParameterManageActivity.MODE_EDIT)) {
                // TODO: send parameter as the changed one
            }
        });
    }
}