package com.nsu.rpgstats.ui.parameters;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

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
import com.nsu.rpgstats.viewmodel.parameters.ParameterManageViewModel;

public class ParameterDetailsActivity extends AppCompatActivity {
    private ActivityParameterDetailsBinding binding;
    private ActivityResultLauncher<Intent> activityLauncher;

    private Snackbar error_bar;
    private Snackbar edit_bar;
    private Snackbar add_bar;
    private ParameterManageViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParameterDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle b = getIntent().getExtras();
        putPlaceholdersFromBundle(b);
        initSnaks();
        model = new ViewModelProvider(this).get(ParameterManageViewModel.class);
        model.setId(b.getInt("id"));

        registerResultLauncher();

        binding.paramEdit.setOnClickListener(view -> {
            Intent i = new Intent(this, ParameterManageActivity.class);
            i.putExtra("Mode", ManageFormMode.EDIT.name());
            i.putExtras(b);
            activityLauncher.launch(i);
        });

        getSupportFragmentManager().setFragmentResultListener("deleteRequest", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String result = bundle.getString("bundleKey");
                if(result.equals("del")){
                    Log.i("TAG", "deleting..");
                    model.onDeleteClick();
                    finish();
                }
            }
        });


        binding.paramDelete.setOnClickListener(view -> {
            DialogFragment confirmation = new ConfirmationFragment();
            confirmation.show(getSupportFragmentManager(), "Delete parameter confirmation");
        });
    }

    private void registerResultLauncher() {
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

    }

    void putPlaceholdersFromBundle(Bundle b){
        String nameIs = "Name: " + b.getString("name");
        binding.paramName.setText(nameIs);
        String createdAtIs = "Created at " + b.getString("date");
        binding.createdAt.setText(createdAtIs);
        String minIs = "Minimum value: " + b.getInt("min");
        binding.minValue.setText(minIs);
        String maxIs = "Maximum value: " + b.getInt("max");
        binding.maxValue.setText(maxIs);
    }

    void initSnaks(){
        error_bar = Snackbar.make(binding.getRoot(), "Parameter manage: error", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        add_bar = Snackbar.make(binding.getRoot(), "Parameter manage: added", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        edit_bar = Snackbar.make(binding.getRoot(), "Parameter manage: edited", Snackbar.LENGTH_LONG)
                .setAction("Action", null);
    }
}