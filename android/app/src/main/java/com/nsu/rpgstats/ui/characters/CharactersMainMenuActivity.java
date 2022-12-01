package com.nsu.rpgstats.ui.characters;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityCharactersMainMenuBinding;
import com.nsu.rpgstats.ui.characters.creation.file.FileCreationViewModel;
import com.nsu.rpgstats.ui.characters.creation.new_creation.NewCreationViewModel;

import java.io.FileNotFoundException;

public class CharactersMainMenuActivity extends AppCompatActivity {
    private ActivityCharactersMainMenuBinding binding;
    private WindowViewModel mWindowViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharactersMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mWindowViewModel = new ViewModelProvider(this).get(WindowViewModel.class);
        mWindowViewModel.getIsShow().observe(this, isShow -> {
            binding.windowArea.setVisibility(isShow ? View.VISIBLE : View.GONE);
        });

        binding.windowArea.setOnClickListener(view -> {
            Navigation.findNavController(this, R.id.windowNavHost).navigate(R.id.emptyFragment);
        });


        ((RpgstatsApplication)getApplicationContext()).appContainer.fileActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri chosenImageUri = result.getData().getData();
                        final Cursor cursor = getContentResolver().query( chosenImageUri, null, null, null, null );
                        cursor.moveToFirst();
                        final String filePath = cursor.getString(0);
                        cursor.close();
                        new ViewModelProvider(this).get(FileCreationViewModel.class).setFile(filePath);

                    }
                });
        ((RpgstatsApplication)getApplicationContext()).appContainer.iconActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri chosenImageUri = result.getData().getData();
                        final Cursor cursor = getContentResolver().query( chosenImageUri, null, null, null, null );
                        cursor.moveToFirst();
                        final String filePath = cursor.getString(0);
                        cursor.close();
                        new ViewModelProvider(this).get(NewCreationViewModel.class).setImageFilename(filePath);
                    }
                });
        ((RpgstatsApplication)getApplicationContext()).appContainer.backgroundActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Uri chosenImageUri = result.getData().getData();
                        final Cursor cursor = getContentResolver().query( chosenImageUri, null, null, null, null );
                        cursor.moveToFirst();
                        final String filePath = cursor.getString(0);
                        cursor.close();
                        new ViewModelProvider(this).get(NewCreationViewModel.class).setBackgroundFilename(filePath);
                    }
                });
    }

}