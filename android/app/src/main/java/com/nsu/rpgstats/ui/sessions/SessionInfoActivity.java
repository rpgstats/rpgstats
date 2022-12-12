package com.nsu.rpgstats.ui.sessions;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.rpgstats.databinding.ActivitySessionInfoBinding;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.ui.profile.InvitePlayerPopup;
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

        viewModel.getCurrentSession().observe(this, s -> {
            session = s;
            // if was deleted
            if (session == null) {
                finish();
            } else {
                setInfo();
            }
        });
//        ((RpgstatsApplication) getApplication()).appContainer.currentSession = session;
//        setInfo();
        setCharacterListListener();
        setDeleteListener();
        setInvitePlayerListener();
        //setSupportActionBar(binding.toolbar);
        viewModel.onActivityDidLoad();

    }

    private void setInvitePlayerListener() {
        binding.sessionInfoInviteButton.setOnClickListener(v -> {
            new InvitePlayerPopup().show(v);
        });
    }

    private void setDeleteListener() {
        binding.sessionDelete.setOnClickListener((view)->{
            new DeleteSessionPopup(this::deleteSession).show(view);
        });
    }

    private void deleteSession() {
        viewModel.onDeleteCurrentSession();
    }

    private void setCharacterListListener() {
        binding.sessionCharacterListButton.setOnClickListener((view)-> {
            Intent i = new Intent(this, SessionCharactersListActivity.class);
            startActivity(i);
        }
        );
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
