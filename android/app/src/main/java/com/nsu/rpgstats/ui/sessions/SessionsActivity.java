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
import com.nsu.rpgstats.data.sessions.PlugSessionsRepository;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.databinding.ActivitySessionsBinding;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.ui.profile.ProfileSettingsPopup;
import com.nsu.rpgstats.viewmodel.sessions.SessionsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SessionsActivity extends AppCompatActivity {
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

        setSupportActionBar(binding.toolbar);
        binding.userBar.user.setOnClickListener((v) -> {
            new ProfileSettingsPopup().show(v);
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

//        FastAdapterDiffUtil.INSTANCE.calculateDiff(sessionItemAdapter, sessionsToSessionItems(sessions)).
//                dispatchUpdatesTo(sessionsFastAdapter);

        //sessions = new ArrayList<>();
        //SessionsViewModel

        binding.sessionsTab.plusButton.setOnClickListener(view ->
        {
            Intent i = new Intent(this, AddSessionActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sessionItemAdapter.set(sessionsToSessionItems(viewModel.getSessions().getValue()));
    }

    private ArrayList<SessionItem> sessionsToSessionItems(List<Session> sessions) {
        ArrayList<SessionItem> sessionItems = new ArrayList<>();
        for (Session s : sessions) {
            sessionItems.add(new SessionItem(s));
        }
        return sessionItems;
    }

}
