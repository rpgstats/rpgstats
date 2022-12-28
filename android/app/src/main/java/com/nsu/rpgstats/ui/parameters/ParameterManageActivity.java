package com.nsu.rpgstats.ui.parameters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivityParameterManageBinding;
import com.nsu.rpgstats.ui.ManageFormMode;
import com.nsu.rpgstats.viewmodel.parameters.ParameterManageViewModel;

public class ParameterManageActivity extends AppCompatActivity implements ManageListener{
    ActivityParameterManageBinding binding;
    ParameterManageViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityParameterManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        model = new ViewModelProvider(this).get(ParameterManageViewModel.class);

        Bundle b = getIntent().getExtras();
        String mode = b.getString("Mode");
        model.setId(b.getInt("id"));

        if (mode.equals(ManageFormMode.EDIT.name())) {
            binding.paramHead.setText(R.string.parameter_edit_header);
            binding.newParamName.setText(b.getString("name"));
            binding.newParamMin.setText(String.valueOf(b.getInt("min")));
            binding.newParamMax.setText(String.valueOf(b.getInt("max")));
        }

        binding.confirmAddParameterButton.setOnClickListener(view -> {
            model.setName(binding.newParamName.getText().toString());
            if(binding.newParamMin.getText().toString().equals("")){
                model.setMin(0);
            }else {
                model.setMin(Integer.valueOf(binding.newParamMin.getText().toString()));
            }
            if(binding.newParamMax.getText().toString().equals("")){
                model.setMax(0);
            }else {
                model.setMax(Integer.valueOf(binding.newParamMax.getText().toString()));
            }

            if (mode.equals(ManageFormMode.ADD.name())) {
                model.onAddClick();
                onSuccessAdd();
            }
            if (mode.equals(ManageFormMode.EDIT.name())) {
                model.onEditClick();
                onSuccessEdit();
            }

        });
    }

    @Override
    public void onSuccessEdit() {
        Intent intent=new Intent();
        intent.putExtra("RESULT_STRING", "edit_ok");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSuccessAdd() {
        Intent intent=new Intent();
        intent.putExtra("RESULT_STRING", "add_ok");
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onMessage(String message) {

    }
}