package com.example.rpgstats;

import android.content.Intent;
import android.os.Bundle;

import com.example.rpgstats.entities.GameSystem;
import com.example.rpgstats.gamesystems.GameSystemsAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rpgstats.databinding.ActivityMainBinding;
import com.example.rpgstats.gamesystems.OnGameSystemClickListener;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnGameSystemClickListener {

    private ActivityMainBinding binding;
    protected ActivityResultLauncher<Intent> activityLauncher;
    private List<GameSystem> mGameSystems;
    private GameSystemsAdapter mGameSystemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        // Re-created activities receive the same MyViewModel instance created by the first activity.
        GameSystemsViewModel gameSystemsViewModel = new ViewModelProvider(this).get(GameSystemsViewModel.class);
        mGameSystems = gameSystemsViewModel.getGameSystems().getValue();
        gameSystemsViewModel.getGameSystems().observe(this, gameSystems -> {
            Log.e("ADD GAME SYSTEM", "Game systems in view model are changed.");
            Log.e("ADD GAME SYSTEM", String.valueOf(gameSystems.size()));
            mGameSystemsAdapter.notifyItemInserted(mGameSystems.size() - 1);
        });

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new AddGameActivityResultCallback(gameSystemsViewModel)
        );


        RecyclerView gameSystemsRecyclerView = findViewById(R.id.gameSystemsRecyclerView);

        mGameSystemsAdapter = new GameSystemsAdapter(mGameSystems, this);
        gameSystemsRecyclerView.setAdapter(mGameSystemsAdapter);
        gameSystemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setup add game system button
        findViewById(R.id.plus_button).setOnClickListener(view -> {
            startAddGameSystemActivityForResult();
        });
    }

    private void startAddGameSystemActivityForResult() {
        Intent intent = new Intent(this, AddGameSystemActivity.class);
        activityLauncher.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.i("MENU", "creating menu...");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGameSystemClick(int position) {
        GameSystem mGameSystem = mGameSystems.get(position);
        Intent intent = new Intent(this, GameSystemInfoActivity.class);
        intent.putExtra("id", mGameSystem.getId().toString());
        startActivity(intent);
    }
}