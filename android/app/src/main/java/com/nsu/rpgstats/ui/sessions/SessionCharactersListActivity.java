package com.nsu.rpgstats.ui.sessions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.nsu.rpgstats.R;
import com.nsu.rpgstats.RpgstatsApplication;
import com.nsu.rpgstats.databinding.ActivitySessionCharacterListBinding;
import com.nsu.rpgstats.databinding.PopupAddCharacterToSessionBinding;
import com.nsu.rpgstats.entities.Session;
import com.nsu.rpgstats.entities.SessionCharacter;
import com.nsu.rpgstats.viewmodel.sessions.SessionsViewModel;

import java.util.ArrayList;
import java.util.List;

public class SessionCharactersListActivity extends AppCompatActivity {
    private static final String TAG = SessionCharactersListActivity.class.getSimpleName();
    private ActivitySessionCharacterListBinding binding;
    private SessionsViewModel viewModel;

    ItemAdapter<SessionCharacterItem> sessionCharacterAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySessionCharacterListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sessionCharactersTab.tabName.setText(R.string.characters);

        viewModel = new ViewModelProvider(this).get(SessionsViewModel.class);


        binding.sessionCharactersList.simpleRecyclerView.setLayoutManager
                (new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sessionCharacterAdapter = new ItemAdapter<>();
        FastAdapter<SessionCharacterItem> sessionsFastAdapter = FastAdapter.with(sessionCharacterAdapter);
        binding.sessionCharactersList.simpleRecyclerView.setAdapter(sessionsFastAdapter);

        Session currentSession = ((RpgstatsApplication) getApplication()).appContainer.currentSession;
        sessionCharacterAdapter.set(charactersToCharacterItems(currentSession.getCharacters()));


        // setup plus button
        binding.sessionCharactersTab.plusButton.setOnClickListener((view -> {
            AddCharacterToSessionPopup popup = new AddCharacterToSessionPopup();
            popup.show(view);
        }));

    }
    private ArrayList<SessionCharacterItem> charactersToCharacterItems(List<SessionCharacter> characters) {
        ArrayList<SessionCharacterItem> characterItems = new ArrayList<>();
        for (SessionCharacter s : characters) {
            characterItems.add(new SessionCharacterItem(s));
        }
        return characterItems;
    }
}
