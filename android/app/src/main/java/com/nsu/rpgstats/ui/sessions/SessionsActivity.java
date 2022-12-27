package com.nsu.rpgstats.ui.sessions;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivitySessionsBinding;
import com.nsu.rpgstats.entities.GameSystem;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.ui.characters.CharactersMainMenuActivity;
import com.nsu.rpgstats.ui.gamesystems.GameSystemsActivity;
import com.nsu.rpgstats.ui.profile.ChangeModeListener;
import com.nsu.rpgstats.ui.profile.ProfileSettingsPopup;
import com.nsu.rpgstats.viewmodel.sessions.SessionsViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SessionsActivity extends AppCompatActivity implements ChangeModeListener {
    private static final String TAG = SessionsActivity.class.getSimpleName();
    private ActivitySessionsBinding binding;

    private List<Session> sessions;
    private SessionsViewModel viewModel;

    ItemAdapter<SessionItem> sessionItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sessionsTab.tabName.setText(R.string.sessions);

        setSupportActionBar(binding.appBar.toolbar);
        binding.appBar.getRoot().setOnClickListener((v) -> {
            new ProfileSettingsPopup(this).show(v);
        });

        binding.switchButton.setOnClickListener(v -> {
            Intent i = new Intent(this, CharactersMainMenuActivity.class);
            startActivity(i);
            finish();
        });

        viewModel = new ViewModelProvider(this).get(SessionsViewModel.class);

        sessions = new ArrayList<>();

        binding.sessionsList.simpleRecyclerView.setLayoutManager
                (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sessionItemAdapter = new ItemAdapter<>();
        FastAdapter<SessionItem> sessionsFastAdapter = FastAdapter.with(sessionItemAdapter);
        binding.sessionsList.simpleRecyclerView.setAdapter(sessionsFastAdapter);

        sessionsFastAdapter.setOnClickListener((view, adapter, item, position) -> {
            ((RpgstatsApplication) getApplication()).appContainer.currentSession = sessions.get(position);
            Intent intent = new Intent(this, SessionInfoActivity.class);
            startActivity(intent);
            return false;
        });

        viewModel.getSessions().observe(this, newSessions -> {
            sessions = newSessions;
            sessionItemAdapter.set(sessionsToSessionItems(sessions));
        });

        binding.sessionsTab.plusButton.setOnClickListener(view ->
        {
            Intent i = new Intent(this, AddSessionActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadSessions();
    }

    private ArrayList<SessionItem> sessionsToSessionItems(List<Session> sessions) {
        ArrayList<SessionItem> sessionItems = new ArrayList<>();
        for (Session s : sessions) {
            sessionItems.add(new SessionItem(s));
        }
        return sessionItems;
    }

    @Override
    public void onChangeMode() {
        Intent i = new Intent(this, GameSystemsActivity.class);
        startActivity(i);
        finish();
    }
}
