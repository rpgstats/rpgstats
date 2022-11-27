package com.nsu.rpgstats.ui.sessions;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nsu.rpgstats.AppContainer;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.data.sessions.PlugSessionsRepository;
import com.nsu.rpgstats.data.sessions.SessionsRepository;
import com.nsu.rpgstats.databinding.ActivitySessionInfoBinding;
import com.nsu.rpgstats.entities.Session;


public class SessionInfoActivity extends Activity {
    private ActivitySessionInfoBinding binding;
    private Session session;
    private SessionsRepository repository;

    public SessionInfoActivity() {
        this.repository = new PlugSessionsRepository();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppContainer appContainer = ((RpgstatsApplication) getApplication()).appContainer;
        session = repository.getSession(appContainer.currentSession.getId());
        setInfo();
        //setSupportActionBar(binding.toolbar);

    }

    private void setInfo() {
        binding.sessionNameText.setText(session.getName());
        binding.sessionAuthorText.setText(session.getAuthor());
        binding.sessionDescriptionText.setText(session.getDescription());
        binding.sessionPlayersNumberText.setText(Integer.toString(session.getPlayersNumber()));
        binding.sessionMaxPlayersNumberText.setText(Integer.toString(session.getMaximumPlayers()));
        binding.sessionGameSystemText.setText(session.getGameSystem());
    }
}
