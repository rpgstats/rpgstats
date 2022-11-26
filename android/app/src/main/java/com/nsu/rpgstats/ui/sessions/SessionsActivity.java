package com.nsu.rpgstats.ui.sessions;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nsu.rpgstats.databinding.ActivitySessionsBinding;
import com.nsu.rpgstats.entities.Session;

import java.util.ArrayList;
import java.util.List;

public class SessionsActivity extends AppCompatActivity implements SessionsAdapter.OnSessionClickListener {
    private static final String TAG = SessionsActivity.class.getSimpleName();
    private ActivitySessionsBinding binding;

    private SessionsAdapter sessionsAdapter;
    private List<Session> sessions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //sessions = new ArrayList<>();
        //SessionsViewModel

    }

    @Override
    public void onSessionClick(int position) {

    }
}
