package com.example.rpgstats;

import android.content.Intent;
import android.os.Bundle;

import com.example.rpgstats.data.GameSystemsRepository;
import com.example.rpgstats.data.PlugGameSystemsRepository;

import com.example.rpgstats.entities.GameSystem;
import com.example.rpgstats.gamesystems.GameSystemsAdapter;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rpgstats.databinding.ActivityMainBinding;
import com.example.rpgstats.gamesystems.OnGameSystemClickListener;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class GameSystemListActivity extends AppCompatActivity implements OnGameSystemClickListener {

    private ActivityMainBinding binding;

    // data
    private List<GameSystem> mGameSystems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        RecyclerView gameSystemsRecyclerView = findViewById(R.id.gameSystemsRecyclerView);
        mGameSystems = new ArrayList<>();
        fillGameSystems();

        GameSystemsAdapter mAdapter = new GameSystemsAdapter(mGameSystems, this);
        gameSystemsRecyclerView.setAdapter(mAdapter);
        gameSystemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setup add game system button
        findViewById(R.id.plus_button).setOnClickListener(view -> {
            Intent intent = new Intent(this, AddGameSystemActivity.class);
            startActivity(intent);
        });
    }

    // suppose getting from server in future
    private void fillGameSystems() {
        GameSystemsRepository gameSystemsRepository = new PlugGameSystemsRepository();
        mGameSystems = gameSystemsRepository.getGameSystems();
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