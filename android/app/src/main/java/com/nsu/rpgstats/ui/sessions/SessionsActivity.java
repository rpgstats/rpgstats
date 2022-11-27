package com.nsu.rpgstats.ui.sessions;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.sessions.PlugSessionsRepository;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.databinding.ActivitySessionsBinding;
import com.nsu.rpgstats.entities.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionsActivity extends AppCompatActivity {
    private static final String TAG = SessionsActivity.class.getSimpleName();
    private ActivitySessionsBinding binding;

    private List<Session> sessions;
    private SessionsRepository repository;

    public SessionsActivity() {
        repository = new PlugSessionsRepository();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        sessions = repository.getSessions();

        binding.sessionsList.sessionsRecyclerView.setLayoutManager
                (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ItemAdapter<SessionItem> sessionItemAdapter = new ItemAdapter<>();
        FastAdapter<SessionItem> sessionsFastAdapter = FastAdapter.with(sessionItemAdapter);
        binding.sessionsList.sessionsRecyclerView.setAdapter(sessionsFastAdapter);
        sessionItemAdapter.set(sessionsToSessionItems(sessions));

        sessionsFastAdapter.setOnClickListener((view, adapter, item, position) -> {
            ((RpgstatsApplication) getApplication()).appContainer.currentSession = sessions.get(position);
            Intent intent = new Intent(this, SessionInfoActivity.class);
            startActivity(intent);
            return false;
        });

//        FastAdapterDiffUtil.INSTANCE.calculateDiff(sessionItemAdapter, sessionsToSessionItems(sessions)).
//                dispatchUpdatesTo(sessionsFastAdapter);

        //sessions = new ArrayList<>();
        //SessionsViewModel

    }

    private ArrayList<SessionItem> sessionsToSessionItems(List<Session> sessions) {
        ArrayList<SessionItem> sessionItems = new ArrayList<>();
        for (Session s : sessions) {
            sessionItems.add(new SessionItem(s));
        }
        return sessionItems;
    }

    private void onSessionClick(int position) {

    }
}
