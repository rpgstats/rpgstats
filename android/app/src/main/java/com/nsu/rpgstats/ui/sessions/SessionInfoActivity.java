package com.nsu.rpgstats.ui.sessions;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nsu.rpgstats.R;
import com.nsu.rpgstats.databinding.ActivitySessionInfoBinding;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.viewmodel.sessions.SessionInfoViewModel;

import java.util.Locale;


public class SessionInfoActivity extends AppCompatActivity implements InvitePlayerPopupListener {
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
            String link = viewModel.getLinkForCurrentSession();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String serverAddr = prefs.getString(getString(R.string.server_address_key), "not working");
            new InvitePlayerPopup(this).show(v, serverAddr + link);
        });
    }

    public void onCopyLink(String link) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", link);
        clipboardManager.setPrimaryClip(clip);

        Toast.makeText(this, "Successfully copied ʕ•́ᴥ•̀ʔっ♡", Toast.LENGTH_SHORT).show();
    }

    private void setDeleteListener() {
        binding.sessionDelete.setOnClickListener((view) -> {
            new DeleteSessionPopup(this::deleteSession).show(view);
        });
    }

    private void deleteSession() {
        viewModel.onDeleteCurrentSession();
    }

    private void setCharacterListListener() {
        binding.sessionCharacterListButton.setOnClickListener((view) -> {
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
