package com.nsu.rpgstats.ui.sessions;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.rpgstats.AppContainer;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivitySessionInfoBinding;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.viewmodel.sessions.SessionInfoViewModel;

import java.util.Locale;


public class SessionInfoActivity extends AppCompatActivity {
    private ActivitySessionInfoBinding binding;
    private Session session;

    private SessionInfoViewModel viewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SessionInfoViewModel.class);

        AppContainer appContainer = ((RpgstatsApplication) getApplication()).appContainer;
        session = viewModel.getSession(appContainer.currentSession.getId());
        setInfo();
        //setSupportActionBar(binding.toolbar);

    }

    private void setInfo() {
        binding.sessionNameText.setText(session.getName());
        binding.sessionAuthorText.setText(session.getAuthor());
        binding.sessionDescriptionText.setText(session.getDescription());
        binding.sessionPlayersNumberText.setText(String.format(Locale.US, "%d", session.getPlayersNumber()));
        binding.sessionMaxPlayersNumberText.setText(String.format(Locale.US, "%d", session.getMaximumPlayers()));
        binding.sessionGameSystemText.setText(session.getGameSystem());
    }
}
