package com.nsu.rpgstats.ui.gamesystems;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivityGameSystemsBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.ui.AddGameSystemActivity;
import com.nsu.rpgstats.viewmodel.GameSystemsViewModel;

import java.util.ArrayList;
import java.util.List;

public class GameSystemsActivity extends AppCompatActivity implements GameSystemsAdapter.OnGameSystemClickListener {

    private static final String TAG = GameSystemsActivity.class.getSimpleName();
    private ActivityGameSystemsBinding binding;
    protected ActivityResultLauncher<Intent> activityLauncher;
    private List<GameSystem> mGameSystems;

    private GameSystemsAdapter mGameSystemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameSystemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBar.toolbar);

        mGameSystems = new ArrayList<>();
        GameSystemsViewModel gameSystemsViewModel = new ViewModelProvider(this).get(GameSystemsViewModel.class);
        gameSystemsViewModel.getGameSystems().observe(this, gameSystems -> {
            mGameSystemsAdapter.setGameSystemsList(gameSystems);
            mGameSystems = gameSystems;
        });

        activityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new AddGameActivityResultCallback(gameSystemsViewModel, this, binding)
        );


        RecyclerView gameSystemsRecyclerView = binding.gameList.gameSystemsRecyclerView;

        mGameSystemsAdapter = new GameSystemsAdapter(mGameSystems, this);
        gameSystemsRecyclerView.setAdapter(mGameSystemsAdapter);
        gameSystemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setup add game system button
        binding.gameSystemsTab.plusButton.setOnClickListener(view -> {
            //startActivity(new Intent(this, CharactersMainMenuActivity.class));
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
        Log.i(TAG, "creating menu...");
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
        ((RpgstatsApplication)getApplication()).appContainer.currentGameSystem = mGameSystems.get(position);
        Intent intent = new Intent(this, GameSystemInfoActivity.class);
        startActivity(intent);
    }
}