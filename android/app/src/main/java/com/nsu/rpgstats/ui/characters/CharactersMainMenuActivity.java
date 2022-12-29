package com.nsu.rpgstats.ui.characters;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityCharactersMainMenuBinding;
import com.nsu.rpgstats.ui.characters.creation.file.FileCreationViewModel;
import com.nsu.rpgstats.ui.characters.creation.new_creation.NewCreationViewModel;
import com.nsu.rpgstats.ui.sessions.SessionsActivity;

import java.io.FileNotFoundException;
import java.util.Objects;

public class CharactersMainMenuActivity extends AppCompatActivity {
    private ActivityCharactersMainMenuBinding binding;
    private WindowViewModel mWindowViewModel;
    private BackgroundViewModel mBackgroundViewModel;

    @Override
    public void onBackPressed() {
        NavController windowController = Navigation.findNavController(this, R.id.windowNavHost);
        String winName = Objects.requireNonNull(windowController.getCurrentDestination()).getDisplayName();
        if (!winName.equals("com.nsu.rpgstats:id/emptyFragment")) {
            Log.d("back pressed win name", winName);
            Navigation.findNavController(this, R.id.windowNavHost).navigate(R.id.emptyFragment);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCharactersMainMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupSwitchToSessions();

        mWindowViewModel = new ViewModelProvider(this).get(WindowViewModel.class);
        mWindowViewModel.getIsShow().observe(this, isShow -> {
            binding.windowArea.setVisibility(isShow ? View.VISIBLE : View.GONE);
        });

        mBackgroundViewModel = new ViewModelProvider(this).get(BackgroundViewModel.class);
        mBackgroundViewModel.getIcon().observe(this, bitmap -> {
            binding.include.icon.setImageBitmap(bitmap);
        });

        mBackgroundViewModel.getBackground().observe(this, bitmap -> {
            binding.include.background.setImageBitmap(bitmap);
        });

        mBackgroundViewModel.getName().observe(this, name-> {
            binding.include.CharTitleName.setText(name);
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
                        new ViewModelProvider(this).get(FileCreationViewModel.class).setPath(chosenImageUri);
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
                        new ViewModelProvider(this).get(NewCreationViewModel.class).setPathIcon(chosenImageUri);
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
                        new ViewModelProvider(this).get(NewCreationViewModel.class).setPathBackground(chosenImageUri);
                    }
                });
    }

    private void setupSwitchToSessions() {
        binding.switchButton.setOnClickListener(v -> {
            Intent i = new Intent(this, SessionsActivity.class);
            startActivity(i);
            finish();
        });
    }

}