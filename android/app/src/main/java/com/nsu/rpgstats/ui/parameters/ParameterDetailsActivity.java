package com.nsu.rpgstats.ui.parameters;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityParameterDetailsBinding;
import com.nsu.rpgstats.ui.ConfirmationFragment;
import com.nsu.rpgstats.ui.ManageFormMode;
import com.nsu.rpgstats.ui.gamesystems.AddGameActivityResultCallback;

public class ParameterDetailsActivity extends AppCompatActivity {
    private ActivityParameterDetailsBinding binding;
    private ActivityResultLauncher<Intent> activityLauncher;

    private Snackbar error_bar;
    private Snackbar edit_bar;
    private Snackbar add_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParameterDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        error_bar = Snackbar.make(binding.getRoot(), "Parameter manage: error", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        add_bar = Snackbar.make(binding.getRoot(), "Parameter manage: added", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        edit_bar = Snackbar.make(binding.getRoot(), "Parameter manage: edited", Snackbar.LENGTH_LONG)
                .setAction("Action", null);

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>(){
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        String res = result.getData().getStringExtra("RESULT_STRING");
                        if(res == null){
                            error_bar.show();
                        }else {
                            switch (res) {
                                case "add_ok":
                                    add_bar.show();
                                case "edit_ok":
                                    edit_bar.show();
                            }
                            Log.i("TAG", res);
                        }
                    }
                }
        );


        edit.setOnClickListener(view -> {
            Intent i = new Intent(this, ParameterManageActivity.class);
            i.putExtra("Mode", ManageFormMode.EDIT.name());
            i.putExtras(b);
            activityLauncher.launch(i);
        });

        Button delete = findViewById(R.id.paramDelete);

        delete.setOnClickListener(view -> {
            DialogFragment confirmation = new ConfirmationFragment();
            confirmation.show(getSupportFragmentManager(), "Delete parameter confirmation");
        });
    }
}